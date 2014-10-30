package com.bunniesarecute.admin.textmadness;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TextMessageNextStepFragment extends Fragment {

    private Button doneButton;
    private Button contactLookupButton;
    private EditText whereToSend;
    private String phoneNumber;
    private String messageToSend;

    private final int PICK_CONTACT = 42;
    ContinueOrNot cont = new ContinueOrNot();

    private String contactID;

    public TextMessageNextStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_textmessage, container, false);
        phoneNumber = "";
        messageToSend = getActivity().getIntent().getStringExtra(MainActivity.FULL_TEXT);
        Log.d("TextMessageNextStepFragment", "messageToSend is " + messageToSend);

        doneButton = (Button) rootView.findViewById(R.id.done_button);
        contactLookupButton = (Button) rootView.findViewById(R.id.contact_lookup_button);
        whereToSend = (EditText) rootView.findViewById(R.id.info_enter_space);

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = whereToSend.getText().toString();
                sendTextMessage(messageToSend, phoneNumber);

                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ContinueOrNot())
                        .commit();

            }
        });

        contactLookupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });


        return rootView;
    }

    public void sendTextMessage(String message, String phoneNumber){
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == ShareOptions.RESULT_OK) {

                    Uri contactData = data.getData();


//                    Bundle extras = data.getExtras();
                    String id = contactData.getLastPathSegment();
                    Cursor cursorID = getActivity().getContentResolver().query(contactData,
                            new String[]{ContactsContract.Contacts._ID},
                            null, null, null);

                    if (cursorID.moveToFirst()) {

                        contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
                    }

                    cursorID.close();

                    Log.d("***testing phone number***", "Contact ID: " + contactID);

                    // Using the contact ID now we will get contact phone number
                    Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                            new String[]{contactID},
                            null);

                    String contactNumber = null;
                    if (cursorPhone.moveToFirst()) {
                        contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }

                    cursorPhone.close();

                    Log.d("***testing phone number***", "Contact Phone Number: " + contactNumber);
                    whereToSend = (EditText) getActivity().findViewById(R.id.info_enter_space);
                    whereToSend.setText(contactNumber);

                    break;
                }

        }
    }

}