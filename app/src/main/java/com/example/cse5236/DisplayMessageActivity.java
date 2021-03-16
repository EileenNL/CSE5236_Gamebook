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
                if(p.mPrompt == R.string.survive){
                    myref.child("users").child(user[0].getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                int score = Integer.valueOf(String.valueOf(task.getResult().getValue()));
                                score++;
                                myref.child("users").child(user[0].getId()).setValue(score);
                                nameText.setText(user[0].getName() + ": " + score);
                            }
                        }
                    });
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
                if(p.mPrompt == R.string.survive){
                    myref.child("users").child(user[0].getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                int score = Integer.valueOf(String.valueOf(task.getResult().getValue()));
                                score++;
                                myref.child("users").child(user[0].getId()).setValue(score);
                                nameText.setText(user[0].getName() + ": " + score);
                            }
                        }
                    });
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
        Prompt survive = new Prompt(R.string.survive, 0, 0, null, null);
        Prompt dead = new Prompt(R.string.dead, 0, 0, null, null);
        Prompt turnZombie = new Prompt(R.string.turnZombie, 0, 0, null, null);
        Prompt left = new Prompt(R.string.seeZombie, R.string.run, R.string.fight, dead, survive);
        Prompt right = new Prompt(R.string.turnZombie, 0, 0, null, null);
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
}