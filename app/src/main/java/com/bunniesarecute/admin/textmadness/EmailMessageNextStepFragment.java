package com.bunniesarecute.admin.textmadness;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;
import java.util.Iterator;
import java.util.Set;

public class EmailMessageNextStepFragment extends Fragment {

    private Button doneButton;
    private Button contactLookupButton;
    private EditText whereToSend;
    private String emailAddress;
    private String emailSubject;
    private String messageToSend;
    private final int PICK_CONTACT = 42;
    private static final int REQUEST_CONTACT = 43;
    private String LOG_CAT = "EmailMessageNextStepFragment";
    ContinueOrNot cont = new ContinueOrNot();

    private Cursor cursor;
    private String contactID;

    public EmailMessageNextStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_CAT, "entered teh fragment");
        View rootView = inflater.inflate(R.layout.fragment_share_emailmessage, container, false);
        emailSubject = "My MadText";
        emailAddress = "";
        messageToSend = getActivity().getIntent().getStringExtra(MainActivity.FULL_TEXT);
        Log.d(LOG_CAT, "In the onCreateView, messageToSend is " + messageToSend);
        doneButton = (Button) rootView.findViewById(R.id.done_button);
        contactLookupButton = (Button) rootView.findViewById(R.id.contact_lookup_button);
        whereToSend = (EditText) rootView.findViewById(R.id.info_enter_space);

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                emailAddress = whereToSend.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                Log.d(LOG_CAT, "email messageToSend is " + messageToSend);
                intent.putExtra(Intent.EXTRA_TEXT, messageToSend);
                startActivity(Intent.createChooser(intent, "send email"));
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ContinueOrNot())
                        .commit();


            }
        });

        contactLookupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_CAT, "entered the contactLookupBotton");
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(LOG_CAT, "entered the onActivityResult");

//        if (resultCode == Activity.RESULT_OK) return;
//        if (requestCode == REQUEST_CONTACT) {
            Log.d(LOG_CAT, "entered the REQUEST_CONTACT");
            //Get data
            Uri contactUri = data.getData();
            //specify wich fields you want your query to return values for:
            //perform query. The contactUri is like a "where" clause here
            Cursor cursor = getActivity().getContentResolver().query(contactUri, null, null, null, null);
            ContentResolver contentResolver = getActivity().getContentResolver();
            if (cursor.getCount() !=0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    Cursor emailCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        Log.d(LOG_CAT, "email: " + email);

                        whereToSend = (EditText) getActivity().findViewById(R.id.info_enter_space);
                        whereToSend.setText(email);
                        Log.d(LOG_CAT, "where to send: " + whereToSend.getText());

                        break;
                    }
                    emailCursor.close();
                }
                cursor.close();
                return;
            }
        }

//        }
}
