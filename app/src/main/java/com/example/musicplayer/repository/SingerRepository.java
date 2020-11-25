package com.example.musicplayer.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.model.Singer;
import com.example.musicplayer.model.Song;

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

    public static List<Song> getSongs(Context context, long artist_id){

        List<Song> artistSongList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,//0
                MediaStore.Audio.Media.TITLE,//1
                MediaStore.Audio.Media.ALBUM_ID,//2
                MediaStore.Audio.Media.ALBUM,//3
                MediaStore.Audio.Media.ARTIST,//4
                MediaStore.Audio.Media.DURATION,//5
                MediaStore.Audio.Media.TRACK//6

        };

        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        String selection = "is_music=1 and title !='' and artist_id="+artist_id;
        Cursor cursor = context.getContentResolver().query(uri,projection,selection,null,sortOrder);

        if (cursor!=null&&cursor.moveToFirst()){
            do {

                int trackNumber = cursor.getInt(6);
                while (trackNumber>=1000){
                    trackNumber-=1000;
                }
                artistSongList.add(new Song(cursor.getLong(0),cursor.getString(1),cursor.getLong(2),cursor.getString(3),
                        artist_id,cursor.getString(4),cursor.getInt(5),trackNumber));
            }while (cursor.moveToNext());

            if (cursor!=null) {
                cursor.close();
            }

        }

        return artistSongList;
    }

}
