package com.example.cse5236;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    private Button mOption1;
    private Button mOption2;
    private TextView mPromptTextView;
    private Prompt p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        generatePromptTree();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Hi "+ intent.getStringExtra(EnterName.EXTRA_MESSAGE) + "! Welcome to Gamebook!";

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        BlankFragment fragmentDemo = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.BlankFragment);

        textView.append("\n" + getString(p.getPrompt()));

        mOption1 = (Button) findViewById(R.id.button);
        mOption2 = (Button) findViewById(R.id.button2);

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

    }

    private void generatePromptTree(){
        Prompt survive = new Prompt(R.string.survive, 0, 0, null, null);
        Prompt dead = new Prompt(R.string.dead, 0, 0, null, null);
        Prompt turnZombie = new Prompt(R.string.turnZombie, 0, 0, null, null);
        Prompt left = new Prompt(R.string.seeZombie, R.string.run, R.string.fight, survive, dead);
        Prompt right = new Prompt(R.string.turnZombie, 0, 0, null, null);
        p = new Prompt(R.string.fork, R.string.left, R.string.right, left, right);
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