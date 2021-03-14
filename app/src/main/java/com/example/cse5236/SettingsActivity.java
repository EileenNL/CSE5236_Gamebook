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
        String name = intent.getStringExtra(EXTRA_MESSAGE2);
        TextView textView = findViewById(R.id.textView2);

        myref.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    score = String.valueOf(task.getResult().getValue());
                }
            }
        });

        textView.setText(name+": "+score);

        edit = (Button) findViewById(R.id.button3);
        startOver = (Button) findViewById(R.id.button5);
        returnGame = (Button) findViewById(R.id.button6);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myref.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child(name).setValue(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Throwable databaseError = null;
                        Log.d("User", databaseError.getMessage());
                    }
                });
            }
        });

    }
}