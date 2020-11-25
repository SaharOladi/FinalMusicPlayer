package com.example.musicplayer.fragment;

import android.content.Intent;
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
import com.example.musicplayer.activity.AlbumPlayListActivity;
import com.example.musicplayer.adpter.AlbumAdapter;
import com.example.musicplayer.model.Album;
import com.example.musicplayer.repository.AlbumRepository;

import java.util.List;


public class AlbumFragment extends Fragment {

    public static final int REQUEST_CODE_ALBUM_FRAGMENT = 1;
    public static final String TAG_ALBUM_PLAY_LIST = "TAG_ALBUM_PLAY_LIST";

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
        List<Album> albums = mRepository.getAlbums();
        if (mAlbumAdapter == null) {
            mAlbumAdapter = new AlbumAdapter(getActivity(), albums);
            mRecyclerView.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.setAlbums(albums);
            mAlbumAdapter.notifyDataSetChanged();
        }
    }

    }
