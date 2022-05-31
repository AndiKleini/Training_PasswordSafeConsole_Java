package com.passwordsafe.pwdstrength;

import java.util.regex.Pattern;

public class PasswordContainsSpecialCharacterTester extends PasswordStrengthTesterDecorator {
    public PasswordContainsSpecialCharacterTester(
            IPasswordStrengthTester passwordStrengthTester) {
        super(passwordStrengthTester);
    }

    @Override
    protected int getSpecificStrength(String password) {
        return Pattern.matches(".*[!,&,%,$,§].*", password) ? 10 : 0;
    }
}
