package com.example.cse5236;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private String mId;
    private String mName;
    private int mScore;

    //private Prompt mCurrent;
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public User(){

    }
    public User(String name, int score){
        mName = name;
        mScore = score;
        mId = UUID.randomUUID().toString();
        //mCurrent = prompt;
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
    public static User writeNewUser(String name, int score) {
        User user = new User(name, score);
//        Map<String, Integer> m = new HashMap<>();
//        m.put(name, score);
        mDatabase.child("users").child(name).setValue(score);
        return user;
    }
    public String getId() {
        return mId;
    }
//
//    public void setCurrent(Prompt current) {
//        mCurrent = current;
//    }
}

//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gamebook-28f88-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = database.getReference(name);
//        myRef.setValue(0);
