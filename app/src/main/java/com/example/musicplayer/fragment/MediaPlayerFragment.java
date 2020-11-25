package com.example.musicplayer.fragment;

import android.content.ContentUris;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;


import com.example.musicplayer.R;

public class MediaPlayerFragment extends Fragment {


    public static final String ARGS_MEDIA_PLAYER_SONG_ID = "ARGS_MEDIA_PLAYER_SONG_ID";
    private ImageView mPlay, mPause, mForward, mBackward;
    private SeekBar mSeekBar;

    private MediaPlayer mMediaPlayer;
    private int mSeekForwardTime;
    private int mSeekBackwardTime;

    private long mSongId;

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
        mSeekBar = view.findViewById(R.id.seekBar);

    }


    public static Uri getSongUri(long songId) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
    }

    private void initMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(getActivity(), getSongUri(mSongId));

        mSeekForwardTime = 5 * 1000;
        mSeekBackwardTime = 5 * 1000;
    }

    private void setListener() {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
            }
        });

        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.pause();
            }
        });

        mForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mMediaPlayer.getCurrentPosition();
                if (currentPosition + mSeekBackwardTime <= mMediaPlayer.getDuration()) {
                    mMediaPlayer.seekTo(currentPosition + mSeekBackwardTime);
                } else {
                    mMediaPlayer.seekTo(mMediaPlayer.getDuration());
                }
            }
        });

        mBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = mMediaPlayer.getCurrentPosition();

                if(currentPosition - mSeekBackwardTime >= 0){
                    mMediaPlayer.seekTo(currentPosition - mSeekBackwardTime);
                }else{
                    mMediaPlayer.seekTo(0);
                }
            }
        });
    }
}