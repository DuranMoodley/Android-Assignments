/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays all events
Updated: 5/22/2016
ShowEventDetails.java
Assignment : 2
 */
package myassignment.moodley.duran.prjplayhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moodley.duran.prjplayhouse.R;

import java.util.ArrayList;

public class ShowEventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        displayData();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
//*************************************************************
private void displayData()
    {
        //Retrieve data from text file and fill into list view
        ArrayList objArray;
        FactoryClass objFactory = new FactoryClass(this,"events");
        objArray = objFactory.readData();
        ListView lstEvent = (ListView) findViewById(R.id.lstvEvents);
        ArrayAdapter<String> arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, objArray);
        assert lstEvent != null;
        assert lstEvent != null;
        lstEvent.setAdapter(arrMenuAdapter);
        arrMenuAdapter.notifyDataSetChanged();
    }
}
