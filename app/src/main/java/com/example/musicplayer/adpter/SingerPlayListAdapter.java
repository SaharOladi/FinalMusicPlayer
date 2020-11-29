package com.example.musicplayer.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.MediaPlayerActivity;
import com.example.musicplayer.model.Song;

import java.util.List;

public class SingerPlayListAdapter extends RecyclerView.Adapter<SingerPlayListAdapter.SingerPlayListHolder> {

    private List<Song> mSongs;
    private Context mContext;

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        this.mSongs = songs;
    }

    public SingerPlayListAdapter(Context context, List<Song> songs) {
        mContext = context;
        mSongs = songs;
    }

    @NonNull
    @Override
    public SingerPlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_item_playlist, parent, false);

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

    public class SingerPlayListHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mPlay;
        private Song mSong;

        public SingerPlayListHolder(@NonNull View itemView, Song song) {
            super(itemView);
            mSong = song;
        }

        public Song getSong() {
            return mSong;
        }

        public void setSong(Song song) {
            mSong = song;
        }

        public SingerPlayListHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);

            setListeners();

        }

        private void setListeners() {

            mPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mediaPlayerSongId = MediaPlayerActivity.newIntent(mContext,
                            mSong.getId());
                    mContext.startActivity(mediaPlayerSongId);
                }
            });

        }

        private void findHolderViews(@NonNull View itemView) {
            mTitle = itemView.findViewById(R.id.row_item_playlist_title);
            mPlay = itemView.findViewById(R.id.row_item_playlist_btn_play);
        }

        private void bindSongs(Song song) {
            mSong = song;
            mTitle.setText(song.getTitle());
        }

    }


}

