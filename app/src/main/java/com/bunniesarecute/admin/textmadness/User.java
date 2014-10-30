package com.bunniesarecute.admin.textmadness;

import java.util.ArrayList;

/**
 * Created by BFineRocks on 10/30/14.
 */
public class User {
    private String userName;
    private String userEmail;
    private String userPassword;

    private ArrayList<String> userMessages;

    public User(){

    }

    public User(String userName, String userEmail, String userPassword){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        userMessages = new ArrayList<String>();
    }

    public void addNewMessage(String messageAdded){
        userMessages.add(messageAdded);
    }

    public ArrayList<String> getUserMessages(){
        return userMessages;
    }


}
