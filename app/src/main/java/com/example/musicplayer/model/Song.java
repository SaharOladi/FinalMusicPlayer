package com.example.musicplayer.model;

public class Song {

    private Long mSongId;
    private String mSongTitle;
    private Long mAlbumId;
    private String mAlbumTitle;
    private Long mArtistId;
    private String mArtistName;
    private String fullPath;
    private int songDuration;

    public Song() {
    }

    public Song(Long songId, String songTitle, Long albumId, String albumTitle,
                Long artistId, String artistName, String fullPath, int songDuration) {

        mSongId = songId;
        mSongTitle = songTitle;
        mAlbumId = albumId;
        mAlbumTitle = albumTitle;
        mArtistId = artistId;
        mArtistName = artistName;
        this.fullPath = fullPath;
        this.songDuration = songDuration;
    }

    public Long getSongId() {
        return mSongId;
    }

    public void setSongId(Long songId) {
        mSongId = songId;
    }

    public String getSongTitle() {
        return mSongTitle;
    }

    public void setSongTitle(String songTitle) {
        mSongTitle = songTitle;
    }

    public Long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(Long albumId) {
        mAlbumId = albumId;
    }

    public String getAlbumTitle() {
        return mAlbumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        mAlbumTitle = albumTitle;
    }

    public Long getArtistId() {
        return mArtistId;
    }

    public void setArtistId(Long artistId) {
        mArtistId = artistId;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String artistName) {
        mArtistName = artistName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }
}
