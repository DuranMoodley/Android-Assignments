/*
HowToPlay.java
Gives the user instructions on how to plays the game
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

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Use factory class methods, to read text file
        //Display data to the user
        FactoryClass objFactory = new FactoryClass(this);
        TextView text = (TextView) findViewById(R.id.txtData);
        assert text != null;
        text.setText(objFactory.readDate("howToPlay"));
    }
    //******************************************************************************
}
