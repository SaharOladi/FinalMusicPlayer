package com.example.musicplayer.adpter;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.activity.SingerPlayListActivity;
import com.example.musicplayer.fragment.SingerFragment;
import com.example.musicplayer.model.Singer;

import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerHolder> {

    private List<Singer> mSingers;
    private Context mContext;

    public List<Singer> getSingers() {
        return mSingers;
    }

    public void setSingers(List<Singer> singers) {
        this.mSingers = singers;
    }

    public SingerAdapter(Context context, List<Singer> singers) {
        mContext = context;
        mSingers = singers;
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
            mTitle = itemView.findViewById(R.id.name);
            mTrackNumber = itemView.findViewById(R.id.singer);
        }

        private void bindSingers(Singer singer) {
            mSingerId = singer.getSingerId();
            if (!singer.getSingerName().equals(null))
                mTitle.setText(singer.getSingerName());
            if (singer.getTrackNumber()!=0)
                mTrackNumber.setText(singer.getTrackNumber()+"");

        }

    }
}
