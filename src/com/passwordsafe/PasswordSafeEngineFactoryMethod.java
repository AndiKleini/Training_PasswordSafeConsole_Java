package com.passwordsafe;

public class PasswordSafeEngineFactoryMethod {
    static PasswordSafeEngine getPasswordSafeEngine(String masterPw, String passwordPath) {
        return new PasswordSafeEngine(passwordPath, new CipherFacility(masterPw));
    }
}
