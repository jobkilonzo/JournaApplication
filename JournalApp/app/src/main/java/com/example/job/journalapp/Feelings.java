package com.example.job.journalapp;

import com.google.firebase.database.Exclude;

public class Feelings {
    private String mEvent;
    private String mTitle;

    public Feelings(){}
    public Feelings(String title,String event){
        mEvent = event;
        mTitle = title;
    }

    public String getmTitle() {
        return mTitle;
    }
    public String getmEvent() {
        return mEvent;
    }
}
