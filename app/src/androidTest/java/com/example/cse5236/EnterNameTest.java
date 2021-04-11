package com.example.cse5236;

import android.content.Intent;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnterNameTest extends ActivityTestRule<EnterName> {

    private EnterName mEnterName;
    private EditText mUsername;
    private EditText mPassword;

    public EnterNameTest() {
        super(EnterName.class);
        launchActivity(getActivityIntent());
        mEnterName = getActivity();
        mUsername = (EditText) mEnterName.findViewById(R.id.editTextUsername);
        mPassword = (EditText) mEnterName.findViewById(R.id.editTextPassword);
        getInstrumentation().waitForIdleSync();
    }
    @Test
    public void testPreconditions() {
        assertNotNull(mEnterName);
        assertNotNull(mUsername);
        assertNotNull(mPassword);
    }
    @Test
    public void testEmptyEditText() {
        assertEquals("Username...", mUsername.getText().toString());
        assertEquals("Password...", mPassword.getText().toString());
    }

    @Test
    public void testUI(){
        mEnterName.runOnUiThread(new Runnable() {
            public void run() {
                mUsername.setText("hello");
                mPassword.setText("hello123");
                assertEquals("hello", mUsername.getText().toString());
                assertEquals("hello123", mPassword.getText().toString());
            }
        });

    }

    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mEnterName.finish();
        }
    }
}
