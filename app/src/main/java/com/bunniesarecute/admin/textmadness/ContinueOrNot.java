package com.bunniesarecute.admin.textmadness;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ContinueOrNot extends Fragment implements OnClickListener {

    private Button yesButton;
    private Button noButton;

    public ContinueOrNot(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.continue_fragment, container, false);

        yesButton = (Button) rootview.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(this);
        noButton = (Button) rootview.findViewById(R.id.no_button);
        noButton.setOnClickListener(this);

        return rootview;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes_button:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ShareOptionsFragment()).commit();
                break;
            case R.id.no_button:
                TextBuilder.wipeTheWordArray();
                getActivity().finish();
                Intent goHome = new Intent(getActivity(), MainActivity.class);
                startActivity(goHome);
                break;
        }

    }

}
