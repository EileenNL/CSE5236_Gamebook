package com.example.cse5236;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.pm.ActivityInfo;
import android.widget.TextView;

public class EnterName extends AppCompatActivity {
    private int currentScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

    public static final String EXTRA_MESSAGE = "com.example.cse5236.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        TextView invalidPasswordText = findViewById(R.id.invalidPasswordText);
        invalidPasswordText.setVisibility(View.INVISIBLE);
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText usernameText = (EditText) findViewById(R.id.editTextUsername);
        EditText passwordText = (EditText) findViewById(R.id.editTextPassword);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        Log.i("EnterName", "Proceeding to display message.");
        final User[] user = {new User(username, password, 0, null)};
        user[0].LoginUser();
        UserCreatedListener listener = new UserCreatedListener(){
            @Override
            public void userCreated(){
                intent.putExtra(EXTRA_MESSAGE, user[0]);
                startActivity(intent);
            }
            @Override
            public void userFound(User updatedUser){
                user[0] = updatedUser;
                intent.putExtra(EXTRA_MESSAGE, user[0]);
                startActivity(intent);
            }
            @Override
            public void invalidPassword(){
                TextView invalidPasswordText = findViewById(R.id.invalidPasswordText);
                invalidPasswordText.setVisibility(View.VISIBLE);
            }
        };
    new UserEvents().addListener(listener);
    }

    protected void onPause() {
        super.onPause();
        Log.i("EnterName", "onPause called");
    }

    protected void onResume() {
        super.onResume();
        Log.i("EnterName", "onResume called");
    }
}