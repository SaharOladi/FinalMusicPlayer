package com.example.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


import com.example.musicplayer.R;
import com.example.musicplayer.fragment.MediaPlayerFragment;


public class MediaPlayerActivity extends AppCompatActivity implements MediaPlayerFragment.CallBack {

    public static final String EXTRA_MEDIA_PLAYER =
            "EXTRA_SINGER.com.example.musicplayer.activity";


    private static long mSongId;

    private MediaPlayerFragment.CallBack mCallBack;

    public static Intent newIntent(Context context, long songId) {
        mSongId = songId;
        Intent intent = new Intent(context, MediaPlayerActivity.class);
        intent.putExtra(EXTRA_MEDIA_PLAYER, songId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_media_player_activity);


        if (fragment == null) {
            MediaPlayerFragment mediaPlayerFragment =
                    MediaPlayerFragment.newInstance(mSongId);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_media_player_activity, mediaPlayerFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onStopPlaying(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }

}