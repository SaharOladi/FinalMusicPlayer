package com.example.musicplayer.model;

public class Singer implements Comparable{

    private Long mSingerId;

    public Long getSingerAlbumId() {
        return mSingerAlbumId;
    }

    public void setSingerAlbumId(Long singerAlbumId) {
        mSingerAlbumId = singerAlbumId;
    }

    private Long mSingerAlbumId;
    private String mSingerName;
    private int mTrackNumber;

    public int getTrackNumber() {
        return mTrackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        mTrackNumber = trackNumber;
    }

    public Singer() {
    }

    public Singer(String singerName, Long singerId, Long singerAlbumId, int trackNumber) {
        mSingerName = singerName;
        mSingerId = singerId;
        mTrackNumber = trackNumber;
        mSingerAlbumId = singerAlbumId;
    }


    public String getSingerName() {
        return mSingerName;
    }

    public void setSingerName(String name) {
        mSingerName = name;
    }

    public long getSingerId() {
        return mSingerId;
    }

    public void setSingerId(Long id) {
        mSingerId = id;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
