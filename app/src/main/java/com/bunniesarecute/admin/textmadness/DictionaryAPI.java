package com.bunniesarecute.admin.textmadness;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by admin on 10/22/14.
 */
public class DictionaryAPI extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = "DictionaryAPI";
    public Boolean dirtyWords = MainActivity.getDirtyWords();
    public DictionaryInterface mDictionaryInterface;
    public String urlString = "";

    String dictionaryWordsStr = null;


    @Override
    protected String doInBackground(String... strings) {
        final String API_KEY = "6aa015c0d84b01a6c205f6848a6dea42bcb91d757d4341dde";
        String partOfSpeechSelected = strings[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        Log.d(LOG_TAG, "dirty words is " + dirtyWords);


        if (dirtyWords == true) {

            String[] arrayOfDirtySearchWords = new String[] {"sex", "seduce", "penis", "vagina",};
            Random random = new Random();
            int x = random.nextInt(arrayOfDirtySearchWords.length);

            String query = arrayOfDirtySearchWords[x];
            Log.d(LOG_TAG, "query is " + query);
            final String URI_BASE = "http://api.wordnik.com:80/v4/words.json/reverseDictionary?query=";
            final String URI_MIDDLE = "&includePartOfSpeech=";
            final String URI_END = "&minLength=1&includeTags=false&skip=0&limit=10&api_key=";
            URL urlToUse = null;

            urlString = URI_BASE + query + URI_MIDDLE + partOfSpeechSelected + URI_END + API_KEY;

            Log.d(LOG_TAG, "urlString is " + urlString);

        } else {

            final String URI_BASE = "http://api.wordnik.com/v4/words.json/randomWord?hasDictionaryDef=false&includePartOfSpeech=";
            final String URI_END = "&minLength=1&api_key=";

            urlString = URI_BASE + partOfSpeechSelected + URI_END + API_KEY;
        }



            try {
                URL urlToUse = new URL(urlString);
                Log.d(LOG_TAG, "urlToUse is " + urlToUse);


                urlConnection = (HttpURLConnection) urlToUse.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                dictionaryWordsStr = buffer.toString();

                Log.d(LOG_TAG, "dictionarWordsStr is " + dictionaryWordsStr);
                //final String TOTAL_RESULTS = "totalResults";

//            try {
//                JSONObject jsonObject = new JSONObject(dictionaryWordsStr);
//                int results = jsonObject.getInt(TOTAL_RESULTS);
//                if (results ==0 ) {
//                    Log.d(LOG_TAG, "WE FOUND IT");
//                    Random random = new Random();
//                    int number = random.nextInt(backupVerbs.length);
//                    Log.d(LOG_TAG, "The number is " + number);
//                    if (strings[0].equals("Noun")) {
//                        dictionaryWordsStr = backupNouns[number];
//                        return dictionaryWordsStr;
//                    }
//                    else if (strings[0].equals("Adjective")) {
//                        dictionaryWordsStr = backupAdjectives[number];
//                        return dictionaryWordsStr;
//                    } else if (strings[0].equals("Verb")) {
//                        dictionaryWordsStr = backupVerbs[number];
//                        return dictionaryWordsStr;
//                    } else if (strings[0].equals("Adverb")) {
//                        dictionaryWordsStr = backupAdverbs[number];
//                        return dictionaryWordsStr;
//                    }
//                    else {
//                        dictionaryWordsStr = "chair";
//                    }
//                }
//            } catch (JSONException e) {
//                Log.d(LOG_TAG, "WE'RE SCREWED");
//                return null;
//            }
            } catch (MalformedURLException e) {
                Log.e("createUrl", e.getMessage());
            } catch (IOException e) {
                Log.e("connection", e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                Log.d(LOG_TAG, "made it to the last part of DoInBackground");
                return getWordFromJSON(dictionaryWordsStr, dirtyWords, partOfSpeechSelected);
            } catch (JSONException e) {
                Log.e("JSON error", e.getMessage());
            }
            return null;
        }


    private String getWordFromJSON(String wordListString, Boolean dirtyWords, String partOfSpeechSelected) throws JSONException
    {
        Log.d(LOG_TAG, "Made is to getWordFromJSON");
        final String DEFINED_WORD = "word";
        final String TOTAL_RESULTS = "totalResults";
        String aWordFound = "fake word return";

        if (dirtyWords) {
            JSONObject jsonObject = new JSONObject(wordListString);
            int results = jsonObject.getInt(TOTAL_RESULTS);
            if (results == 0) {

                String[] backupVerbs = new String[]{"rubb", "cuddle", "whip", "seduce", "caress"};
                String[] backupAdverbs = new String[]{"seductively", "slowly", "roughly", "lovingly", "softly"};
                String[] backupAdjectives = new String[]{"spikey", "rough", "hott", "sexy", "wet"};
                String[] backupNouns = new String[]{"special place", "lips", "chain", "curves", "bed"};

                Log.d(LOG_TAG, "No results from query");
                Random random = new Random();
                int number = random.nextInt(backupVerbs.length);
                Log.d(LOG_TAG, "The number is " + number);
                if (partOfSpeechSelected.equals("noun")) {
                    aWordFound = backupNouns[number];
                    return aWordFound;
                } else if (partOfSpeechSelected.equals("adjective")) {
                    aWordFound = backupAdjectives[number];
                    return aWordFound;
                } else if (partOfSpeechSelected.equals("verb")) {
                    aWordFound = backupVerbs[number];
                    return aWordFound;
                } else if (partOfSpeechSelected.equals("adverb")) {
                    aWordFound = backupAdverbs[number];
                    return aWordFound;
                } else {
                    aWordFound = "chair";
                }
            } else {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                Random random = new Random();

                int randomInt = random.nextInt(jsonArray.length());
                JSONObject chosenObject = jsonArray.getJSONObject(randomInt);
                aWordFound = chosenObject.getString(DEFINED_WORD);
            }
        }
        else {
            //Make a new JSON Object from JSONString
            JSONObject jsonObject = new JSONObject(wordListString);

            Log.i("JsonArray", jsonObject.toString());



            aWordFound = jsonObject.getString(DEFINED_WORD);


            Log.i("wordsFound", aWordFound.toString());
        }

        Log.d(LOG_TAG, "aWordFound is " + aWordFound);
        return aWordFound;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mDictionaryInterface.foundAWord(s);
    }



    public void setDictionaryInterface(DictionaryInterface dictionaryInterface){
        this.mDictionaryInterface = dictionaryInterface;
    }

    public interface DictionaryInterface{
       public void foundAWord(String wordFound);
    }

}

