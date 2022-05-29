package com.passwordsafe;

import java.util.Date;

public class MasterPasswordRepositorySecurityProxy implements IMasterPasswordRepository {

    private final IMasterPasswordRepository proxiedInstance;
    private int numberOfEnteredWrongPasswords;

    public MasterPasswordRepositorySecurityProxy(IMasterPasswordRepository masterPasswordRepository) {
        this.proxiedInstance = masterPasswordRepository;
    }

    @Override
    public void setMasterPasswordPlain(String masterPassword) throws Exception {
        this.proxiedInstance.setMasterPasswordPlain(masterPassword);
    }

    @Override
    public String getMasterPasswordPlain() throws Exception {
        return this.proxiedInstance.getMasterPasswordPlain();
    }

    @Override
    public boolean MasterPasswordIsEqualTo(String masterPassword) throws Exception {
        if (this.numberOfEnteredWrongPasswords > 2) {
            System.out.println("Wrong password was entered too often. You are locked now.");
            return false;
        }
        boolean passwordMatch = this.proxiedInstance.MasterPasswordIsEqualTo(masterPassword);
        if (!passwordMatch) {
            this.numberOfEnteredWrongPasswords++;
        } else {
            this.numberOfEnteredWrongPasswords = 0;
        }
        return passwordMatch;
    }
}
