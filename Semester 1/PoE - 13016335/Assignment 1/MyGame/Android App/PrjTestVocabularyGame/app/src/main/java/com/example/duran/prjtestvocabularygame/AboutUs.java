/*
AboutUs.java
Read data from a text file and display information to the user
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */

package com.example.duran.prjtestvocabularygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Use factory class methods, to read text file
        //Display data to the user
        FactoryClass objFactory = new FactoryClass(this);
        TextView aboutUsText = (TextView) findViewById(R.id.tvAbout);
        if (aboutUsText != null)
        {
            aboutUsText.setText(objFactory.readDate("aboutUs"));
        }
    }
    //***************************************************
}
