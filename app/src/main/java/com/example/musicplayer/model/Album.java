package com.example.musicplayer.model;

public class Album {

    private Long  mAlbumId;
    private String mAlbumTitle;
    private String mAlbumPath;
    private String mAlbumArtist;
    private String mReleaseDate;
    private int mNumberSong;

    public int getNumberSong() {
        return mNumberSong;
    }

    public void setNumberSong(int numberSong) {
        mNumberSong = numberSong;
    }

    public Album() {
    }

    public Album(Long id, String title, String albumPath, String albumArtist, String releaseDate, int number) {
        mAlbumId = id;
        mAlbumTitle = title;
        mAlbumPath = albumPath;
        mAlbumArtist = albumArtist;
        mReleaseDate = releaseDate;
        mNumberSong = number;
    }

    public Long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(Long id) {
        mAlbumId = id;
    }

    public String getAlbumTitle() {
        return mAlbumTitle;
    }

    public void setAlbumTitle(String title) {
        mAlbumTitle = title;
    }

    public String getAlbumPath() {
        return mAlbumPath;
    }

    public void setAlbumPath(String albumPath) {
        mAlbumPath = albumPath;
    }

    public String getAlbumArtist() {
        return mAlbumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        mAlbumArtist = albumArtist;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
