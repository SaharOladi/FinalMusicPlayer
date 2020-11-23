package com.example.musicplayer.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;

public class PlayListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;

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

        mRepository = SongRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_song);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        List<Song> songs = mRepository.getSongs();
        if (mSongAdapter == null) {
            mSongAdapter = new SongAdapter(songs);
            mRecyclerView.setAdapter(mSongAdapter);
        } else {
            mSongAdapter.setSongs(songs);
            mSongAdapter.notifyDataSetChanged();
        }


    }

    private class SongHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private Song mSong;

        public SongHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);

            setListeners();

        }

        private void setListeners() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        private void findHolderViews(@NonNull View itemView) {
            mTitle = itemView.findViewById(R.id.name);
        }

        private void bindSong(Song song) {
            mSong = song;
            mTitle.setText(song.getSongTitle());
        }

    }

    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {

        private List<Song> mSongs;
        private List<Song> mSearchSongs;


        public List<Song> getSongs() {
            return mSongs;
        }

        public void setSongs(List<Song> songs) {
            this.mSongs = songs;
            if (songs != null)
                this.mSearchSongs = new ArrayList<>(songs);
            notifyDataSetChanged();
        }

        public SongAdapter(List<Song> songs) {
            mSongs = songs;
            mSearchSongs = new ArrayList<>(songs);
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.row_item, parent, false);

            SongHolder songHolder = new SongHolder(view);
            return songHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.bindSong(song);
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }



}