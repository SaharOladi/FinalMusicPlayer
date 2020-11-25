package com.example.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.musicplayer.R;
import com.example.musicplayer.fragment.SingerPlayListFragment;

public class SingerPlayListActivity extends AppCompatActivity {

    public static final String EXTRA_SINGER = "EXTRA_SINGER.com.example.musicplayer.activity";
    private static long mSingerAlbumId;

    public static Intent newIntent(Context context, long singerId) {
        mSingerAlbumId = singerId;
        Intent intent = new Intent(context, SingerPlayListActivity.class);
        intent.putExtra(EXTRA_SINGER, singerId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_activity);


        if (fragment == null) {
            SingerPlayListFragment singerPlayListFragment = SingerPlayListFragment.newInstance(mSingerAlbumId);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_activity, singerPlayListFragment)
                    .commit();
        }
    }
}