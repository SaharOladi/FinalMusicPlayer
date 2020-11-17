package com.example.musicplayer.repository;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Singer;
import com.example.musicplayer.model.Song;

import android.database.CursorWrapper;
import android.util.Log;

public class MusicCursorWrapper extends CursorWrapper {

    public static final String TAG = "MusicCursorWrapper";

    public MusicCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Album getAlbum() {

        String title = getString(getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM));
        String albumPath = getString(getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART));
        Long Id = getLong(getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ID));
        String artist = getString(getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST));
        int number = getInt(getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS));

        Album album = new Album();
        album.setAlbumId(Id);
        album.setAlbumPath(albumPath);
        album.setAlbumTitle(title);
        album.setAlbumArtist(artist);
        album.setNumberSong(number);

        Log.d(TAG, "getAlbums: ");

        return album;
    }

    public Song getSong() {

        long id = getLong(getColumnIndex(MediaStore.Audio.Media._ID));
        String FilePath = getString(getColumnIndex(MediaStore.Audio.Media.DATA));
        String Title = getString(getColumnIndex(MediaStore.Audio.Media.TITLE));
        String Artist = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST));
        String Album = getString(getColumnIndex(MediaStore.Audio.Media.ALBUM));
        int Duration = getInt(getColumnIndex(MediaStore.Audio.Media.DURATION));

        Song song = new Song();
        song.setSongId(id);
        song.setSongTitle(Title);
        song.setAlbumTitle(Album);
        song.setArtistName(Artist);
        song.setSongDuration(Duration);
        song.setFullPath(FilePath);

        return song;
    }

    public Singer getArtist() {

        Long id = getLong(getColumnIndex(MediaStore.Audio.Artists._ID));
        String name = getString(getColumnIndex(MediaStore.Audio.Artists.ARTIST));
        int numberOfSongs = getInt(getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

        Singer singer = new Singer();
        singer.setSingerId(id);
        singer.setSingerName(name);
        singer.setTrackNumber(numberOfSongs);

        return singer;
    }
}
