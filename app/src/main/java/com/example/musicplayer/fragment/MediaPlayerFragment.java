package com.example.musicplayer.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.musicplayer.R;
import com.example.musicplayer.activity.MediaPlayerActivity;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;


public class MediaPlayerFragment extends Fragment {


    public static final String ARGS_MEDIA_PLAYER_SONG_ID = "ARGS_MEDIA_PLAYER_SONG_ID";

    // TODO: create service, permission from user to access and part that has mostly repeated song and ...
    //TODO: add a layout above of list, that have 2 part, one part is playAll and other is Shuffle
    //TODO:play next song automatically
    private ImageView mPlay, mForward, mBackward, mSongBitmap;
    private TextView mSongTitle;
    private SeekBar mSeekBar;

    private MediaPlayer mMediaPlayer;
    private int mSeekForwardTime;
    private int mSeekBackwardTime;

    private long mSongId;
    private Song currentSong, nextSong;
    int count = 0;

    private SongRepository mRepository;
    private List<Song> mSongList = new ArrayList<>();

    private Handler mHandler = new Handler();

    public MediaPlayerFragment() {
        // Required empty public constructor
    }

    public static MediaPlayerFragment newInstance(long songId) {
        MediaPlayerFragment fragment = new MediaPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_MEDIA_PLAYER_SONG_ID, songId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSongId = (long) getArguments().getSerializable(ARGS_MEDIA_PLAYER_SONG_ID);
        mRepository = SongRepository.getInstance(getActivity());
        mSongList = mRepository.getSongs();
        currentSong = mSongList.get(findCurrentSongPosition(mSongId));

    }

    private int findCurrentSongPosition(long songId) {
        for (int i = 0; i < mSongList.size(); i++) {
            if (mSongList.get(i).getId() == songId)
                return i;
        }
        return 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_player, container, false);

        findViews(view);
        initMediaPlayer();

        setListener();

        return view;
    }

    private void findViews(View view) {
        mPlay = view.findViewById(R.id.btn_play);
        mForward = view.findViewById(R.id.btn_forward);
        mBackward = view.findViewById(R.id.btn_backward);
        mSongTitle = view.findViewById(R.id.song_title);
        //TODO: extract the image of song if there is any.
        mSongBitmap = view.findViewById(R.id.song_image);
        mSeekBar = view.findViewById(R.id.seekBar);
    }


    public static Uri getSongUri(long songId) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
    }

    private void initMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(getActivity(), getSongUri(mSongId));
        setupSongView(mSongId);
    }


    private void setListener() {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
                } else {
                    mPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_pause));
                    mMediaPlayer.start();

                }
                mHandler.postDelayed(mRunnable, 0);
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (count < mSongList.size()) {
                            int currentIndexMusic = findCurrentSongPosition(currentSong.getId());
                            if (currentIndexMusic == mSongList.size() - 1) {
                                nextSong = mSongList.get(0);
                                initMediaPlayer(nextSong);
                                setupSongView(nextSong.getId());
                            } else {
                                nextSong = mSongList.get(currentIndexMusic + 1);
                                initMediaPlayer(nextSong);
                                setupSongView(nextSong.getId());
                            }
                            currentSong = nextSong;
                            count++;
                        }
                    }
                });
            }
        });

        mForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mMediaPlayer.getCurrentPosition();
                if (currentPosition + mSeekForwardTime <= mMediaPlayer.getDuration()) {
                    mMediaPlayer.seekTo(currentPosition + mSeekForwardTime);
                } else {
                    mMediaPlayer.seekTo(mMediaPlayer.getDuration());
                }
            }
        });

        mBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mMediaPlayer.getCurrentPosition();

                if (currentPosition - mSeekBackwardTime >= 0) {
                    mMediaPlayer.seekTo(currentPosition - mSeekBackwardTime);
                } else {
                    mMediaPlayer.seekTo(0);
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar != null && fromUser) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    private void initMediaPlayer(Song song) {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(getActivity(), getSongUri(song.getId()));
        mMediaPlayer.start();
    }

    private void setupSongView(long id) {
        if (!mSongList.get(findCurrentSongPosition(id)).getTitle().equals(null))
            mSongTitle.setText(mSongList.get(
                    findCurrentSongPosition(id)).getTitle() + "");

        mSeekForwardTime = 5 * 1000;
        mSeekBackwardTime = 5 * 1000;

        if (mMediaPlayer != null)
            mSeekBar.setMax(mMediaPlayer.getDuration());
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
            mHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }


    public interface CallBack {
        void onStopPlaying(MediaPlayer mediaPlayer);
    }

    public CallBack mCallBack;

    public CallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }


}