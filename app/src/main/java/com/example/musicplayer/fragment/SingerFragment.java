package com.example.musicplayer.fragment;

import android.content.Intent;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.AlbumPlayListActivity;
import com.example.musicplayer.activity.SingerPlayListActivity;
import com.example.musicplayer.adpter.SingerAdapter;
import com.example.musicplayer.model.Singer;
import com.example.musicplayer.repository.SingerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SingerFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private SingerAdapter mSingerAdapter;

    private SingerRepository mRepository;

    public SingerFragment() {
        // Required empty public constructor
    }


    public static SingerFragment newInstance() {
        SingerFragment fragment = new SingerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = SingerRepository.getInstance(getActivity());


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
        List<Singer> singers = mRepository.getSingers();

        if (mSingerAdapter == null) {
            mSingerAdapter = new SingerAdapter(getActivity(), singers);
            mRecyclerView.setAdapter(mSingerAdapter);
        } else {
            mSingerAdapter.setSingers(singers);
            mSingerAdapter.notifyDataSetChanged();
        }
    }



}