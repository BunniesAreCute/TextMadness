package com.bunniesarecute.admin.textmadness;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


public class ShareOptionsFragment extends Fragment implements OnClickListener{
    private ImageButton mTextMessageButton;
    private ImageButton mEmailMessageButton;
    private ImageButton mFacebookButton;
    private ImageButton mTwitterButton;
    private Bundle mBundle;

    public static final int SEND_EMAIL_REQUEST_CODE = 58;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_share_options, container, false);
        mTextMessageButton = (ImageButton) rootView.findViewById(R.id.text_message_button);
        mTextMessageButton.setOnClickListener(this);
        mEmailMessageButton = (ImageButton) rootView.findViewById(R.id.email_button);
        mEmailMessageButton.setOnClickListener(this);
        mFacebookButton = (ImageButton) rootView.findViewById(R.id.facebook_button);
        mFacebookButton.setOnClickListener(this);
        mTwitterButton = (ImageButton) rootView.findViewById(R.id.twitter_button);
        mTwitterButton.setOnClickListener(this);

        return rootView;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.text_message_button:
                TextMessageNextStepFragment textFrag = new TextMessageNextStepFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, textFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.email_button:


                EmailMessageNextStepFragment emailFrag = new EmailMessageNextStepFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.container, emailFrag)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.facebook_button:
                Toast.makeText(view.getContext(), "Facebook is terrible and won't let us share your" +
                        " message without allowing targeted ads in our app. Sorry for the inconvenience.", Toast.LENGTH_LONG).show();
                break;

            case R.id.twitter_button:
                TwitterNextStepFragment twitterFrag = new TwitterNextStepFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, twitterFrag)
                        .commit();
                break;


        }

    }

    public void popUpVersionToast(){
        Toast.makeText(getActivity().getApplicationContext(), "Coming Soon in Version 2.Dan!", Toast.LENGTH_SHORT).show();
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEND_EMAIL_REQUEST_CODE){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container , new ContinueOrNot())
                    .commit();
        }
    }*/


}
