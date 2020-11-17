package com.example.musicplayer.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.model.Singer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingerRepository {

    private static SingerRepository sInstance;
    private List<Singer> mSingers = new ArrayList<>();
    private Context mContext;

    public SingerRepository(Context context) {
        mContext = context;
    }

    public static SingerRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new SingerRepository(context);
        return sInstance;
    }

    public List<Singer> getSingers() {

        MusicCursorWrapper cursor = new MusicCursorWrapper(mContext.getContentResolver()
                .query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                        null, null, null, null));
        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    mSingers.add(cursor.getArtist());
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            } finally {
                cursor.close();
            }
        }
        Collections.sort(mSingers);
        return mSingers;
    }

}
