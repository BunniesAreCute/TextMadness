package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    Button insertWordButton;
    Button shareTextButton;
    EditText mainEditText;
    Button randomWordButton;
    View v;
    int code = 0;

    String mFullTextMessage = "";
    String mRandomWordFromMessage = "";
    private static boolean mDirtyWords = false;

    public static final int GENERATE_RANDOM_WORD_REQUEST = 37;

    static final String FULL_TEXT = "com.bunniesarecute.admin.textmadness.mainactivity.mFullTextMessage";
    static final String RAND_FROM_MESSAGE = "com.bunniesarecute.admin.textmadness.mainactivity.mFullTextMessage";

    public static final MainFragment newInstance(String message)
    {
        MainFragment f = new MainFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        v = inflater.inflate(R.layout.main_fragment, container, false);
        mainEditText = (EditText) v.findViewById(R.id.edit_text);
        insertWordButton = (Button) v.findViewById(R.id.generate_word_button);
        insertWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mTextBuilder.addTextToStringArrayList(mainEditText.getText().toString());
                Intent genWordIntent = new Intent(mainEditText.getContext(), WordSelect.class);
                startActivityForResult(genWordIntent, GENERATE_RANDOM_WORD_REQUEST);

                //once logic is set for getting random word, set it with mTextBuilder.addRandomWordToArrayList(*whatever the new random word is*)
            }
        });
        shareTextButton = (Button) v.findViewById(R.id.send);
        shareTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBuilder.swapOutMaskedWord(mainEditText.getText().toString());
                mFullTextMessage = TextBuilder.getUnMaskedMessage();

                // mTextBuilder.addTextToStringArrayList(mainEditText.getText().toString());
                // mTextBuilder.buildText();
                //mFullTextMessage = mTextBuilder.getTextFromMainEditText();
                Log.i("extra message", mFullTextMessage);
                Intent sendMessageIntent = new Intent(getActivity(), ShareOptions.class); //new share message activity
                sendMessageIntent.putExtra(FULL_TEXT, mFullTextMessage);

                startActivity(sendMessageIntent);
            }
        });
        randomWordButton = (Button) v.findViewById(R.id.random_word_button);
        randomWordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBuilder.addTextToStringArrayList(mainEditText.getText().toString(), code);
                mRandomWordFromMessage = TextBuilder.selectRandomWordFromMessage();
                Intent randomIntent = getActivity().getIntent().putExtra("RAND_FROM_MESSAGE", mRandomWordFromMessage);
                code++;
                //Bundle randomBundle = new Bundle();
                //randomBundle.putString("RAND_FROM_MESSAGE", randomWordReturned);
                getFragmentManager().beginTransaction().replace(R.id.main_activity, new SwapRandomWord()).commit();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mainEditText = (EditText) v.findViewById(R.id.edit_text);
//        mainEditText.setText(mainEditText.getText() + " appended str");  //  Please replace me with the real thing
        if (requestCode == GENERATE_RANDOM_WORD_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // use random word here
                mainEditText = (EditText) v.findViewById(R.id.edit_text);
                mainEditText.setText(mainEditText.getText() + " " + data.getStringExtra("RANDOM_WORD"));
                TextBuilder.addTextToStringArrayList(mainEditText.getText().toString(), code);
                code++;

                mainEditText.setSelection(mainEditText.length());
            }
        }
    }

}
