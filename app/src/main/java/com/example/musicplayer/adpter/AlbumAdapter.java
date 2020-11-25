package com.example.musicplayer.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.AlbumPlayListActivity;
import com.example.musicplayer.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

    private List<Album> mAlbums;
    private Context mContext;

    public List<Album> getAlbums() {
        return mAlbums;
    }

    public void setAlbums(List<Album> albums) {
        this.mAlbums = albums;
    }

    public AlbumAdapter(Context context, List<Album> albums) {
        mContext = context;
        mAlbums = albums;
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
            mTitle = itemView.findViewById(R.id.name);
            mSinger = itemView.findViewById(R.id.singer);
        }

        private void bindAlbums(Album album) {
            mAlbumId = album.getAlbumId();
            if (!album.getAlbumTitle().equals(null))
                mTitle.setText(album.getAlbumTitle());
            if (!album.getAlbumArtist().equals(null))
                mSinger.setText(album.getAlbumArtist());

        }

    }


}