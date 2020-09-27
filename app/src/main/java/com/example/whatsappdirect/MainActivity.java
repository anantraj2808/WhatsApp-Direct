package com.example.whatsappdirect;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    EditText phoneNumberET,messageET;
    ImageView backButton;
    CountryCodePicker ccp;
    Button redirectBtn,sendMessageBtn;
    String countryCode,phoneNumber,message,name;
    private static final String FILE_NAME = "WhatsApp_file" ;
    private static final String NAME_KEY = "UserName_key";
    private static final String FIRST_TIME_SHOW_KEY = "FirstTimeShow_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberET = findViewById(R.id.phoneNumberET);
        messageET = findViewById(R.id.messageET);
        backButton = findViewById(R.id.back);
        ccp = findViewById(R.id.ccp);
        redirectBtn = findViewById(R.id.redirectBtn);
        sendMessageBtn = findViewById(R.id.sendMessageBtn);
        countryCode = "";
        phoneNumber = "";
        message = "";
        name = "";

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    ccp.registerPhoneNumberTextView(phoneNumberET);
                    if (messageET.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Type a message or click Redirect button", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        message = messageET.getText().toString();
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        "https://api.whatsapp.com/send?phone=" + ccp.getFullNumberWithPlus() + "&text=" + message
                                )));
                    }
                }
            }
        });

        redirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberET.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    ccp.registerPhoneNumberTextView(phoneNumberET);
                    message = messageET.getText().toString();
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://api.whatsapp.com/send?phone=" + ccp.getFullNumberWithPlus() + "&text=" + message
                            )));

                }
            }
        });
        //makeAlertBox();
    }

    AlertDialog alert;
    public void makeAlertBox(){

        // load the dialog_promt_user.xml layout and inflate to view
        LayoutInflater layoutinflater = LayoutInflater.from(getApplicationContext());
        View promptUserView = layoutinflater.inflate(R.layout.dailog_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setCancelable(false);


        final EditText userAnswer = (EditText) promptUserView.findViewById(R.id.username);

        alertDialogBuilder.setTitle("What's your name?");

        // prompt for username
        alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Shared Preferences :
                SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
                editor.putString(NAME_KEY,userAnswer.getText().toString());
                editor.putInt(FIRST_TIME_SHOW_KEY,1);
                editor.apply();

                //setTextInButton();
            }
        });

        // all set and time to build and show up!
        alert = alertDialogBuilder.create();
        alert.show();
    }
}
