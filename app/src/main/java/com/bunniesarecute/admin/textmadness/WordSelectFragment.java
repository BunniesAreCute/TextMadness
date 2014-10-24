package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bunniesarecute.admin.textmadness.DictionaryAPI.DictionaryInterface;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by katecatlin on 10/21/14.
 */

public class WordSelectFragment extends Fragment implements DictionaryInterface {
    POSAdapter mAdapter;
    ArrayList<String> dirtyWordList;
    Random randomWordSelector;



    public WordSelectFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAdapter = new POSAdapter(getActivity(), WordSelect.posObjectArrayList);
        dirtyWordList = new ArrayList<String>();
        dirtyWordList.add("cock");
        dirtyWordList.add("pussy");
        dirtyWordList.add("boobs");
        dirtyWordList.add("fuck");
        dirtyWordList.add("sex");

    }

    @Override
    public void onStart () {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_word_select, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview);

        final DictionaryAPI anApi = new DictionaryAPI();

        anApi.setDictionaryInterface(this);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowID) {
                POSObject thisObject = mAdapter.getItem(position);
                String posSelected = thisObject.getpOS();
                randomWordSelector = new Random();
                String dirtyWord = dirtyWordList.get(randomWordSelector.nextInt(5));
                Log.i("whatword", dirtyWord);
                anApi.execute(posSelected, dirtyWord);
            }

        });


        return rootView;
    }

    @Override
    public void foundAWord(String word){

        if(word == null){
            word = "skittles";
        }

        Intent intent = new Intent();

        intent.putExtra("RANDOM_WORD", word);

        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }


}