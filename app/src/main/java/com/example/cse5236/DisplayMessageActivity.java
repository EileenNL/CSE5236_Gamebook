package com.example.cse5236;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayMessageActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE2 = "com.example.cse5236.MESSAGE2";
    private Button mOption1;
    private Button mOption2;
    private Button mSettings;
    private Prompt p = generatePromptTree();;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String name = intent.getStringExtra(EnterName.EXTRA_MESSAGE);
        String message = "Hi "+ intent.getStringExtra(EnterName.EXTRA_MESSAGE) + "! Welcome to Gamebook!";
        //User user = new User(name, 0);
        user = User.writeNewUser(name, 12);
        TextView nameText = findViewById(R.id.textView4);
        nameText.setText(user.getName() + ": " + user.getScore());

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
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
            }
        });
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayMessageActivity.this, SettingsActivity.class);
                intent.putExtra(EXTRA_MESSAGE2, user.getName());
                DisplayMessageActivity.this.startActivity(intent);
            }
        });
    }

    public User getUser() {
        return user;
    }

    private Prompt generatePromptTree(){
        Prompt survive = new Prompt(R.string.survive, 0, 0, null, null);
        Prompt dead = new Prompt(R.string.dead, 0, 0, null, null);
        Prompt turnZombie = new Prompt(R.string.turnZombie, 0, 0, null, null);
        Prompt left = new Prompt(R.string.seeZombie, R.string.run, R.string.fight, survive, dead);
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