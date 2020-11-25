package com.example.musicplayer.fragment;

import android.content.ContentUris;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

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


public class MediaPlayerFragment extends Fragment implements MediaPlayerActivity.CallBack {


    public static final String ARGS_MEDIA_PLAYER_SONG_ID = "ARGS_MEDIA_PLAYER_SONG_ID";
    private ImageView mPlay, mPause, mForward, mBackward, mSongBitmap;
    private TextView mSongTitle;
    private SeekBar mSeekBar;

    private MediaPlayer mMediaPlayer;
    private int mSeekForwardTime;
    private int mSeekBackwardTime;

    private long mSongId;

    private boolean isPaused = false;
    private boolean isPlayed = true;

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
        mPause = view.findViewById(R.id.btn_pause);
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

        if (!mSongList.get(findCurrentSongPosition(mSongId)).title.equals(""))
            mSongTitle.setText(mSongList.get(findCurrentSongPosition(mSongId)).title + "");


        mSeekForwardTime = 5 * 1000;
        mSeekBackwardTime = 5 * 1000;

        if (mMediaPlayer != null)
            mSeekBar.setMax(mMediaPlayer.getDuration());

    }


    private void setListener() {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                mHandler.postDelayed(mRunnable, 0);
                isPaused = false;
                isPlayed = true;

                if (!isPaused)
                    mPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_pause));
                if (isPlayed)
                    mPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_pause));

            }
        });

        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.pause();

                isPlayed = false;
                if (!isPlayed)
                    mPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));

                if (!isPaused)
                    mPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));

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

        //TODO: auttomtically play next song
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

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

    @Override
    public void onStopPlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mHandler.removeCallbacks(mRunnable);
        }
    }
}