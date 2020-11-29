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

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    private List<Song> mSongs;
    private Context mContext;

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        this.mSongs = songs;
    }

    public MusicAdapter(Context context, List<Song> songs) {
        mContext = context;
        mSongs = songs;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_item_playlist, parent, false);

        MusicHolder holder = new MusicHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.bindSong(song);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mSinger;
        private ImageView mPlayBtn;
        private Song mSong;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);
            setListeners();

        }

        private void setListeners() {

            mPlayBtn.setOnClickListener(new View.OnClickListener() {
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
            mSinger = itemView.findViewById(R.id.row_item_playlist_singer);
            mPlayBtn = itemView.findViewById(R.id.row_item_playlist_btn_play);
        }

        private void bindSong(Song song) {
            mSong = song;
            if (!song.getTitle().equals(null))
                mTitle.setText(song.getTitle());
            if (!song.getArtistName().equals(null))
                mSinger.setText(song.getArtistName());
        }

    }

}


