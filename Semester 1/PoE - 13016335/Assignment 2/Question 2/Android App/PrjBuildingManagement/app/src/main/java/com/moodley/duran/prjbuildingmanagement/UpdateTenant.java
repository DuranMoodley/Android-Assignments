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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tenant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //****************************************************************
    public void searchButtonClick(View v)
    {
        idNumber = (EditText) findViewById(R.id.edtId);
        Cursor getTenantCursor;

        //Validate text from user
        assert idNumber != null;
        if(!idNumber.getText().toString().isEmpty())
        {
            if(idNumber.length() != 11)
            {
                userMessage(R.string.title_error_id_digits);
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
                   userMessage(R.string.title_error_id_not_found);
                }
                objAdapter.close();
            }
        }
        else
        {
            userMessage(R.string.title_error_tenant_incident_id);
        }
    }
    //****************************************************************
    private boolean validation()
    {
       String [] arrComponents = {tenantName.getText().toString(),numOccupants.getText().toString(),contactNum.getText().toString(),
                                  emergencyContact.getText().toString(),flatNum.getText().toString()};
        int counter = 0;
        boolean isValid = true;

        //Check each component for null values
        while(counter < arrComponents.length && isValid)
        {
            //if the value is null, display error message and end while loop
            if(checkNull(arrComponents[counter]))
            {
                isValid = false;
                userMessage(R.string.title_null_error);
            }
            else
            {
                //Checks number of digits
                if(counter == 2)
                {
                    if(contactNum.getText().toString().length() != 10)
                    {
                        isValid = false;
                        userMessage(R.string.title_cell_number_length);
                    }
                }
                else if(counter == 3)
                {
                    if(emergencyContact.getText().toString().length() != 10)
                    {
                        userMessage(R.string.title_cell_number_length);
                        isValid = false;
                    }
                }
            }
            counter++;
        }

        return isValid;
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
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
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
                userMessage(R.string.title_activity_update_successful);
            }
            else
            {
                userMessage(R.string.title_activity_update_unsuccessful);
            }
        }
    }
    //****************************************************************
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
    private void userMessage(int message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    //*************************************************************
}
