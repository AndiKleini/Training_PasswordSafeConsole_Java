package com.passwordsafe.pwdstrength;

import java.util.regex.Pattern;

public class PasswordContainsNumberTester extends PasswordStrengthTesterDecorator {
    public PasswordContainsNumberTester(IPasswordStrengthTester strengthTester) {
        super(strengthTester);
    }

    @Override
    public int getSpecificStrength(String password) {
        return Pattern.matches(".*\\d.*", password) ? 10 : 0;
    }
}
