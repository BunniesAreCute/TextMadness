package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class ShareOptions extends Activity  implements OnClickListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_options);
        getFragmentManager().beginTransaction().add(R.id.container, new ShareOptionsFragment()).commit();

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


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.text_message_button:
                TextMessageNextStepFragment textFrag = new TextMessageNextStepFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, textFrag)
                        .commit();
                break;

            case R.id.email_button:

                EmailMessageNextStepFragment emailFrag = new EmailMessageNextStepFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, emailFrag)
                        .commit();
                break;

            case R.id.facebook_button:
                popUpVersionToast();
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
        Toast.makeText(this, "Coming Soon in Version 2.Dan!", Toast.LENGTH_SHORT).show();
    }


}
