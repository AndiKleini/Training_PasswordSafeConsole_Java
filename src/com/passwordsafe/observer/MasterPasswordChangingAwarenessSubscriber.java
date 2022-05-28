package com.passwordsafe.observer;

public class MasterPasswordChangingAwarenessSubscriber implements com.passwordsafe.observer.IUserSelectionSubscriber {
    @Override
    public void forwardSelection(int selection) {
         if (selection == 6) {
             System.out.println("Please be aware that changing the master password leads to complete data loss !!!");
         }
    }
}
