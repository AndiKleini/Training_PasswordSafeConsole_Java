package com.passwordsafe;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final MasterPasswordRepository masterRepository = new MasterPasswordRepository("./master.pw");
    private static PasswordSafeEngine passwordSafeEngine = null;

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Passwordsafe");

        boolean abort = false;
        boolean unlocked = false;
        Scanner read = new Scanner(System.in);
        while (!abort) {
            System.out.println("Enter master (1), show all (2), show single (3), add (4), delete(5), set new master (6), Abort (0)");
            int input = read.nextInt();
            switch (input) {
                case 0: {
                    abort = true;
                    break;
                }
                case 1: {
                    unlocked = enterMaster(read);
                    break;
                }
                case 2: {
                    showAll(unlocked);
                    break;
                }
                case 3: {
                    showSingle(unlocked, read);
                    break;
                }
                case 4: {
                    addNew(unlocked, read);
                    break;
                }
                case 5: {
                    delete(unlocked, read);
                    break;
                }
                case 6: {
                    unlocked = false;
                    passwordSafeEngine = null;
                    setNewMaster(read);
                    break;
                }
                default:
                    System.out.println("Invalid input");
            }
        }

        System.out.println("Good by !");
    }

    private static void setNewMaster(Scanner read) throws Exception {
        System.out.println("Enter new master password ! (Warning you will loose all already stored passwords)");
        String masterPw = read.next();
        masterRepository.setMasterPasswordPlain(masterPw);
        // urgent hotfix delete old passwords after changing the master
        File oldPasswords = new File("./passwords.pw");
        if (oldPasswords.isDirectory())
        {
            String[] children = oldPasswords.list();
            for (int i=0; i<children.length; i++) {
                (new File(oldPasswords, children[i])).delete();
            }
        }
        // The directory is now empty or this is a file so delete it
        oldPasswords.delete();
    }

    private static void delete(boolean unlocked, Scanner read) throws Exception {
        if (unlocked) {
            System.out.println("Enter password name");
            String passwordName = read.next();
            passwordSafeEngine.DeletePassword(passwordName);
        } else {
            System.out.println("Please unlock first by entering the master password.");
        }
    }

    private static void addNew(boolean unlocked, Scanner read) throws Exception {
        if (unlocked) {
            System.out.println("Enter new name of password");
            String passwordName = read.next();
            System.out.println("Enter password");
            String password = read.next();
            passwordSafeEngine.AddNewPassword(new PasswordInfo(password, passwordName));
        } else {
            System.out.println("Please unlock first by entering the master password.");
        }
    }

    private static void showSingle(boolean unlocked, Scanner read) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (unlocked) {
            System.out.println("Enter password name");
            String passwordName = read.next();
            System.out.println(passwordSafeEngine.GetPassword(passwordName));
        } else {
            System.out.println("Please unlock first by entering the master password.");
        }
    }

    private static void showAll(boolean unlocked) throws Exception {
        if (unlocked) {
            Arrays.stream(passwordSafeEngine.GetStoredPasswords()).forEach(pw -> System.out.println(pw));
        } else {
            System.out.println("Please unlock first by entering the master password.");
        }
        return;
    }

    private static boolean enterMaster(Scanner read) throws Exception {
        boolean unlocked;
        System.out.println("Enter master password");
        String masterPw = read.next();
        unlocked = masterRepository.MasterPasswordIsEqualTo(masterPw);
        if (unlocked) {
            passwordSafeEngine = new PasswordSafeEngine("./passwords.pw", new CipherFacility(masterPw));
            System.out.println("unlocked");
        } else {
            System.out.println("master password did not match ! Failed to unlock.");
        }
        return unlocked;
    }
}
