package com.example.musicplayer.repository;

import android.content.Context;

import com.example.musicplayer.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongRepository {

    private static SongRepository sInstance;
    private List<Song> mSongs = new ArrayList<>();
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

}
