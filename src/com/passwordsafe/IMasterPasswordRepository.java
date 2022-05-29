package com.passwordsafe;

public interface IMasterPasswordRepository {
    void setMasterPasswordPlain(String masterPassword) throws Exception;

    String getMasterPasswordPlain() throws Exception;

    boolean MasterPasswordIsEqualTo(String masterPassword) throws Exception;
}
