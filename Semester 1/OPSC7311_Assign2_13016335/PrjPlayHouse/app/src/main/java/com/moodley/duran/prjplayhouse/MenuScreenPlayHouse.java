/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : Shows a list of options to the user
Updated: 5/22/2016
MenuScreenPlayHouse.java
Assignment : 2
 */
package com.moodley.duran.prjplayhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuScreenPlayHouse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_drama_faces);
        setMenuAdapter();
    }
    //*********************************************
    private void setMenuAdapter()
    {
        //Create array that contains the menu items
        String [] arrMenuItems = {"Add Event",
                                  "Show Events",
                                  "Send Email"};
        ArrayAdapter<String> arrMenuAdapter;
        ListView menuList;

        //Set up list view adapter with array elements
        arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrMenuItems);
        menuList = (ListView) findViewById(R.id.lstvMenuItems);
        if (menuList != null)
        {
            menuList.setAdapter(arrMenuAdapter);
        }
        menuItemClick(menuList);
    }
    //*********************************************
    private void menuItemClick(ListView menu)
    {
        //Checks which item in the list is clicked
        //Checks the position, and calls the associated activity
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent newActivity = new Intent();
                    if (position == 0) {
                        newActivity = new Intent(MenuScreenPlayHouse.this, AddActivity.class);
                    } else if (position == 1) {
                        newActivity = new Intent(MenuScreenPlayHouse.this, ShowEventDetails.class);
                    } else if (position == 2) {
                        newActivity = new Intent(MenuScreenPlayHouse.this, SendEmail.class);
                    }

                    startActivity(newActivity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }
    //*************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help)
        {
            Intent newActivityHelp = new Intent(MenuScreenPlayHouse.this, Help.class);
            startActivity(newActivityHelp);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
