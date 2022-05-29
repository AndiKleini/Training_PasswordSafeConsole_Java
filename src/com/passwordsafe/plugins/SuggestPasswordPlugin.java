package com.passwordsafe.plugins;

import java.util.Random;

public class SuggestPasswordPlugin extends PluginBase {

    private static final char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVW123456789(/&§".toCharArray();

    @Override
    public void execute() {
        char[] password = new char[10];
        Random rng = new Random();
        for(int i = 0; i < password.length; i++) {
            password[i] = letters[rng.nextInt(letters.length)];
        }
        displayText(password.toString());
    }

}
