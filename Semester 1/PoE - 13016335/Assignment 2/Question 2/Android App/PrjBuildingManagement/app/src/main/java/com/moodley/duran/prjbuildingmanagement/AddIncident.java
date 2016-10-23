/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : allows user to add information to database table
Updated: 5/22/2016
AddIncident.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddIncident extends AppCompatActivity {

    private EditText date;
    private EditText time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incident);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = (EditText) findViewById(R.id.edtDate);
        time = (EditText) findViewById(R.id.edtTime);
    }
    //******************************************************
    public void submitIncidentClick(View v)
    {
        //Call db adapter , open the database and insert the necessary fields
        DatabaseAdapter objAdapter = new DatabaseAdapter(this);
        EditText incidentDescription = (EditText) findViewById(R.id.edtIncidentDescription);
        EditText idNumber = (EditText) findViewById(R.id.edtTenantIdNumb);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgbRating);
        RadioButton radMinor = (RadioButton) findViewById(R.id.rdMinor);
        RadioButton radMajor = (RadioButton) findViewById(R.id.rdMajor);
        String problemIndicator = null;

        assert radioGroup != null;
        assert radMinor != null;
        if(radioGroup.getCheckedRadioButtonId() == radMinor.getId())
        {
            problemIndicator = radMinor.getText().toString();
        }
        else {
            assert radMajor != null;
            if(radioGroup.getCheckedRadioButtonId() == radMajor.getId())
            {
                problemIndicator = radMajor.getText().toString();
            }
        }
        objAdapter.open();

        //Validation input, check for null values
        if(Validation(incidentDescription,idNumber,problemIndicator))
        {
            assert incidentDescription != null;
            assert idNumber != null;
            if (objAdapter.insertIncident(incidentDescription.getText().toString(), idNumber.getText().toString(),
                    date.getText().toString(), time.getText().toString(), problemIndicator) >= 0)
            {
                Toast.makeText(this, R.string.title_incident_added, Toast.LENGTH_LONG).show();
                clearFields(incidentDescription, idNumber);
            }
            else
            {
                Toast.makeText(this, R.string.title_incident_unsuccessful, Toast.LENGTH_LONG).show();
            }
        }
        //Close db
        objAdapter.close();
    }
    //******************************************************
    public void dateButton(View v)
    {
        //Open the date picker dialog when the date edit text is selected
        date.setEnabled(false);
        final Calendar caldate = Calendar.getInstance();
        int year = caldate.get(Calendar.YEAR);
        int month = caldate.get(Calendar.MONTH);
        int day = caldate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String userDate = dayOfMonth + "-" + String.valueOf(monthOfYear + 1) + "-" + year;
                date.setText(userDate);
            }
        },year,month,day);

        datePicker.show();
    }
    //******************************************************
    public void timeButton(View v)
    {
        //Open the time picker dialog when the date edit text is selected
        time.setEnabled(false);
        final Calendar caltime = Calendar.getInstance();
        int hour = caltime.get(Calendar.HOUR_OF_DAY);
        int minute = caltime.get(Calendar.MINUTE);

        final TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                String timeOfDay = hourOfDay + " " + minute;
                time.setText(timeOfDay);
            }
        },hour,minute,false);

        timePicker.show();
    }
    //******************************************************
    public void showIncidentButtonClick(View v)
    {
        //Opens new activity
        Intent objNewActivity = new Intent(AddIncident.this,ShowIncidents.class);
        startActivity(objNewActivity);
    }
    //******************************************************
    private void clearFields(EditText description, EditText tenantId)
    {
        description.setText("");
        tenantId.setText("");
        date.setText("");
        time.setText("");
    }
    //******************************************************
    private boolean Validation(EditText incDes, EditText id , String incidentRating)
    {
        //Display error messages and check for null values
        boolean isValid = true;
        if(incDes.getText().toString().isEmpty())
        {
            isValid = false;
            errorMessage(R.string.title_error_incidents_description);
        }
        else if(id.getText().toString().isEmpty())
        {
            isValid = false;
            errorMessage(R.string.title_error_tenant_incident_id);
        }
        else if(id.getText().length() != 11)
        {
            isValid = false;
            errorMessage(R.string.title_error_id_digits);
        }
        else if(date.getText().toString().isEmpty())
        {
            isValid = false;
            errorMessage(R.string.title_error_date);
        }
        else if(time.getText().toString().isEmpty())
        {
            isValid = false;
            errorMessage(R.string.title_error_time);
        }
        else if(incidentRating.isEmpty())
        {
            isValid = false;
            errorMessage(R.string.title_error_radio_button);
        }

        return isValid;
    }
    //******************************************************
    private void errorMessage(int message)
    {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    //*************************************************************
}
