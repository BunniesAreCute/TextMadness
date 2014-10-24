package com.bunniesarecute.admin.textmadness;


import android.app.Fragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TextMessageNextStepFragment extends Fragment {

    private Button doneButton;
    private EditText whereToSend;
    private String phoneNumber;
    private String messageToSend;
    ContinueOrNot cont = new ContinueOrNot();

    public TextMessageNextStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_textmessage, container, false);
        phoneNumber = "";
        Bundle bundle = this.getArguments();
        messageToSend = getActivity().getIntent().getStringExtra(MainActivity.FULL_TEXT);

        doneButton = (Button) rootView.findViewById(R.id.done_button);
        whereToSend = (EditText) rootView.findViewById(R.id.info_enter_space);

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = whereToSend.getText().toString();
                sendTextMessage(messageToSend, phoneNumber);
                cont.startContinueFragment();

            }
        });


        return rootView;
    }

    public void sendTextMessage(String message, String phoneNumber){
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
    }
}