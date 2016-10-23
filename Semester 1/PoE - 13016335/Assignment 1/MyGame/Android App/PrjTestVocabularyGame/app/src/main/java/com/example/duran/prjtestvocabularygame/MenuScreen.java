/*
MenuScreen.java
Displays a set of options to the user
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

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
/*
Change Light on Device
 */
public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMenuAdapter();
    }
    //*************************************************************
    private void setMenuAdapter()
    {
        //Create array that contains the menu items
        String [] arrMenuItems = {"Play Game",
                                  "How to Play",
                                  "Game Play History"};
        ArrayAdapter <String> arrMenuAdapter;
        ListView menuList;

        //Set up list view adapter with array elements
        arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrMenuItems);
        menuList = (ListView) findViewById(R.id.lstMenuItems);
        if (menuList != null) {
            menuList.setAdapter(arrMenuAdapter);
        }

        menuItemClick(menuList);
    }
    //*************************************************************
    private void menuItemClick(ListView menu)
    {
            //Checks which item in the list is clicked
            //Checks the position, and calls the associated activity
            menu.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    try{
                        Intent newActivity = new Intent();
                        if (position == 0)
                        {
                            newActivity = new Intent(MenuScreen.this, GamePlay.class);
                        }
                        else if(position == 1)
                        {
                            newActivity = new Intent(MenuScreen.this,HowToPlay.class);
                        }
                        else if(position == 2)
                        {
                            newActivity = new Intent(MenuScreen.this,History.class);
                        }

                        startActivity(newActivity);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });
    }
    //*************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }
    //*************************************************************
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Click on the option
        //Displays relevant activity
        int id = item.getItemId();

        if(id == R.id.action_about_us)
        {
            Intent newActivity = new Intent(MenuScreen.this, AboutUs.class);
            startActivity(newActivity);
        }

        return super.onOptionsItemSelected(item);
    }
}
