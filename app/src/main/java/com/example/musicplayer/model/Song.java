package com.example.musicplayer.model;

public class Song implements Comparable{

    public  long id;
    public  String title;
    public  long albumId;
    public  String albumName;
    public  long artistId;
    public  String artistName;
    public  int duration;

    public Song(long id, String title, long albumId,
                String albumName, long artistId, String artistName,
                int duration, int trackNumber) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.duration = duration;
        this.trackNumber = trackNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public  int trackNumber;

    public Song() {
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
