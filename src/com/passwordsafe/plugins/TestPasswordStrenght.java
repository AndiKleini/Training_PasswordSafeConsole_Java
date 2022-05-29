package com.passwordsafe.plugins;

import com.passwordsafe.plugins.PluginBase;

import java.util.Scanner;

public class TestPasswordStrenght extends PluginBase {
    private final Scanner reader;

    public TestPasswordStrenght(Scanner reader) {
        this.reader = reader;
    }

    @Override
    public void execute() {
        this.displayText("Enter password to test: ");
        String input = reader.next();
        if (input.length() > 8 &&
            input.matches(".*[&,?,!,*,+,-].*")) {
            displayText("Password is strong.");
        } else {
            displayText("Password is weak.");
        }
    }
}
