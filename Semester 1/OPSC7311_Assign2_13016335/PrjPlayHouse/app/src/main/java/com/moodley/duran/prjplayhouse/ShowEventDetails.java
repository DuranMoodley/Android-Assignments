/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays all events
Updated: 5/22/2016
ShowEventDetails.java
Assignment : 2
 */
package com.moodley.duran.prjplayhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowEventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//*************************************************************
private void displayData()
    {
        //Retrieve data from text file and fill into list view
        ArrayList objArray;
        FactoryClass objFactory = new FactoryClass(this,"events");
        objArray = objFactory.readData();
        ListView lstEvent = (ListView) findViewById(R.id.lstvEvents);
        ArrayAdapter<String> arrMenuAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,objArray);
        lstEvent.setAdapter(arrMenuAdapter);
        arrMenuAdapter.notifyDataSetChanged();
    }
}
