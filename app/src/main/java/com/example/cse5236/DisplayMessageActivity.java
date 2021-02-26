package com.example.cse5236;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Hi "+ intent.getStringExtra(EnterName.EXTRA_MESSAGE) + "! Welcome to {title of our game}!";

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
        BlankFragment fragmentDemo = (BlankFragment)
                getSupportFragmentManager().findFragmentById(R.id.BlankFragment);
        fragmentDemo.message(textView, message);
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