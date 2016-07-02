/*
SplashScreen.java
Displays an activity for a specified timer
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Sets up thread to run
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    Intent newActivity = new Intent(SplashScreen.this,MenuScreen.class);
                    startActivity(newActivity);
                }
            }
        };
        timer.start();
    }
    //************************************************************
    @Override
    protected void onPause()
    {
        //Destroys activity
        super.onPause();
        finish();
    }
}
