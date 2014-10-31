package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class ShareOptions extends Activity {
    FireBaseMessages mFireBaseMessages;
    SharedPreferences mSharedPreferences;

    public static final int SEND_EMAIL_REQUEST_CODE = 56;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_options);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = mSharedPreferences.getString("userName", null);
        mFireBaseMessages = new FireBaseMessages();
        mFireBaseMessages.getMessagesFromFireBase(userName);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new ShareOptionsFragment()).commit();
        if(EmailMessageNextStepFragment.sentEmail){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ContinueOrNot()).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share_options, menu);
        return true;
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


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SEND_EMAIL_REQUEST_CODE){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container , new ContinueOrNot())
                    .commit();
        }
    }*/
}


