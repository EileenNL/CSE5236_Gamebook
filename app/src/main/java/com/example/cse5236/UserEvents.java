package com.example.cse5236;

import java.util.ArrayList;
import java.util.List;

interface UserCreatedListener {
    public void userCreated();
    public void userFound(User updatedUser);
    public void invalidPassword();
}

public class UserEvents {

    private static List<UserCreatedListener> listeners = new ArrayList<UserCreatedListener>();

    public void addListener(UserCreatedListener toAdd) {
        listeners.add(toAdd);
    }

    public void userCreated() {
        for (UserCreatedListener listen : listeners)
            listen.userCreated();
    }

    public void userFound(User updatedUser) {
        for (UserCreatedListener listen : listeners)
            listen.userFound(updatedUser);
    }

    public void invalidPassword() {
        for (UserCreatedListener listen : listeners)
            listen.invalidPassword();
    }

}
