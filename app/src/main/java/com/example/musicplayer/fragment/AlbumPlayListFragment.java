package com.example.musicplayer.fragment;

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
import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.repository.AlbumRepository;

import java.util.ArrayList;
import java.util.List;

public class AlbumPlayListFragment extends Fragment {

    public static final String ARGS_ALBUM_FRAGMENT = "ARGS_ALBUM";
    private RecyclerView mRecyclerView;
    private AlbumPlayListAdapter mAlbumAdapter;

    private long albumId;
    private List<Song> mSongList;

    public AlbumPlayListFragment() {
        // Required empty public constructor
    }


    public static AlbumPlayListFragment newInstance(long albumId) {
        AlbumPlayListFragment fragment = new AlbumPlayListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ALBUM_FRAGMENT, albumId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        albumId = (long) getArguments().getSerializable(ARGS_ALBUM_FRAGMENT);
        mSongList = AlbumRepository.getInstance(getActivity()).getSongs(albumId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_playlist, container, false);
        findViews(view);

        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_album_playlist);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        if (mAlbumAdapter == null) {
            mAlbumAdapter = new AlbumPlayListAdapter(mSongList);
            mRecyclerView.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.setSongs(mSongList);
            mAlbumAdapter.notifyDataSetChanged();
        }
    }

    private class AlbumPlayListHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private Song mSong;

        public AlbumPlayListHolder(@NonNull View itemView) {
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
            mTitle = itemView.findViewById(R.id.row_item_playlist_title);
        }

        private void bindSongs(Song song) {
            mSong = song;
            mTitle.setText(song.getTitle());
        }

    }

    private class AlbumPlayListAdapter extends RecyclerView.Adapter<AlbumPlayListHolder> {

        private List<Song> mSongs;


        public List<Song> getSongs() {
            return mSongs;
        }

        public void setSongs(List<Song> songs) {
            this.mSongs = songs;
            notifyDataSetChanged();
        }

        public AlbumPlayListAdapter(List<Song> songs) {
            mSongs = songs;
        }

        @NonNull
        @Override
        public AlbumPlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.row_item, parent, false);

            AlbumPlayListHolder albumHolder = new AlbumPlayListHolder(view);
            return albumHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumPlayListHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.bindSongs(song);
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }


    }
}