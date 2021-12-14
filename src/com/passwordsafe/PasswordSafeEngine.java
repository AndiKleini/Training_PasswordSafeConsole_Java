package com.passwordsafe;;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PasswordSafeEngine {

    private IDataSourceLayer dataLayer;

    private final CipherFacility cipherFaciility;

    public PasswordSafeEngine(
             IDataSourceLayer dataLayer, CipherFacility cipherFacility) {
        this.dataLayer = dataLayer;
        this.cipherFaciility = cipherFacility;

    }
    public String[] GetStoredPasswords() throws Exception {
        return dataLayer.getAllNamesOfPasswords();
    }

    public void AddNewPassword(PasswordInfo info) throws IOException, Exception {
        this.dataLayer.storeNewPassword(
                info.getName(),
                this.cipherFaciility.Encrypt(info.getPlain()));
    }

    public void DeletePassword(String passwordName) throws Exception, IOException {
        this.dataLayer.deletePassword(passwordName, this);
    }

    public String GetPassword(String passwordName) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        char[] buffer = this.dataLayer.getPasswordCipher(passwordName, this);
        return this.cipherFaciility.Decrypt(new String(buffer));
    }
}
