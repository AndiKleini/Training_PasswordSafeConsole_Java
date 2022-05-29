package com.passwordsafe.plugins;

public abstract class PluginBase {
    public abstract void execute();

    protected void displayText(String password) {
        System.out.println(password);
    }
}
