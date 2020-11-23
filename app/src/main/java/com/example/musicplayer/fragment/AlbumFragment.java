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
import com.example.musicplayer.repository.AlbumRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AlbumFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private AlbumAdapter mAlbumAdapter;

    private AlbumRepository mRepository;

    public AlbumFragment() {
        // Required empty public constructor
    }


    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = AlbumRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
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
        List<Album> albums = mRepository.getAlbums();
        if (mAlbumAdapter == null) {
            mAlbumAdapter = new AlbumAdapter(albums);
            mRecyclerView.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.setAlbums(albums);
            mAlbumAdapter.notifyDataSetChanged();
        }
    }

    private class AlbumHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private Album mAlbum;

        public AlbumHolder(@NonNull View itemView) {
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

        private void bindAlbums(Album album) {
            mAlbum = album;
            mTitle.setText(album.getAlbumTitle());
        }

    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

        private List<Album> mAlbums;
        private List<Album> mSearchAlbums;


        public List<Album> getAlbums() {
            return mAlbums;
        }

        public void setAlbums(List<Album> albums) {
            this.mAlbums = albums;
            if (albums != null)
                this.mSearchAlbums = new ArrayList<>(albums);
            notifyDataSetChanged();
        }

        public AlbumAdapter(List<Album> albums) {
            mAlbums = albums;
            mSearchAlbums = new ArrayList<>(albums);
        }

        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.row_item, parent, false);

            AlbumHolder albumHolder = new AlbumHolder(view);
            return albumHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            Album album = mAlbums.get(position);
            holder.bindAlbums(album);
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
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
