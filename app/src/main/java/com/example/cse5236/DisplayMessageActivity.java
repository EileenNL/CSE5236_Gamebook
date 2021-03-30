package com.example.cse5236;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayMessageActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE2 = "com.example.cse5236.MESSAGE2";
    private Button mOption1;
    private Button mOption2;
    private Button mSettings;
    private Prompt p = generatePromptTree();;
    private DatabaseReference myref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        final String[] message = new String[1];
        final User[] user = new User[1];

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        user[0] = (User) intent.getSerializableExtra(EnterName.EXTRA_MESSAGE);

        TextView nameText = findViewById(R.id.textView4);
        myref.child("users").child(user[0].getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    user[0] = task.getResult().getValue(User.class);
                    Log.i("TESTTHING2", "ID: " + user[0].getId());
                    nameText.setText(user[0].getName() + ": " + user[0].getScore());
                    message[0] = "Hi "+ intent.getStringExtra(EnterName.EXTRA_MESSAGE) + "! Welcome to Gamebook!";
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });

        //User user = new User(name, 0);



        //nameText.setText(user.getName() + ": " + user.getScore());

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message[0]);
        BlankFragment fragmentDemo = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.BlankFragment);

        textView.append("\n" + getString(p.getPrompt()));

        mOption1 = (Button) findViewById(R.id.button);
        mOption2 = (Button) findViewById(R.id.button2);
        mSettings = (Button) findViewById(R.id.button4);

        mOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = p.getOption1Prompt();
                fragmentDemo.message(textView, p.getPrompt());
                if(p.getOption1() != 0 || p.getOption2() != 0){
                    mOption1.setText(p.getOption1());
                    mOption2.setText(p.getOption2());
                } else {
                    ViewGroup layout = (ViewGroup) mOption1.getParent();
                    layout.removeView(mOption1);
                    ViewGroup layout2 = (ViewGroup) mOption2.getParent();
                    layout.removeView(mOption2);
                }
                if (p.mPrompt == R.string.survive){
                    incrementScore(user, nameText);
                }
                if (p.mPrompt == R.string.pipe1){
                    incrementScore(user, nameText);
                }
                if (p.mPrompt == R.string.lucky){
                    incrementScore(user, nameText);
                }
            }
        });

        mOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = p.getOption2Prompt();
                fragmentDemo.message(textView, p.getPrompt());
                if(p.getOption1() != 0 || p.getOption2() != 0) {
                    mOption1.setText(p.getOption1());
                    mOption2.setText(p.getOption2());
                } else {
                    ViewGroup layout = (ViewGroup) mOption1.getParent();
                    layout.removeView(mOption1);
                    ViewGroup layout2 = (ViewGroup) mOption2.getParent();
                    layout.removeView(mOption2);
                }
                if (p.mPrompt == R.string.survive){
                    incrementScore(user, nameText);
                }
                if (p.mPrompt == R.string.pipe1){
                    incrementScore(user, nameText);
                }
                if (p.mPrompt == R.string.lucky){
                    incrementScore(user, nameText);
                }
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMessageActivity.this, SettingsActivity.class);
                intent.putExtra(EXTRA_MESSAGE2, user[0]);
                Log.i("TESTTEST", user[0].getName());
                Log.i("TESTTEST", user[0].getId());
                startActivity(intent);
            }
        });
    }



    private Prompt generatePromptTree(){
        Prompt dead = new Prompt(R.string.dead, 0, 0, null, null);

        Prompt eatBrains = new Prompt(R.string.eatBrains1,0,0,null,null);
        Prompt eatBrainsNot = new Prompt(R.string.eatBrainsNot1, 0,0,null,null);
        Prompt bushes = new Prompt(R.string.bushes1,0,0,null,null);
        Prompt turnZombie = new Prompt(R.string.turnZombie, R.string.eatBrains, R.string.eatBrainsNot, eatBrains, eatBrainsNot);
        Prompt look = new Prompt(R.string.look, R.string.bushes, R.string.emptyHouse, bushes,turnZombie);

        Prompt lucky = new Prompt(R.string.lucky,0,0,null,null);
        Prompt cont = new Prompt(R.string.continueWalking1,R.string.squareUp,R.string.run,dead,lucky);
        Prompt accept = new Prompt(R.string.accept1,0,0,null,null);
        Prompt refuse = new Prompt(R.string.refuse1,R.string.enterHouse,R.string.continueWalking,turnZombie,cont);

        Prompt talk = new Prompt(R.string.talk1,R.string.accept,R.string.refuse,accept,refuse);
        Prompt steal = new Prompt(R.string.steal1,0,0,null,null);
        Prompt walk = new Prompt(R.string.walk1, R.string.talk,R.string.steal,talk,steal);
        Prompt ignore = new Prompt(R.string.ignore1, 0,0,null,null);
        Prompt survive = new Prompt(R.string.survive, R.string.walk, R.string.ignore, walk, ignore);
        Prompt left = new Prompt(R.string.seeZombie, R.string.run, R.string.fight, dead, survive);

        Prompt goBack = new Prompt(R.string.goBack1, 0, 0, null, null);
        Prompt town = new Prompt(R.string.town1, 0, 0, null,null);
        Prompt brick = new Prompt(R.string.brick1, 0,0,null,null);
        Prompt pipe = new Prompt(R.string.pipe1,R.string.goBack,R.string.town,goBack,town);
        Prompt findHelp = new Prompt(R.string.findHelp1,R.string.brick,R.string.pipe,brick,pipe);
        Prompt treat = new Prompt(R.string.treat1,R.string.ally, R.string.alone,null,null);
        Prompt help1 = new Prompt(R.string.help1, R.string.treat, R.string.findHelp, treat,findHelp);
        Prompt right = new Prompt(R.string.person, R.string.hide, R.string.help, look, help1);

        p = new Prompt(R.string.fork, R.string.left, R.string.right, left, right);
        return p;
    }

    protected void onPause() {
        super.onPause();
        Log.i("DisplayMessage", "onPause called");
    }

    protected void onResume() {
        super.onResume();
        Log.i("DisplayMessage", "onResume called");
    }

    private void incrementScore(User[] user, TextView nameText){
 /*       myref.child("users").child(user[0].getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {*/
                    int score = user[0].getScore();
                    score++;
                    user[0].setScore(score);
                    myref.child("users").child(user[0].getId()).setValue(user[0]);
                    nameText.setText(user[0].getName() + ": " + user[0].getScore());
                /*}
            }
        });*/
    }
}