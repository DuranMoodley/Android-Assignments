/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays a list of options to the user
Updated: 5/22/2016
MenuScreen.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMenuAdapter();
    }
    //***********************************************************8
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen, menu);
        return true;
    }
    //***********************************************************8
    private void setMenuAdapter()
    {
        //Create array that contains the menu items
        String [] arrMenuItems = {"Add Tenant Details",
                                  "Update/Search Tenant Details",
                                  "Delete Tenant",
                                  "Show Tenant Details",
                                  "Add/Show Incidents",
                                  "Send Sms"};
        ArrayAdapter<String> arrMenuAdapter;
        ListView menuList;

        //Set up list view adapter with array elements
        arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrMenuItems);
        menuList = (ListView) findViewById(R.id.lstvOptions);
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
                    if (position == 0)
                    {
                        newActivity = new Intent(MenuScreen.this, AddTenant.class);
                    }
                    else if (position == 1)
                    {
                        newActivity = new Intent(MenuScreen.this, UpdateTenant.class);
                    }
                    else if (position == 2)
                    {
                        newActivity = new Intent(MenuScreen.this, DeleteTenant.class);
                    }
                    else if(position == 3)
                    {
                        newActivity = new Intent(MenuScreen.this, ShowTenant.class);
                    }
                    else if(position == 4)
                    {
                        newActivity = new Intent(MenuScreen.this, AddIncident.class);
                    }
                    else if(position == 5)
                    {
                        newActivity = new Intent(MenuScreen.this, SendSms.class);
                    }

                    startActivity(newActivity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent newMenuActivity;

        if (id == R.id.action_aboutUs)
        {
            newMenuActivity = new Intent(MenuScreen.this,AboutUs.class);
            startActivity(newMenuActivity);
            return true;
        }
        else if(id == R.id.action_help)
        {
            newMenuActivity = new Intent(MenuScreen.this,Help.class);
            startActivity(newMenuActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
