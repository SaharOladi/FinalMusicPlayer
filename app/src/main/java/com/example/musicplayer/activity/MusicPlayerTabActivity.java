package com.example.musicplayer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.musicplayer.R;
import com.example.musicplayer.fragment.AlbumFragment;
import com.example.musicplayer.fragment.PlayListFragment;
import com.example.musicplayer.fragment.SingerFragment;
import com.example.musicplayer.model.Album;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayerTabActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    MusicPageAdapter mMusicPageAdapter;



    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MusicPlayerTabActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initView();
    }


    private void findViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    private void initView() {
        mMusicPageAdapter = new MusicPageAdapter(this);
        mViewPager.setAdapter(mMusicPageAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                (mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setText("SONGS");
                                break;
                            }
                            case 1: {
                                tab.setText("ALBUM");
                                break;
                            }
                            case 2: {
                                tab.setText("ARTIST");
                                break;
                            }
                        }
                    }
                });

        tabLayoutMediator.attach();
    }


    private class MusicPageAdapter extends FragmentStateAdapter {

        public MusicPageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    PlayListFragment playListFragment = PlayListFragment.newInstance();
                    return playListFragment;
                case 1:
                    AlbumFragment albumFragment = AlbumFragment.newInstance();
                    return albumFragment;
                case 2:
                    SingerFragment singerFragment = SingerFragment.newInstance();
                    return singerFragment;
                default:
                    return null;
        }}

        @Override
        public int getItemCount() {
            return 3;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mMusicPageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMusicPageAdapter.notifyDataSetChanged();
    }

}