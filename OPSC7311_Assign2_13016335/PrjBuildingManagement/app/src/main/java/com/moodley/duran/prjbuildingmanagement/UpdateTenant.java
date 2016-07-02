/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : allows user to update fields from db
Updated: 5/22/2016
UpdateTenant.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTenant extends AppCompatActivity {

    private EditText tenantName;
    private EditText numOccupants;
    private EditText contactNum;
    private EditText emergencyContact;
    private EditText flatNum;
    private EditText idNumber;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tenant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //****************************************************************
    public void searchButtonClick(View v)
    {
        idNumber = (EditText) findViewById(R.id.edtId);
        Cursor getTenantCursor;

        //Validate text from user
        if(!idNumber.getText().toString().isEmpty())
        {
            if(idNumber.length() != 11)
            {
                outputMessage("Incorrect Number of Digits Entered in Id Number Field");
            }
            else
            {
                //If valid, open db and get the tenant details using id number
                DatabaseAdapter objAdapter = new DatabaseAdapter(this);
                objAdapter.open();
                getTenantCursor = objAdapter.getTenant(Long.parseLong(idNumber.getText().toString()));

                //Displays result of the search
                if(getTenantCursor.getCount() > 0)
                {
                    displayTenantDetails(getTenantCursor);
                }
                else
                {
                    outputMessage("Id Could not be Found. Tenant does not Exist");
                }
                objAdapter.close();
            }
        }
        else
        {
            outputMessage("Please Enter Id Number");
        }
    }
    //****************************************************************
    private boolean validation()
    {
        EditText [] arrComponents = {tenantName,numOccupants,contactNum,emergencyContact,flatNum};
        int counter = 0;
        boolean isValid = true;
        String errorMessage = "Please Enter a value at: " ;

        //Check each component for null values
        while(counter < arrComponents.length && isValid)
        {
            //if the value is null, display error message and end while loop
            if(checkNull(arrComponents[counter]))
            {
                isValid = false;
                errorMessage += arrComponents[counter].getHint().toString();
                errorMessage(errorMessage);
            }
            else
            {
                //Checks number of digits
                if(counter == 2)
                {
                    if(contactNum.getText().toString().length() != 10)
                    {
                        isValid = false;
                        errorMessage += arrComponents[counter].getHint().toString() + ".\nPhone number must be 10 digits.";
                        errorMessage(errorMessage);
                    }
                }
                else if(counter == 3)
                {
                    if(emergencyContact.getText().toString().length() != 10)
                    {
                        errorMessage += arrComponents[counter].getHint().toString() + ".\nPhone number must be 10 digits.";
                        errorMessage(errorMessage);
                        isValid = false;
                    }
                }
            }
            counter++;
        }

        return isValid;
    }
    //****************************************************************
    private void outputMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //****************************************************************
    private void displayTenantDetails(Cursor curTenant)
    {
        //Make edit texts visible with data
        initialize();
        setVisible(tenantName);
        setVisible(numOccupants);
        setVisible(contactNum);
        setVisible(emergencyContact);
        setVisible(flatNum);

        //Display data
        tenantName.setText(curTenant.getString(0));
        numOccupants.setText(curTenant.getString(2));
        contactNum.setText(curTenant.getString(3));
        emergencyContact.setText(curTenant.getString(4));
        flatNum.setText(curTenant.getString(5));
    }
    //****************************************************************
    private void initialize()
    {
        tenantName = (EditText) findViewById(R.id.edtName);
        numOccupants = (EditText) findViewById(R.id.edtNumOccupants);
        contactNum = (EditText) findViewById(R.id.edtContactNum);
        emergencyContact = (EditText) findViewById(R.id.edtEmergency);
        flatNum = (EditText) findViewById(R.id.edtFlatNum);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        if (btnUpdate != null)
        {
            btnUpdate.setVisibility(View.VISIBLE);
        }
    }
    //****************************************************************
    private void setVisible(EditText edt)
    {
        edt.setVisibility(View.VISIBLE);
    }
    //****************************************************************
    public void updateButtonClick(View v)
    {
        //Updates the record when button is clicked
        DatabaseAdapter objAdapter = new DatabaseAdapter(this);
        objAdapter.open();
        Tenant objTenant ;
        if(validation())
        {
            objTenant = new Tenant(tenantName.getText().toString(),
                                   idNumber.getText().toString(),
                                   Integer.parseInt(numOccupants.getText().toString()),
                                   contactNum.getText().toString(),
                                   emergencyContact.getText().toString(),
                                   Integer.parseInt(flatNum.getText().toString()));

            if(objAdapter.updateTenant(Long.parseLong(objTenant.getIdNumber()), objTenant.getTenantName(),
                                    objTenant.getNumOfOccupants() , objTenant.getContactNumber(), objTenant.getEmergencyContact(),
                                    objTenant.getFlatNumber()))
            {
                outputMessage("Update Successful");
            }
            else{
                outputMessage("Update Unsuccessfully");
            }
        }
    }
    //****************************************************************
    private boolean checkNull(EditText componentName)
    {
        //Check if the component is empty
        boolean isNull = false;
        if(componentName.getText().toString().isEmpty())
        {
            isNull = true;
        }
        return isNull;
    }
    //*************************************************************
    private void errorMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    //*************************************************************
}
