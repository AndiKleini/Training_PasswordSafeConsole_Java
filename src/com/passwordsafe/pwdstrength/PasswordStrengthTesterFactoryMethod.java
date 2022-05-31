package com.passwordsafe.pwdstrength;

public class PasswordStrengthTesterFactoryMethod {
    public static IPasswordStrengthTester getStrengthTester() {
        IPasswordStrengthTester passwordStrengthTester =
                new SimplePasswordStrengthTester();

        passwordStrengthTester =
                new PasswordContainsNumberTester(passwordStrengthTester);
        passwordStrengthTester =
                new PasswordContainsSpecialCharacterTester(passwordStrengthTester);
        return passwordStrengthTester;
    }
}