package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by admin on 10/21/14.
 */
public class SplashActivity extends Activity {
    private GifAnimationDrawable little, big;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animateBunny();

        int secondsDelayed = 2;
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
//            }
//        }, secondsDelayed * 1000);

        startActivity(new Intent(this, MainViewPagerActivity.class));
    }



    private void animateBunny() {
        ImageView imageview = (ImageView)findViewById(R.id.loading_gif);
        try{
            android.util.Log.v("GifAnimationDrawable", "===>One");
            little = new GifAnimationDrawable(getResources().openRawResource(R.raw.bunny));
            little.setOneShot(true);
            android.util.Log.v("GifAnimationDrawable", "===>Two");
            imageview.setImageDrawable(little);
            android.util.Log.v("GifAnimationDrawable", "===>Three");
            big = new GifAnimationDrawable(getResources().openRawResource(R.raw.bunny));
            big.setOneShot(true);
            android.util.Log.v("GifAnimationDrawable", "===>Four");
        }catch(IOException ioe){
        }
    }
}
