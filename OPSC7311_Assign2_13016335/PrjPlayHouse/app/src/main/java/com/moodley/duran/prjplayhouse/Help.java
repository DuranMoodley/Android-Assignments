/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : shows help file date to user
Updated: 5/22/2016
Help.java
Assignment : 2
 */
package com.moodley.duran.prjplayhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Instantiates class and calls the read data method to retrieve help file data
        FactoryClass objFactory = new FactoryClass(this);
        TextView help = (TextView) findViewById(R.id.tvHelp);
        if (help != null)
        {
            help.setText(objFactory.readDate("help"));
        }
    }

}
