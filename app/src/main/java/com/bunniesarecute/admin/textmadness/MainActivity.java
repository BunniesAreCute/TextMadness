package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    Button insertWordButton;
    Button shareTextButton;
    EditText mainEditText;
    Button randomWordButton;
    int code = 0;

    String mFullTextMessage = "";
    String mRandomWordFromMessage = "";
    private static boolean mDirtyWords = false;

    public static final int GENERATE_RANDOM_WORD_REQUEST = 37;

    static final String FULL_TEXT = "com.bunniesarecute.admin.textmadness.mainactivity.mFullTextMessage";
    static final String RAND_FROM_MESSAGE = "com.bunniesarecute.admin.textmadness.mainactivity.mFullTextMessage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressBarIndeterminateVisibility(false);
        mainEditText = (EditText) findViewById(R.id.edit_text);
        insertWordButton = (Button) findViewById(R.id.generate_word_button);
        insertWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mTextBuilder.addTextToStringArrayList(mainEditText.getText().toString());
                Intent genWordIntent = new Intent(mainEditText.getContext(), WordSelect.class);
                startActivityForResult(genWordIntent, GENERATE_RANDOM_WORD_REQUEST);

                //once logic is set for getting random word, set it with mTextBuilder.addRandomWordToArrayList(*whatever the new random word is*)
            }
        });
        shareTextButton = (Button) findViewById(R.id.send);
        shareTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBuilder.swapOutMaskedWord(mainEditText.getText().toString());
                mFullTextMessage = TextBuilder.getUnMaskedMessage();

                // mTextBuilder.addTextToStringArrayList(mainEditText.getText().toString());
                // mTextBuilder.buildText();
                //mFullTextMessage = mTextBuilder.getTextFromMainEditText();
                Log.i("extra message", mFullTextMessage);
                Intent sendMessageIntent = new Intent(getApplicationContext(), ShareOptions.class); //new share message activity
                sendMessageIntent.putExtra(FULL_TEXT, mFullTextMessage);

                startActivity(sendMessageIntent);
            }
        });
        randomWordButton = (Button) findViewById(R.id.random_word_button);
        randomWordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBuilder.addTextToStringArrayList(mainEditText.getText().toString(), code);
                mRandomWordFromMessage = TextBuilder.selectRandomWordFromMessage();
                Intent randomIntent = getIntent().putExtra("RAND_FROM_MESSAGE", mRandomWordFromMessage);
                code++;
                //Bundle randomBundle = new Bundle();
                //randomBundle.putString("RAND_FROM_MESSAGE", randomWordReturned);
                getFragmentManager().beginTransaction().add(R.id.main_activity, new SwapRandomWord()).commit();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nsfw:
                setDirtyWords(true);
                item.setChecked(true);
                return true;
            case R.id.sfw:
                setDirtyWords(false);
                item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mainEditText = (EditText) findViewById(R.id.edit_text);
//        mainEditText.setText(mainEditText.getText() + " appended str");  //  Please replace me with the real thing
        if (requestCode == GENERATE_RANDOM_WORD_REQUEST) {
            if (resultCode == RESULT_OK) {
                // use random word here
                mainEditText = (EditText) findViewById(R.id.edit_text);
                mainEditText.setText(mainEditText.getText() + " " + data.getStringExtra("RANDOM_WORD"));
                TextBuilder.addTextToStringArrayList(mainEditText.getText().toString(), code);
                code++;
                mainEditText.setSelection(mainEditText.length());
            }
        }
    }

    public void setDirtyWords(boolean dirtyWords) {
        mDirtyWords = dirtyWords;
    }

    public static boolean getDirtyWords() {
        return mDirtyWords;
    }

}
