package com.example.musicplayer.repository;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;

import com.example.musicplayer.model.Album;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumRepository {

    public static final String TAG = "AlbumRepository";
    private static AlbumRepository sInstance;
    private List<Album> mAlbums = new ArrayList<>();
    private Context mContext;

    public AlbumRepository(Context context) {
        mContext = context;
    }

    public static AlbumRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new AlbumRepository(context);
        return sInstance;
    }

    public List<Album> getAlbums() {
        MusicCursorWrapper cursor = new MusicCursorWrapper(mContext.getContentResolver()
                .query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        null, null, null, null));
        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    Log.d(TAG, "getAlbums: ");
                    mAlbums.add(cursor.getAlbum());
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            } finally {
                cursor.close();
            }
        }

        Collections.sort(mAlbums);
        return mAlbums;
    }




}
