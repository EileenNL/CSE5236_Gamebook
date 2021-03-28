package com.example.cse5236;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.os.Bundle;
import android.view.View;

public class EnterName extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.cse5236.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextName);
        String message = editText.getText().toString();
        Log.i("EnterName", "Proceeding to display message.");
        User user = new User(message, 0, null);
        user.writeNewUser();
        intent.putExtra(EXTRA_MESSAGE, user);
        startActivity(intent);
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