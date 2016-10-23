/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays help information to user
Updated: 5/22/2016
Help.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

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
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Read help file data and display contents
        FactoryClass objFactory = new FactoryClass(this);
        TextView helpData = (TextView) findViewById(R.id.tvHelp);
        assert helpData != null;
        helpData.setText(objFactory.readDate("help"));
    }
}
