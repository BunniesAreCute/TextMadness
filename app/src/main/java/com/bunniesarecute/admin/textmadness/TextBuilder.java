package com.bunniesarecute.admin.textmadness;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by admin on 10/21/14.
 */
public  class TextBuilder {
    private  static ArrayList<String> editTextStrings = new ArrayList<String>();
    private String mTextFromMainEditText;
    public static String unMaskedMessage = "";
    private static Random randWordSelector = new Random();
    private  static HashMap<Integer, String> wordMap = new HashMap<Integer, String>();
    private  static ArrayList<String> listOfKeys = new ArrayList<String>();
    private  static Integer wordCounter = 0;
    private  static Integer keysReturned = 0;
    private static int wordSelected;




    public static Integer getWordCounter(){
        return wordCounter;
    }

    public static Integer getKeysReturned()
    {
        return keysReturned;
    }

    public static void wordCountUp(){
        wordCounter = getWordCounter() + 1;
    }

    public static ArrayList<String> getEditTextStrings(){
        return editTextStrings;
    }

    public static String getUnMaskedMessage() {

        Log.i("textEdit class", unMaskedMessage);
        return unMaskedMessage;
    }

    public static void addTextToStringArrayList(String text, int code) {
        wipeTheWordArray();
            int i;
            String[] splitText = text.split(" ");
            for (i = 0; i < splitText.length; i++) {
                getEditTextStrings().add(splitText[i]);
            }

        Log.i("editText String", getEditTextStrings().toString());
    }

    public static String selectRandomWordFromMessage(){
        wordSelected = randWordSelector.nextInt(editTextStrings.size());
        Log.i("randomNumber", String.valueOf(wordSelected));
        return getEditTextStrings().get(wordSelected);
    }

    public static void replaceSwappedWordWithRandom(String word){
        getEditTextStrings().remove(wordSelected);
        getEditTextStrings().add(wordSelected, word);
    }
    public static void replaceSwappedWordWithRandom(String word, int location){
        getEditTextStrings().remove(location);
        getEditTextStrings().add(location, word);
    }


    public static void addRandomWordToArrayList(String word) {
        getEditTextStrings().add(word);
    }

    public static void buildText() {
        StringBuilder mStringBuilder = new StringBuilder();
        for(String word: editTextStrings){
            mStringBuilder.append(word);
            mStringBuilder.append(" ");}
        unMaskedMessage =  mStringBuilder.toString();
    }

    public static HashMap<Integer, String> getWordMap(){
        return wordMap;
    }

    public static void getNextKey(){
       keysReturned++;
    }

    public static void addRandomWordToMap(String word){
        getWordMap().put(getWordCounter(), word);
        Log.i("word counter", getWordCounter().toString());
        Log.i("word added to map", wordMap.get(getWordCounter()));
        wordCountUp();
    }

    public static  String getRandomWordFromMap(Integer wordCounter){
        return getWordMap().get(wordCounter);
    }

    public static void swapOutMaskedWord(String messageFromMain){
        //addTextToStringArrayList(messageFromMain);

        for(int i = 0; i < editTextStrings.size(); i++)
        {
            String wordToCheck = getEditTextStrings().get(i);
            Log.i("word to check", wordToCheck);
            if(wordIsMasked(wordToCheck)){

               wordToCheck = getRandomWordFromMap(getKeysReturned());
               getNextKey();
                replaceSwappedWordWithRandom(wordToCheck, i);

            }
            buildText();
        }
    }

    public static boolean wordIsMasked(String wordToCompare){
        for(int i = 0; i < WordSelect.posObjectArrayList.size(); i++){
            String posWord = WordSelect.posObjectArrayList.get(i).getpOS();
            if(posWord.equalsIgnoreCase(wordToCompare)){
                return true;
            }
        }
        return false;
    }

    public static void wipeTheWordArray()
    {
        getEditTextStrings().clear();
    }

}
