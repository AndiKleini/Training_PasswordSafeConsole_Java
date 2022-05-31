package com.passwordsafe.pwdstrength;

public class SimplePasswordStrengthTester implements IPasswordStrengthTester {
    @Override
    public int getStrength(String password) {
        return password.length();
    }
}
