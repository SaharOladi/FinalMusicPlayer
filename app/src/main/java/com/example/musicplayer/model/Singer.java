package com.example.musicplayer.model;

public class Singer {

    private String mSingerName;
    private long mSingerId;

    public Singer() {
    }

    public Singer(String name, long id) {
        mSingerName = name;
        mSingerId = id;
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

    public void setSingerId(long id) {
        mSingerId = id;
    }
}
