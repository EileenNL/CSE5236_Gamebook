package com.example.cse5236;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class User implements Serializable {
    private String mUsername;
    private String mPassword;
    private int mScore;
    private String mCurrent;

    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public User(){}

    public User(String name, String pass, int score, Prompt prompt){
        mUsername = name;
        mPassword = pass;
        mScore = score;
        if(prompt != null){
            mCurrent = prompt.Id;
        } else{
            mCurrent = null;
        }
    }

    public String getName() {
        return mUsername;
    }

    public void setName(String name) {
        mUsername = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }

    public void LoginUser(){
        User[] users = new User[1];
        DatabaseReference usernameRef = mDatabase.child("users").child(mUsername);
        final boolean[] writeUser = {false};
        User tempUser = this;
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists() || !dataSnapshot.getValue(User.class).getName().equals(tempUser.getName())) {
                    //writeUser[0] = true;
                    tempUser.writeNewUser();

                    new UserEvents().userCreated();
                }
                else{
                    //TODO check password of existing user, or set valid to false
                    //if pass valid: return user and proceed
                    if(dataSnapshot.getValue(User.class).getPassword().equals(tempUser.getPassword())){
                        new UserEvents().userFound(dataSnapshot.getValue(User.class));
                    }
                    else{
                        new UserEvents().invalidPassword();
                    }
                    //if pass invalid: fire event to block user

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("USER", databaseError.getMessage()); //Don't ignore errors!
            }
        };
//        if (writeUser[0]) {
//            writeNewUser();
//            valid = true;
//        }
        usernameRef.addListenerForSingleValueEvent(eventListener);
    }

    public void writeNewUser() {
        mDatabase.child("users").child(this.mUsername).setValue(this);
    }
//
//    public void setCurrent(Prompt current) {
//        mCurrent = current;
//    }
}

//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gamebook-28f88-default-rtdb.firebaseio.com/");
//        DatabaseReference myRef = database.getReference(name);
//        myRef.setValue(0);
