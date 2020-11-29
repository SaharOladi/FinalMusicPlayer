package com.example.musicplayer.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adpter.SingerPlayListAdapter;
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
        setHasOptionsMenu(true);

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
            mAlbumAdapter = new SingerPlayListAdapter(getActivity(), mSongList);
            mRecyclerView.setAdapter(mAlbumAdapter);
        } else {
            mAlbumAdapter.setSongs(mSongList);
            mAlbumAdapter.notifyDataSetChanged();
        }
    }


}