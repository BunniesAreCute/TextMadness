package com.bunniesarecute.admin.textmadness;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TextHistoryListViewFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    ArrayAdapter<String> mMessageHistoryAdapter;
    ArrayList<String> mMessagesList;
    FireBaseMessages mFireBaseMessages;
    SharedPreferences mSharedPreferences;


    public static final TextHistoryListViewFragment newInstance(String message)
    {
        TextHistoryListViewFragment f = new TextHistoryListViewFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.text_history_listview, container, false);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mFireBaseMessages = new FireBaseMessages();
        mFireBaseMessages.getMessagesFromFireBase(mSharedPreferences.getString("userName", null));
        mMessagesList = (ArrayList<String>) mFireBaseMessages.getArrayListOfMessages();



        mMessageHistoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mMessagesList);
        mMessageHistoryAdapter.addAll(mMessagesList);
        final ListView historyList = (ListView) v.findViewById(R.id.message_list_view);

        historyList.setAdapter(mMessageHistoryAdapter);
//        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
//        messageTextView.setText(message);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mMessageHistoryAdapter.notifyDataSetChanged();
        return v;
    }

}