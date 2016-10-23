/*
GamePlay.java
Contains the 2 Fragments that executes the game
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Chronometer;
import android.widget.Toast;

public class GamePlay extends AppCompatActivity {

    public Chronometer gameTimer;
    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_game_play);
        gameTimer = (Chronometer) findViewById(R.id.chTimer);
        if (gameTimer != null)
        {
            gameTimer.start();
        }
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setTimerListeners(gameTimer);
    }
    //************************************
    public void setTimerListeners(final Chronometer ch)
    {
        //Waits for a certain time span, thereafter the timer stops , phone vibrator gets executed
        // and a confirmation dialog prompts the user to play again or not.
        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().toString().equalsIgnoreCase("00:15")) {
                    ch.stop();
                    Toast.makeText(GamePlay.this, "Your Time is up", Toast.LENGTH_SHORT).show();
                    createAlertDialog();
                    setDeviceVibrator();
                }
            }
        });
    }
    //************************************
    public void setDeviceVibrator()
    {
        long [] vibratorPattern = {0,200,500,200,700};
        vibrator.vibrate(vibratorPattern, 0);
    }
    //************************************
    @Override
    public void onBackPressed()
    {
        vibrator.cancel();
        super.onBackPressed();
    }
    //************************************
    public void createAlertDialog()
    {
        //Set the properties of the Alert dialog
        //Which will prompt the user to play again or not
        AlertDialog.Builder objAlertBuilder = new AlertDialog.Builder(GamePlay.this);
        objAlertBuilder.setMessage("Would you like to play Again ?")
                .setTitle("Confirmation Message")
                .setIcon(R.mipmap.ic_teacherface);

        //Add the button components to the dialog
        objAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                finish();
                startActivity(getIntent());
            }
        });

        objAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                finish();
            }
        });

        AlertDialog alertDialog = objAlertBuilder.create();
        alertDialog.show();
    }
}
