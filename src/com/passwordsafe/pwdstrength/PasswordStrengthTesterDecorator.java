package com.passwordsafe.pwdstrength;

public abstract class PasswordStrengthTesterDecorator implements IPasswordStrengthTester {
    private IPasswordStrengthTester strengthTester;

    public PasswordStrengthTesterDecorator(IPasswordStrengthTester strengthTester) {
        this.strengthTester = strengthTester;
    }

    @Override
    public int getStrength(String password) {
        return
                this.getSpecificStrength(password) +
                this.strengthTester.getStrength(password);
    }

    protected abstract int getSpecificStrength(String password);
}
