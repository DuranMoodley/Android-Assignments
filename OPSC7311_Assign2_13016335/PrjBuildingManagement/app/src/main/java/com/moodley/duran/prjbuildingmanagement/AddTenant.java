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

        //Call validation method to check each component user values
        if(Validation(tenantName,idNumber,numberOfTenants,contactNumber,emergencyNumber,flatNumber))
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
                Toast.makeText(this, R.string.title_tenant_added, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, R.string.title_tenant_unsuccessful, Toast.LENGTH_LONG).show();
            }

            //Close db
            objAdapter.close();
        }
    }
    //****************************************************************
    private boolean Validation(EditText tenantName, EditText idNumber, EditText numOfTenants,
                               EditText contactNum, EditText emergencyNum, EditText flatNum)
    {
        //Put all components into an array
        EditText [] arrComponents = {tenantName, idNumber,numOfTenants, contactNum , emergencyNum, flatNum};
        int counter = 0;
        boolean isValid = true;
        String message = "Incorrect Value entered at :" ;

        //Check each component for null values
        while(counter < arrComponents.length && isValid)
        {
            //if the value is null, display error message and end while loop
            if(checkNull(arrComponents[counter]))
            {
                isValid = false;
                message += arrComponents[counter].getHint().toString();
                errorMessage(message);
            }
            else
            {
                if(counter == 1)
                {
                    if(idNumber.getText().toString().length() != 11)
                    {
                        message += arrComponents[counter].getHint().toString()  + ".\nId number must be 11 digits.";
                        errorMessage(message);
                        isValid = false;
                    }
                }
                else if(counter == 3)
                {
                    if(contactNum.getText().toString().length() != 10)
                    {
                        message += arrComponents[counter].getHint().toString() + ".\nPhone number must be 10 digits.";
                        errorMessage(message);
                        isValid = false;
                    }
                }
                else if(counter == 4)
                {
                    if(emergencyNum.getText().toString().length() != 10)
                    {
                        message += arrComponents[counter].getHint().toString() + ".\nPhone number must be 10 digits.";
                        errorMessage(message);
                        isValid = false;
                    }
                }
            }
            message = "Incorrect Value entered at :" ;
            counter++;
        }
        return isValid;
    }
    //*************************************************************
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
