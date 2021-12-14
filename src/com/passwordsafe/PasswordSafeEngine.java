package com.passwordsafe;;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordSafeEngine {
    private final CipherFacility cipherFaciility;
    private String path;
    public PasswordSafeEngine(
            String path, CipherFacility cipherFacility) {
        this.cipherFaciility = cipherFacility;
        this.path = path;
    }
    public String[] GetStoredPasswords() throws Exception {
        return getAllNamesOfPasswords();
    }

    public void AddNewPassword(PasswordInfo info) throws IOException, Exception {
        this.storeNewPassword(info);
    }

    public void DeletePassword(String passwordName) throws Exception, IOException {
        this.deletePassword(passwordName);
    }

    public String GetPassword(String passwordName) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        char[] buffer = this.getPasswordCipher(passwordName);
        return this.cipherFaciility.Decrypt(new String(buffer));
    }

    private void WriteToFile(String filename, String password) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
            String crypted = this.cipherFaciility.Encrypt(password);
            writer.write(crypted);
        } finally {
            if (writer != null) try { writer.close(); } catch (IOException ignore) {}
        }
    }

    private File GetFileFromName(String name) {
        return new File( path + "/" + name + ".pw");
    }

    private String[] getAllNamesOfPasswords() throws Exception {
        File directory = new File(this.path);
        if (!directory.isDirectory() && !directory.mkdir()) {
            throw new Exception("Unable to create directory");
        }
        List<File> files = Arrays.asList(directory.listFiles());
        return files.stream()
                .filter(s -> s.getName().endsWith(".pw"))
                .map(f -> f.getName().split("\\.")[0])
                .collect(Collectors.toList()).toArray(new String[0]);
    }

    private void storeNewPassword(PasswordInfo info) throws Exception {
        File directory = new File(this.path);
        if (!directory.isDirectory() && !directory.mkdir()) {
            throw new Exception("Unable to create directory");
        }
        File storage = (this.GetFileFromName(info.getName()));
        if (storage.createNewFile()) {
            this.WriteToFile(storage.getPath(), info.getPlain());
        } else {
            throw new Exception("Password with the same name already existing. Please choose another name of update the existing one.");
        }
    }

    private void deletePassword(String passwordName) throws Exception {
        File storage = this.GetFileFromName(passwordName);
        if (!storage.delete()) {
            throw new Exception("Unable to delete password setting under " + storage.getName());
        }
    }

    private char[] getPasswordCipher(String passwordName) throws IOException {
        File passwordFile = this.GetFileFromName(passwordName);
        char[] buffer = null;
        if (passwordFile.exists()) {
            FileReader reader = null;
            try {
                buffer = new char[(int)passwordFile.length()];
                reader = new FileReader(passwordFile);
                reader.read(buffer);
            }
            finally {
                if (reader != null) { try { reader.close(); } catch (IOException ex) { } };
            }
        }
        return buffer;
    }
}
