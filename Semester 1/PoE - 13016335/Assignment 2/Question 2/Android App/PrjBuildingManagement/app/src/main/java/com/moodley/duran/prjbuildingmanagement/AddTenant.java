/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : adds a tenant to db
Updated: 5/22/2016
AddTenant.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTenant extends AppCompatActivity {

    private DatabaseAdapter objAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        objAdapter = new DatabaseAdapter(this);
    }
    //****************************************************************
    public void submitButtonClick(View v)
    {
        initialize();
    }
    //****************************************************************
    private void initialize()
    {
        //Initialize objects and components
        Tenant objTenant;
        objAdapter.open();
        EditText tenantName = (EditText) findViewById(R.id.edtName);
        EditText idNumber = (EditText) findViewById(R.id.edtIdNumber);
        EditText numberOfTenants = (EditText) findViewById(R.id.edtNumberOfOccupants);
        EditText contactNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        EditText emergencyNumber = (EditText) findViewById(R.id.edtEmergencyContact);
        EditText flatNumber = (EditText) findViewById(R.id.edtFlatNumber);

        assert tenantName != null;
        assert numberOfTenants != null;
        assert contactNumber != null;
        assert emergencyNumber != null;
        assert flatNumber != null;
        assert idNumber != null;
        //Call validation method to check each component user values
        if(Validation(tenantName.getText().toString(),idNumber.getText().toString(),
                      numberOfTenants.getText().toString(),contactNumber.getText().toString(),
                      emergencyNumber.getText().toString(),flatNumber.getText().toString()))
        {
            //After all is valid, open db connection, and parse values into object
            objTenant = new Tenant(tenantName.getText().toString(),
                                   idNumber.getText().toString(),
                                   Integer.parseInt(numberOfTenants.getText().toString()),
                                   contactNumber.getText().toString(),
                                   emergencyNumber.getText().toString(),
                                   Integer.parseInt(flatNumber.getText().toString()));

            //call getters, and parse into insert method of db class
            if(objAdapter.insertTenant(objTenant.getTenantName(),objTenant.getIdNumber(),objTenant.getNumOfOccupants(),
                                      objTenant.getContactNumber(),objTenant.getEmergencyContact(),objTenant.getFlatNumber()) >= 0)
            {
                userMessage(R.string.title_tenant_added);
            }
            else
            {
                userMessage(R.string.title_tenant_unsuccessful);
            }

            //Close db
            objAdapter.close();
        }
    }
    //****************************************************************
    private boolean Validation(String tenantName, String idNumber, String numOfTenants,
                               String contactNum, String emergencyNum, String flatNum)
    {
        //Put all components into an array
        String [] arrComponents = {tenantName, idNumber,numOfTenants, contactNum , emergencyNum, flatNum};
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
                if(counter == 1)
                {
                    if(idNumber.length() != 11)
                    {
                        userMessage(R.string.title_error_id_digits);
                        isValid = false;
                    }
                }
                else if(counter == 3)
                {
                    if(contactNum.length() != 10)
                    {
                        userMessage(R.string.title_cell_number_length);
                        isValid = false;
                    }
                }
                else if(counter == 4)
                {
                    if(emergencyNum.length() != 10)
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
    //*************************************************************
    private boolean checkNull(String component)
    {
        //Check if the component is empty
        boolean isNull = false;
        if(component.isEmpty())
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
