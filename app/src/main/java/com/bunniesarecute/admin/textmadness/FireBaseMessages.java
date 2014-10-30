package com.bunniesarecute.admin.textmadness;

import com.firebase.client.Firebase;

/**
 * Created by BFineRocks on 10/30/14.
 */
public class FireBaseMessages {

    final static String FIREBASE_BASE_URL = "https://dazzling-heat-5107.firebaseio.com/Users";


    public void addMessageToFireBase(String userName, String message)
    {
        Firebase ref = new Firebase(FIREBASE_BASE_URL);
        Firebase userRef = ref.child(userName);
        userRef.push().setValue(message);
    }
}
