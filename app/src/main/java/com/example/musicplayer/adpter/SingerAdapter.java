package com.example.musicplayer.adpter;

import android.content.ContentProvider;
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
import com.example.musicplayer.activity.MediaPlayerActivity;
import com.example.musicplayer.activity.SingerPlayListActivity;
import com.example.musicplayer.fragment.SingerFragment;
import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Singer;
import com.example.musicplayer.model.Song;
import com.example.musicplayer.repository.SingerRepository;
import com.example.musicplayer.repository.SongRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerHolder> implements Filterable {

    private List<Singer> mSingers;
    private List<Singer> mSearchSingers;

    private Context mContext;

    public List<Singer> getSingers() {
        return mSingers;
    }

    public void setSingers(List<Singer> singers) {
        this.mSingers = singers;
        if (singers != null)
            this.mSearchSingers = new ArrayList<>(singers);
        notifyDataSetChanged();
    }

    public SingerAdapter(Context context, List<Singer> singers) {
        mContext = context;
        mSingers = singers;
        mSearchSingers = new ArrayList<>(singers);

    }

    @NonNull
    @Override
    public SingerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
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


    public class SingerHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mTrackNumber;
        private long mSingerId;

        public SingerHolder(@NonNull View itemView) {
            super(itemView);

            findHolderViews(itemView);

            setListeners();

        }

        private void setListeners() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent singerPlayListActivity = SingerPlayListActivity.newIntent(mContext,
                            mSingerId);
                    mContext.startActivity(singerPlayListActivity);

               }
            });

        }

        private void findHolderViews(@NonNull View itemView) {
            mTitle = itemView.findViewById(R.id.row_item_playlist_title);
            mTrackNumber = itemView.findViewById(R.id.row_item_playlist_singer);
        }

        private void bindSingers(Singer singer) {
            mSingerId = singer.getSingerId();
            if (!singer.getSingerName().equals(null))
                mTitle.setText(singer.getSingerName());
            if (singer.getTrackNumber()!=0)
                mTrackNumber.setText(singer.getTrackNumber()+"");

        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Singer> filteredList = new ArrayList<>();

                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(mSearchSingers);
                } else {
                    for (Singer singer : mSearchSingers) {
                        if (singer.getSingerName().toLowerCase().trim().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredList.add(singer);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (mSingers != null)
                    mSingers.clear();
                if (filterResults.values != null)
                    mSingers.addAll((Collection<? extends Singer>) filterResults.values);
            }
        };
    }
}
