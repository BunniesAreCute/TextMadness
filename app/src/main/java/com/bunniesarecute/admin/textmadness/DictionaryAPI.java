package com.bunniesarecute.admin.textmadness;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 10/22/14.
 */
public class DictionaryAPI extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = "DictionaryAPI";

    public DictionaryInterface mDictionaryInterface;

/*    public void buildUrlString(String badWord, String wordType) {
        String urlPartOne = "http://api.wordnik.com:80/v4/words.json/reverseDictionary?query=";
        String urlPartTwo = "&includePartOfSpeech=";
        String urlPartThree = "&maxCorpusCount=1&minLength=1&includeTags=false&limit=10&api_key=";
        searchURL = urlPartOne + badWord + urlPartTwo + wordType + urlPartThree + APIkey;
    }*/

    String dictionaryWordsStr = null;
    @Override
    protected String doInBackground(String... strings) {

        String [] backupVerbs = new String[] {"leprechaun jump", "scramble", "fluff", "kill", "air kiss"};
        String [] backupAdverbs = new String[] {"accidentally", "boldly", "dutifully", "seldom", "unhappily"};
        String [] backupAdjectives = new String[] {"spikey", "rough", "purple", "muscular", "angry"};
        String [] backupNouns = new String[] {"kidney", "turkey herding rod", "pillow", "hair ball", "antelope"};

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String partOfSpeechSelected = strings[0];
        Log.d(LOG_TAG, strings[0]);
        String randomDirtyWord = strings[1];
        final String URI_BASE = "http://api.wordnik.com/v4/words.json/reverseDictionary?query=";
        final String URI_POS = "&includePartOfSpeech=";
        final String URI_END = "&maxCorpusCount=1&minLength=1&includeTags=false&limit=10&api_key=";
        final String API_KEY = "6aa015c0d84b01a6c205f6848a6dea42bcb91d757d4341dde";
        URL urlToUse = null;

/*        api.wordnik.com/v4/words.json/reverseDictionary?query=anal&includePartOfSpeech=noun
                &maxCorpusCount=1&minLength=1&includeTags=false&limit=10&api_key=6aa015c0d84b01a6c205f6848a6dea42bcb91d757d4341dde*/

        try {
/*            Uri.Builder dictionaryUri = new Uri.Builder();
            dictionaryUri.scheme("http").authority("api.wordnik.com").appendPath("v4").appendPath("words.json")
                    .appendPath("reverseDictionary").appendQueryParameter("query", randomDirtyWord)
                    .appendQueryParameter("includePartOfSpeech", partOfSpeechSelected)
                    .appendQueryParameter("maxCorpusCount", "1").appendQueryParameter("minLength", "1")
                    .appendQueryParameter("includeTags", "false").appendQueryParameter("skip", "0")
                    .appendQueryParameter("limit", "10").appendQueryParameter("api_key", API_KEY);*/

/*            Uri dictionaryUri = Uri.parse(URI_BASE).buildUpon()
                    .appendQueryParameter("query", randomDirtyWord)
                    .appendQueryParameter("includePartOfSpeech", partOfSpeechSelected)
                    .appendPath(URI_END).appendPath(API_KEY).build();*/


            String urlString = URI_BASE + randomDirtyWord + URI_POS + partOfSpeechSelected + URI_END + API_KEY;


            urlToUse = new URL(urlString);

            Log.i("url", urlString);


            urlConnection = (HttpURLConnection) urlToUse.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inStream == null)
            {
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

            Log.d(LOG_TAG, dictionaryWordsStr);
            final String TOTAL_RESULTS = "totalResults";

            try {
                JSONObject jsonObject = new JSONObject(dictionaryWordsStr);
                int results = jsonObject.getInt(TOTAL_RESULTS);
                if (results ==0 ) {
                    Log.d(LOG_TAG, "WE FOUND IT");
                    Random random = new Random();
                    int number = random.nextInt(backupVerbs.length);
                    Log.d(LOG_TAG, "The number is " + number);
                    if (strings[0].equals("Noun")) {
                        dictionaryWordsStr = backupNouns[number];
                        return dictionaryWordsStr;
                    }
                    else if (strings[0].equals("Adjective")) {
                        dictionaryWordsStr = backupAdjectives[number];
                        return dictionaryWordsStr;
                    } else if (strings[0].equals("Verb")) {
                        dictionaryWordsStr = backupVerbs[number];
                        return dictionaryWordsStr;
                    } else if (strings[0].equals("Adverb")) {
                        dictionaryWordsStr = backupAdverbs[number];
                        return dictionaryWordsStr;
                    }
                    else {
                        dictionaryWordsStr = "chair";
                    }
                }
            } catch (JSONException e) {
                Log.d(LOG_TAG, "WE'RE SCREWED");
                return null;
            }
        }
        catch (MalformedURLException e){
                Log.e("createUrl", e.getMessage());}

        catch (IOException e){
            Log.e("connection", e.getMessage());
        }
        finally{
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

        try{
            return getWordFromJSON(dictionaryWordsStr);
        }catch (JSONException e){
            Log.e("JSON error", e.getMessage());
        }
        return null;
    }

    private String getWordFromJSON(String wordListString) throws JSONException
    {
        Random randWordSelector = new Random();
        final String COMPLETE_RESULTS = "results";
        //The word that has to be parsed.
        final String DEFINED_WORD = "word";
        final int LIMIT_RESULTS_CALLED = 10;

        //Make a new JSON Object from JSONString
        JSONObject jsonObject = new JSONObject(wordListString);
        JSONArray wordsArray = jsonObject.getJSONArray(COMPLETE_RESULTS);

        Log.i("JsonArray", wordsArray.toString());

        String[] wordsReturned = new String[LIMIT_RESULTS_CALLED];

        for(int i = 0; i < wordsArray.length(); i++) {

            String aWordFound;

            //get JsonObjects from Array
            JSONObject aWordDefinition = wordsArray.getJSONObject(i);

            aWordFound = aWordDefinition.getString(DEFINED_WORD);

            wordsReturned[i] = aWordFound;
            Log.i("wordsFound", wordsReturned[i].toString());
        }

        return wordsReturned[randWordSelector.nextInt(LIMIT_RESULTS_CALLED)];

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mDictionaryInterface.foundAWord(s);


    }



    public void setDictionaryInterface(DictionaryInterface something){
        this.mDictionaryInterface = something;
    }

    public interface DictionaryInterface{
       public void foundAWord(String wordFound);
    }

}








    // http://developer.wordnik.com/v4

// "/words.json/reverseDictionary

// "http://api.wordnik.com:80/v4/words.json/reverseDictionary?query=shoe
// &includePartOfSpeech=noun
// &maxCorpusCount=1&minLength=1
// &includeTags=false&skip=0&limit=10&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5