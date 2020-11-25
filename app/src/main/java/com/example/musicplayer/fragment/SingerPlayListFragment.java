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
import com.example.musicplayer.model.Song;
import com.example.musicplayer.repository.SingerRepository;

import java.util.List;


public class SingerPlayListFragment extends Fragment {

    public static final String ARGS_SINGER_FRAGMENT = "ARGS_SINGER";
    private RecyclerView mRecyclerView;
    private SingerPlayListAdapter mAlbumAdapter;

    private long singerAlbumId;
    private List<Song> mSongList;

    public SingerPlayListFragment() {
        // Required empty public constructor
    }


    public static SingerPlayListFragment newInstance(long singerAlbumId) {
        SingerPlayListFragment fragment = new SingerPlayListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_SINGER_FRAGMENT, singerAlbumId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singerAlbumId = (long) getArguments().getSerializable(ARGS_SINGER_FRAGMENT);
        mSongList = SingerRepository.getInstance(getActivity()).getSongs(getActivity(), singerAlbumId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singer_playlist, container, false);
        findViews(view);

        initViews();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_singer_playlist);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        if (mAlbumAdapter == null) {
            mAlbumAdapter = new SingerPlayListAdapter(mSongList);
            mRecyclerView.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.setSongs(mSongList);
            mAlbumAdapter.notifyDataSetChanged();
        }
    }

    private class SingerPlayListHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private Song mSong;

        public SingerPlayListHolder(@NonNull View itemView) {
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

        private void bindSongs(Song song) {
            mSong = song;
            mTitle.setText(song.getTitle());
        }

    }

    private class SingerPlayListAdapter extends RecyclerView.Adapter<SingerPlayListHolder> {

        private List<Song> mSongs;


        public List<Song> getSongs() {
            return mSongs;
        }

        public void setSongs(List<Song> songs) {
            this.mSongs = songs;
            notifyDataSetChanged();
        }

        public SingerPlayListAdapter(List<Song> songs) {
            mSongs = songs;
        }

        @NonNull
        @Override
        public SingerPlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.row_item, parent, false);

            SingerPlayListHolder singerHolder = new SingerPlayListHolder(view);
            return singerHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SingerPlayListHolder holder, int position) {
            Song song = mSongs.get(position);
            holder.bindSongs(song);
        }

        @Override
        public int getItemCount() {
            return mSongs.size();
        }


    }
}