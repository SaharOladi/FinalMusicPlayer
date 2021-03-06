package com.example.musicplayer.repository;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Song;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongRepository {

    private static SongRepository sInstance;
    private List<Song> mSongs = new ArrayList<>();
    private List<Song> mSongsLiked = new ArrayList<>();
    private Context mContext;

    public SongRepository(Context context) {
        mContext = context;
    }

    public static SongRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new SongRepository(context);
        return sInstance;
    }

    public List<Song> getSongs() {
        MusicCursorWrapper cursor = new MusicCursorWrapper(mContext.getContentResolver()
                .query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null));
        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    mSongs.add(cursor.getSong());
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            } finally {
                cursor.close();
            }
        }
        Collections.sort(mSongs);
        return mSongs;
    }

    public List<Song> getSongsLiked() {
        for (int i = 0; i < mSongs.size(); i++) {
            if (mSongs.get(i).isLike == true && !mSongsLiked.contains(mSongs.get(i)))
                mSongsLiked.add(mSongs.get(i));
        }
        Collections.sort(mSongsLiked);
        return mSongsLiked;
    }


}
