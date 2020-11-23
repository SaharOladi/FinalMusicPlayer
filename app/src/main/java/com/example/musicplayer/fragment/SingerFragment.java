package com.example.musicplayer.fragment;

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
        View view = inflater.inflate(R.layout.fragment_singer, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_singer);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    public void updateUI() {
        List<Singer> singers = mRepository.getSingers();

        if (mSingerAdapter == null) {
            mSingerAdapter = new SingerAdapter(singers);
            mRecyclerView.setAdapter(mSingerAdapter);
        } else {
            mSingerAdapter.setSingers(singers);
            mSingerAdapter.notifyDataSetChanged();
        }
    }

    private class SingerHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private Singer mSinger;

        public SingerHolder(@NonNull View itemView) {
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

        private void bindSingers(Singer singer) {
            mSinger = singer;
            mTitle.setText(singer.getSingerName());
        }

    }

    private class SingerAdapter extends RecyclerView.Adapter<SingerHolder> {

        private List<Singer> mSingers;
        private List<Singer> mSearchSingers;


        public List<Singer> getSingers() {
            return mSingers;
        }

        public void setSingers(List<Singer> singers) {
            this.mSingers = singers;
            if (singers != null)
                this.mSearchSingers = new ArrayList<>(singers);
            notifyDataSetChanged();
        }

        public SingerAdapter(List<Singer> singers) {
            mSingers = singers;
            mSearchSingers = new ArrayList<>(singers);
        }

        @NonNull
        @Override
        public SingerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.row_item, parent, false);

            SingerHolder singerHolder = new SingerHolder(view);
            return singerHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SingerHolder holder, int position) {
            Singer singer = mSingers.get(position);
            holder.bindSingers(singer);
        }

        @Override
        public int getItemCount() {
            return mSingers.size();
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