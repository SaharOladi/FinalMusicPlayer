package com.example.musicplayer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.musicplayer.R;
import com.example.musicplayer.fragment.AlbumFragment;
import com.example.musicplayer.fragment.AlbumPlayListFragment;
import com.example.musicplayer.model.Album;

public class AlbumPlayListActivity extends AppCompatActivity {

    public static final String EXTRA_ALBUM = "EXTRA_ALBUM.com.example.musicplayer.activity";
    private static long mAlbumId;

    public static Intent newIntent(Context context, long albumId) {
        mAlbumId = albumId;
        Intent intent = new Intent(context, AlbumPlayListActivity.class);
        intent.putExtra(EXTRA_ALBUM, albumId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_play_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_activity);


        if (fragment == null) {
            AlbumPlayListFragment albumPlayListFragment = AlbumPlayListFragment.newInstance(mAlbumId);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container_activity, albumPlayListFragment)
                    .commit();
        }
    }
}