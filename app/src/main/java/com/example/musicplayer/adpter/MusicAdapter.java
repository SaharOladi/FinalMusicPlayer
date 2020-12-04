package com.example.musicplayer.adpter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.MediaPlayerActivity;
import com.example.musicplayer.model.Song;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> implements Filterable {

    private List<Song> mSongs;
    private List<Song> mSearchSongs;
    private Context mContext;

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        this.mSongs = songs;
        if (songs != null)
            this.mSearchSongs = new ArrayList<>(songs);
        notifyDataSetChanged();
    }

    public MusicAdapter(Context context, List<Song> songs) {
        mContext = context;
        mSongs = songs;
        mSearchSongs = new ArrayList<>(songs);
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

        holder.bindSong(holder, song);
    }

    // TODO: implementation 'org.jaudiotagger:jaudiotagger:2.0.1'
    public static Uri getImage(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mSinger;
        private ImageView mPlayBtn, mCover;
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
            mCover = itemView.findViewById(R.id.row_item_playlist_cover_art);
        }

        private void bindSong(MusicHolder holder, Song song) {
            mSong = song;
            if (!song.getTitle().equals(null))
                mTitle.setText(song.getTitle());
            if (!song.getArtistName().equals(null))
                mSinger.setText(song.getArtistName());

            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));


            ImageLoader.getInstance().displayImage(getImage(song.getAlbumId()).toString(), holder.mCover,
                    new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.image_music)
                            .resetViewBeforeLoading(true).build());



        }

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Song> filteredList = new ArrayList<>();

                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(mSearchSongs);
                } else {
                    for (Song song : mSearchSongs) {
                        if (song.getTitle().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim()) ||
                                song.getArtistName().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim()) ||
                                song.getAlbumName().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredList.add(song);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (mSongs != null)
                    mSongs.clear();
                if (filterResults.values != null)
                    mSongs.addAll((Collection<? extends Song>) filterResults.values);
            }
        };
    }

}


