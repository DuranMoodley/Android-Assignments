/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays all incidents to the user
Updated: 5/22/2016
ShowIncidents.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ShowIncidents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_incidents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showIncidents();
    }
    //***************************************************
    private void showIncidents()
    {
        //Opens db and retrieves all records
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        ArrayList objIncidents = new ArrayList();
        Cursor cursor = dbAdapter.getAllIncidents();

        //Move through the cursor and add each field to array list
        if (cursor.moveToFirst())
        {
            do {
                objIncidents.add(cursor.getString(0) + "\n" +
                        cursor.getString(1) + "\n" +
                        cursor.getString(2) + "\n" +
                        cursor.getString(3)+ "\n" +
                        cursor.getString(4)) ;
            }
            while (cursor.moveToNext());
        }
        DisplayIncidents(objIncidents);
        dbAdapter.close();
    }
    //***************************************************
    private void DisplayIncidents(ArrayList incident)
    {
        //Display items in list view
        ListView allIncidents = (ListView) findViewById(R.id.lstvIncidents);
        ArrayAdapter<String> arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, incident);
        assert allIncidents != null;
        allIncidents.setAdapter(arrMenuAdapter);
    }
    //***************************************************
}
