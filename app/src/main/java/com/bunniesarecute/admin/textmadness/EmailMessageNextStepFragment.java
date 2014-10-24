package com.bunniesarecute.admin.textmadness;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EmailMessageNextStepFragment extends Fragment {

    private Button doneButton;
    private EditText whereToSend;
    private String emailAddress;
    private String emailSubject;
    private String messageToSend;
    ContinueOrNot cont = new ContinueOrNot();


    public EmailMessageNextStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_emailmessage, container, false);
        emailSubject = "My MadText";
        emailAddress = "";
        Bundle bundle = this.getArguments();
        messageToSend = getActivity().getIntent().getStringExtra(MainActivity.FULL_TEXT);

        doneButton = (Button) rootView.findViewById(R.id.done_button);
        whereToSend = (EditText) rootView.findViewById(R.id.info_enter_space);

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress = whereToSend.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                intent.putExtra(Intent.EXTRA_TEXT, messageToSend);

                startActivity(Intent.createChooser(intent, "send email"));

            }
        });


        return rootView;
    }
}
