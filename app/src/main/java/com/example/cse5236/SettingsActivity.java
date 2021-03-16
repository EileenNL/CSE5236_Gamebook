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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.example.cse5236.DisplayMessageActivity.EXTRA_MESSAGE2;

public class SettingsActivity extends AppCompatActivity {
    private Button edit;
    private Button startOver;
    private Button returnGame;
    private String score;
    private DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.textView2);

        final String[] message = new String[1];
        final User[] user = new User[1];

        user[0] = (User) intent.getSerializableExtra(EXTRA_MESSAGE2);

        myref.child("users").child(user[0].getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    user[0] = task.getResult().getValue(User.class);
                    textView.setText(user[0].getName() + ": " + user[0].getScore());
                }
                else {
                    Log.e("firebase", "Error getting data", task.getException());
                }
            }
        });

        edit = (Button) findViewById(R.id.button3);
        startOver = (Button) findViewById(R.id.button5);
        returnGame = (Button) findViewById(R.id.button6);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user[0].setScore(0);
                myref.child("users").child(user[0].getId()).setValue(user[0]);
                textView.setText(user[0].getName() + ": " + user[0].getScore());
            }
        });

        startOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myref.child("users").child(user[0].getId()).removeValue();
                Intent intent = new Intent(SettingsActivity.this, EnterName.class);
                SettingsActivity.this.startActivity(intent);
            }
        });

        returnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, DisplayMessageActivity.class);
                SettingsActivity.this.startActivity(intent);
            }
        });
    }
}