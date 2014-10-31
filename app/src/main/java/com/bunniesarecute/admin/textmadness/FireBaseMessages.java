package com.bunniesarecute.admin.textmadness;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BFineRocks on 10/30/14.
 */
public class FireBaseMessages {

    final static String FIREBASE_BASE_URL = "https://dazzling-heat-5107.firebaseio.com/Users";
    final static String FIREBASE_MESSAGE = "sentMessages";
    final static String FIREBASE_TAG = "message";
    final static ArrayList<String> messageHistory = new ArrayList<String>();


    public void addMessageToFireBase(String userName, String message)
    {
        Firebase ref = new Firebase(FIREBASE_BASE_URL);
        Firebase userRef = ref.child(userName);
        Firebase messageRef = userRef.child(FIREBASE_MESSAGE);
        Map<String, String> messageMaker = new HashMap<String, String>();
        messageMaker.put(FIREBASE_TAG, message);
        messageRef.push().setValue(messageMaker);
    }

    public void getMessagesFromFireBase(String userName){
        Firebase ref = new Firebase(FIREBASE_BASE_URL);
        Firebase userRef = ref.child(userName);
        Firebase messageRef = userRef.child(FIREBASE_MESSAGE);
        Query messageQuery = messageRef.limit(10);

       messageQuery.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               Map<String, Object> message = (Map<String, Object>) dataSnapshot.getValue();
               String aMessage = message.get(FIREBASE_TAG).toString();
               addNewMessageToHistory(aMessage);


           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {


           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
       });

    }
    

    public void addNewMessageToHistory(String message){
        getArrayListOfMessages().add(message);
    }

    public List<String> getArrayListOfMessages(){
//        Log.i("messageList", messageHistory.get(0));
        return messageHistory;
    }
}
