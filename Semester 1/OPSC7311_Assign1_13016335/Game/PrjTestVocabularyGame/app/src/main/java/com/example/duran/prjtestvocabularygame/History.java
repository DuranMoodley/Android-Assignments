/*
History.java
Contains the 2 tabs containing fragments that are displayed to the user
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

public class History extends AppCompatActivity {

    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sets the tabHost up
        tabHost = (TabHost) findViewById(R.id.tabhost);
        if (tabHost != null) {
            tabHost.setup();
        }

        //Associates a tab to the specified fragment
        TabHost.TabSpec firstTab = tabHost.newTabSpec("tabFragScores");
        firstTab.setContent(R.id.tabFragScores);
        firstTab.setIndicator("Scores");
        tabHost.addTab(firstTab);

        //Associates a tab to the specified fragment
        TabHost.TabSpec secondTab = tabHost.newTabSpec("tabFragWords");
        secondTab.setContent(R.id.tabFragWords);
        secondTab.setIndicator("Created Words");
        tabHost.addTab(secondTab);
    }
}
