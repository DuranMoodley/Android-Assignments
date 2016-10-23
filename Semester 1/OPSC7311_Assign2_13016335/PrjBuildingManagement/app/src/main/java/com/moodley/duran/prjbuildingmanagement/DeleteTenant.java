/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : deletes a particular tenant from db
Updated: 5/22/2016
DeleteTenant.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteTenant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_tenant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //*********************************************************
    public void deleteButtonClick(View v)
    {
        //Opens the db
        EditText idNumber = (EditText) findViewById(R.id.edtDltIdNumber);
        DatabaseAdapter objAdapter = new DatabaseAdapter(this);
        objAdapter.open();

        if (idNumber != null)
        {
            //Validates the id number, check for null
            if(!idNumber.getText().toString().isEmpty())
            {
                if(idNumber.length() != 11)
                {
                    errorMessage(R.string.title_error_id_digits);
                }
                else
                {
                    //Deletes record from db, returns the result
                    if(objAdapter.deleteTenant(Long.parseLong(idNumber.getText().toString())))
                    {
                        Toast.makeText(this,R.string.title_tenant_deleted,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this,R.string.title_error_not_deleted,Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                errorMessage(R.string.title_error_tenant_incident_id);
            }
        }
        objAdapter.close();
    }
    //************************************************************
    private void errorMessage(int message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    //*************************************************************
}
