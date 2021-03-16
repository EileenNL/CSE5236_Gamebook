package com.example.cse5236;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User implements Serializable {
    private String mId;
    private String mName;
    private int mScore;
    private String mCurrent;

    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public User(){}

    public User(String name, int score, Prompt prompt){
        mName = name;
        mScore = score;
        mId = UUID.randomUUID().toString();
        if(prompt != null){
            mCurrent = prompt.Id;
        } else{
            mCurrent = null;
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void writeNewUser() {
        mDatabase.child("users").child(this.mId).setValue(this);
    }
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
//
//    public void setCurrent(Prompt current) {
//        mCurrent = current;
//    }
}

//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gamebook-28f88-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = database.getReference(name);
//        myRef.setValue(0);
