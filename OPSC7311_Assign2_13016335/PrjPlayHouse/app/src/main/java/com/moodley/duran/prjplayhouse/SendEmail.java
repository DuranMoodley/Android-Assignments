/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : allows user to enter email message and user email application to send
Updated: 5/22/2016
SendEmail.java
Assignment : 2
 */
package com.moodley.duran.prjplayhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //***********************************************************
    public void sendEmailButtonClick(View v)
    {
        //Validation email message
        EditText emailMessage = (EditText) findViewById(R.id.edtMessageEmail);
        if(validation(emailMessage.getText().toString()))
        {
            //Create intent sender, set properties and send email via email application (gmail)
            Intent email = new Intent(android.content.Intent.ACTION_SEND);
            email.putExtra(android.content.Intent.EXTRA_SUBJECT,"Play House Update");
            email.setType("plain/text");
            email.putExtra(android.content.Intent.EXTRA_TEXT,emailMessage.getText().toString());
            startActivity(email);
        }
    }
    //***********************************************************
    private boolean validation(String message)
    {
        boolean isValid = true;

        //Check if message data is null
        if(message.isEmpty())
        {
            isValid = false;
            outputMessage();
        }
        return isValid;
    }
    //***********************************************************
    private void outputMessage()
    {
        Toast.makeText(this, R.string.title_error_email, Toast.LENGTH_SHORT).show();
    }
}
