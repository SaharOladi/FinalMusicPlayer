package com.example.musicplayer.model;

public class Singer implements Comparable{

    private String mSingerName;
    private Long mSingerId;
    private int mTrackNumber;

    public int getTrackNumber() {
        return mTrackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        mTrackNumber = trackNumber;
    }

    public Singer() {
    }

    public Singer(String singerName, Long singerId, int trackNumber) {
        mSingerName = singerName;
        mSingerId = singerId;
        mTrackNumber = trackNumber;
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
