package com.bunniesarecute.admin.textmadness;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bunniesarecute.admin.textmadness.RandomizerDictionaryAPI.RandomDictionaryInterface;

public class SwapRandomWord extends Fragment implements RandomDictionaryInterface{

    RandomizerDictionaryAPI mRandomizerDictionaryAPI = new RandomizerDictionaryAPI();




    public SwapRandomWord() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String wordToSwap = getActivity().getIntent().getStringExtra("RAND_FROM_MESSAGE");
        mRandomizerDictionaryAPI.setRandomDictionaryInterface(this);
        mRandomizerDictionaryAPI.execute(wordToSwap);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_swap_random_word, container, false);

        return rootView;
    }


    @Override
    public void randomWord(String randomWord) {
        TextBuilder.replaceSwappedWordWithRandom(randomWord);
        getFragmentManager().beginTransaction().detach(this);
    }
}
