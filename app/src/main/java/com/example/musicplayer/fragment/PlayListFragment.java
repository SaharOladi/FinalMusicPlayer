package com.example.musicplayer.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adpter.MusicAdapter;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.repository.SongRepository;

import java.util.List;
import java.util.Random;

public class PlayListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;

    private SongRepository mRepository;


    public PlayListFragment() {
        // Required empty public constructor
    }


    public static PlayListFragment newInstance() {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        mRepository = SongRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        List<Song> songs = mRepository.getSongs();
        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(), songs);
            mRecyclerView.setAdapter(mMusicAdapter);
        } else {
            mMusicAdapter.setSongs(songs);
            mMusicAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.play_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMusicAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}
