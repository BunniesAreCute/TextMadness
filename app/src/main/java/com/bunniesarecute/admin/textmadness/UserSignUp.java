package com.bunniesarecute.admin.textmadness;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UserSignUp extends Fragment {

    private EditText userNameInput;
    private Button doneButton;
    private String userNameEntered;
    SharedPreferences mSharedPreferences;
    Editor mEditor;
    User aUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_sign_up, container, false);

        userNameEntered = "";
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        mEditor = mSharedPreferences.edit();

        userNameInput = (EditText) rootView.findViewById(R.id.user_name_field);
        doneButton = (Button) rootView.findViewById(R.id.done_button_user_sign_up);
        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameEntered = userNameInput.getText().toString();
                aUser = new User(userNameEntered);
                mEditor.putString("userName", userNameEntered);
                mEditor.commit();
                Intent startMain = new Intent(getActivity(), MainActivity.class);
                startActivity(startMain);

            }
        });

        return rootView;
    }





}
