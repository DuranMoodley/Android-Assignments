/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays information to user
Updated: 5/22/2016
AboutUs.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Displays the expandable view items by calling the getAboutUs method
        //populates expandable view
        ExpandableListView aboutUsDetails = (ExpandableListView) findViewById(R.id.explvAboutUs);
        HashMap<String, List<String>> about = getAboutUsInformation();
        List<String> aboutUsList = new ArrayList<>(about.keySet());
        AboutUsAdapter aboutUsAdapter = new AboutUsAdapter(this, about, aboutUsList);
        if (aboutUsDetails != null) {
            aboutUsDetails.setAdapter(aboutUsAdapter);
        }
    }
    //**************************************************************
    private HashMap<String,List<String>> getAboutUsInformation()
    {
        //Declares array list objects and adds the necessary information
        HashMap<String,List<String>> aboutUsInformation = new HashMap<>();
        List<String> developerDetails = new ArrayList<>();
        List<String> contactDetails = new ArrayList<>();
        List<String> credits = new ArrayList<>();
        developerDetails.add("Duran Moodley");
        developerDetails.add("3rd Year IT Student at Varsity College");
        contactDetails.add("Email : duranmoodley97@gmail.com");
        contactDetails.add("Tel :  031 332 1234");
        contactDetails.add("Cell : 072 344 9811");
        credits.add("Designer : Duran Moodley");
        credits.add("Concept : Duran Moodley");
        credits.add("Marketing : Maggaret Thomas");
        credits.add("Deployment : Duran Moodley");
        credits.add("Testing : Duran Moodley");
        aboutUsInformation.put("Developer Details", developerDetails);
        aboutUsInformation.put("Send us feedback", contactDetails);
        aboutUsInformation.put("Credits", credits);
        return aboutUsInformation;
    }
}
