package com.example.musicplayer.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.AlbumPlayListActivity;
import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Song;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> implements Filterable {

    private List<Album> mAlbums;
    private List<Album> mSearchAlbums;

    private Context mContext;

    public List<Album> getAlbums() {
        return mAlbums;
    }

    public void setAlbums(List<Album> albums) {
        this.mAlbums = albums;
        if (albums != null)
            this.mSearchAlbums = new ArrayList<>(albums);
    }

    public AlbumAdapter(Context context, List<Album> albums) {
        mContext = context;
        mAlbums = albums;
        mSearchAlbums = new ArrayList<>(albums);
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
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

    public class AlbumHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mSinger;
        private long mAlbumId;

        public TextView getTitle() {
            return mTitle;
        }

        public void setTitle(TextView title) {
            mTitle = title;
        }

        public TextView getSinger() {
            return mSinger;
        }

        public void setSinger(TextView singer) {
            mSinger = singer;
        }

        public long getAlbumId() {
            return mAlbumId;
        }

        public void setAlbumId(long albumId) {
            mAlbumId = albumId;
        }

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);

            setListeners();

        }

        private void setListeners() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent albumPlayListActivity = AlbumPlayListActivity.newIntent(mContext,
                            mAlbumId);
                    mContext.startActivity(albumPlayListActivity);
                }
            });

        }

        private void findHolderViews(@NonNull View itemView) {
            mTitle = itemView.findViewById(R.id.row_item_playlist_title);
            mSinger = itemView.findViewById(R.id.row_item_playlist_singer);
        }

        private void bindAlbums(Album album) {
            mAlbumId = album.getAlbumId();
            if (!album.getAlbumTitle().equals(null))
                mTitle.setText(album.getAlbumTitle());
            if (!album.getAlbumArtist().equals(null))
                mSinger.setText(album.getAlbumArtist());

        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Album> filteredList = new ArrayList<>();

                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(mSearchAlbums);
                } else {
                    for (Album album : mSearchAlbums) {
                        if (album.getAlbumTitle().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim()) ||
                                album.getAlbumArtist().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredList.add(album);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (mAlbums != null)
                    mAlbums.clear();
                if (filterResults.values != null)
                    mAlbums.addAll((Collection<? extends Album>) filterResults.values);
            }
        };
    }


}