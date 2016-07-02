/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : send sms
Updated: 5/22/2016
SendSms.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendSms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //***************************************************
    public void sendSmsButtonClick(View v)
    {
        //Gets the message and phone number
        EditText cellNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        EditText message = (EditText) findViewById(R.id.edtMessage);

        //Validates the input
        if(validation(cellNumber.getText().toString(),message.getText().toString()))
        {
            //Instantiates sms manager and set the necessary properties
            SmsManager objSmsManager = SmsManager.getDefault();
            objSmsManager.sendTextMessage(cellNumber.getText().toString(),null,message.getText().toString(),null,null);
            Toast.makeText(this,R.string.title_sms_sent,Toast.LENGTH_LONG).show();

            cellNumber.setText("");
            message.setText("");
        }
    }
    //***************************************************
    private boolean validation(String cell, String messageSms)
    {
        boolean isValid = true;

        //Looks for null values and contains cell number
        if(cell.isEmpty() || messageSms.isEmpty())
        {
            isValid = false;
            Toast.makeText(this,R.string.title_sms_enter_text,Toast.LENGTH_LONG).show();
        }
        else if(cell.length() != 10)
        {
            isValid = false;
            Toast.makeText(this,R.string.title_cell_number_length,Toast.LENGTH_LONG).show();
        }
        return isValid;
    }
}
