/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : logs in a user to program
Updated: 5/22/2016
Login.java
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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    //*******************************************************************
    public void buttonClicked(View v)
    {
        //Takes user input Credentials
        Intent newActivity = null;
        EditText nameOfUser = (EditText) findViewById(R.id.edtUsername);
        EditText passwordOfUser = (EditText) findViewById(R.id.edtPassword);
        String validUser = "Duran";
        String validPassword = "Password1";

        //Checks of the users data is correct
        if(validUser.equals(nameOfUser.getText().toString().trim()) && validPassword.equals(passwordOfUser.getText().toString().trim()))
        {
            //Opens a activity if correct
            if(v.getId() == R.id.btnLogin)
            {
                newActivity = new Intent(Login.this,MenuScreenPlayHouse.class);
                finish();
            }
            startActivity(newActivity);
        }
        else
        {
            Toast.makeText(Login.this, R.string.title_incorrect_password, Toast.LENGTH_SHORT).show();
        }
    }
    //*******************************************************************
}
