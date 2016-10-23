/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : Adds a record to a text file
Updated: 5/22/2016
AddActivity.java
Assignment : 2
 */
package com.moodley.duran.prjplayhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //*************************************************************
    public void saveButtonClick(View v)
    {
        initialization();
    }
    //*************************************************************
    private void initialization()
    {
        //Declares all major objects
        Events objEvents ;
        FactoryClass objFactory = new FactoryClass(this,"events");
        EditText director = (EditText) findViewById(R.id.edtDirectorName);
        EditText phone = (EditText) findViewById(R.id.edtPhone);
        EditText email = (EditText) findViewById(R.id.edtEmail);
        EditText eventName = (EditText) findViewById(R.id.edtEventName);
        EditText amountPaid = (EditText) findViewById(R.id.edtAmountPaid);
        EditText eventDescription = (EditText) findViewById(R.id.edtDescription);

        //Checks if components are validated and Check for null values
        if(Validation(director.getText().toString(),phone.getText().toString(),email.getText().toString(),
                      eventName.getText().toString(),amountPaid.getText().toString(),eventDescription.getText().toString()))
        {
            //Put each value into objects contructor
            objEvents = new Events(director.getText().toString(),
                                   phone.getText().toString(),
                                   email.getText().toString(),
                                   eventName.getText().toString(),
                                   Double.parseDouble(amountPaid.getText().toString()),
                                   eventDescription.getText().toString());

            //Call factor class method to write data to internal storage
            objFactory.writeDate(objEvents.toString());
            Toast.makeText(this,R.string.title_add_successful,Toast.LENGTH_SHORT).show();
        }
    }
    //*************************************************************
    public boolean Validation(String nameDirector, String contactNumber, String emailAdd,
                               String nameEvent, String amtPaid, String description)
    {
        //Put all components into an array
        String [] arrComponents = {nameDirector,contactNumber,emailAdd,nameEvent,amtPaid,description};
        int counter = 0;
        boolean isValid = true;

        //Check each component for null values
        while(counter < arrComponents.length && isValid)
        {
            //if the value is null, display error message and end while loop
            if(checkNull(arrComponents[counter]))
            {
                isValid = false;
                errorMessage(R.string.title_fields_empty);
            }
            else
            {
               if(counter == 1)
               {
                   if(contactNumber.length() !=10)
                   {
                       isValid = false;
                       errorMessage(R.string.title_incorrect_digits);
                   }
               }
            }
            counter++;
        }
        return isValid;
    }
    //*************************************************************
    private boolean checkNull(String componentName)
    {
        //Check if the component is empty
        boolean isNull = false;
       if(componentName.isEmpty())
       {
          isNull = true;
       }
       return isNull;
    }
    //*************************************************************
    private void errorMessage(int message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    //*************************************************************
}
