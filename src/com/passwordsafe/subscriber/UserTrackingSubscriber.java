package com.passwordsafe.subscriber;

import java.util.ArrayList;
import java.util.Date;

public class UserTrackingSubscriber implements IUserSelectionSubscriber {

    record SelectionHistory(Integer selection, Date timeStamp) {}

    private ArrayList<SelectionHistory> selectionHistory = new ArrayList<>();

    @Override
    public void forwardSelection(int selection) {
        this.selectionHistory.add(new SelectionHistory(selection, new Date()));

        System.out.println("User tracking state: ");
        this.selectionHistory.forEach(item -> System.out.println(item));
    }
}
