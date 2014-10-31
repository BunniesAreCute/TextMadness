package com.bunniesarecute.admin.textmadness;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerActivity extends FragmentActivity implements ActionBar.TabListener {

    SharedPreferences mSharedPreferences;
    FireBaseMessages mFireBaseMessages;
    String userName;
    private static boolean mDirtyWords = false;

    String[] tabTitles = new String[] {"Mad Text", "Text History"};
    ActionBar actionBar;
    ViewPager pager;

    MyPageAdapter pageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_pager);

        Firebase.setAndroidContext(this);
        mFireBaseMessages = new FireBaseMessages();


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        optionalNewUser();
        userName = mSharedPreferences.getString("userName", null);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabTitles) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();
//
        fList.add(MainFragment.newInstance("Fragment 1"));
        fList.add(TextHistoryListViewFragment.newInstance("Fragment 2"));
//        fList.add(MyFragment.newInstance("Fragment 3"));

        return fList;
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    public void optionalNewUser(){
        if(mSharedPreferences.getString("userName", null) == null){
            Intent newUser = new Intent(this, UserAccount.class);
            startActivity(newUser);
        }
    }

    public void setDirtyWords(boolean dirtyWords) {
        mDirtyWords = dirtyWords;
    }

    public static boolean getDirtyWords() {
        return mDirtyWords;
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
}
