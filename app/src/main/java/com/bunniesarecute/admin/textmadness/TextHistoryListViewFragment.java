package com.bunniesarecute.admin.textmadness;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextHistoryListViewFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

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
//        TextView messageTextView = (TextView)v.findViewById(R.id.textView);
//        messageTextView.setText(message);

        return v;
    }

}