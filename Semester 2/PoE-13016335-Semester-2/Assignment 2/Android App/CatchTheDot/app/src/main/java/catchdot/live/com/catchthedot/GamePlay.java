/*
GamePlay.java
Executes the game, draws cirles onto a canvas, Saves scores to a MYSQL Database
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 2
Date Updated : 10/11/16
 */
package catchdot.live.com.catchthedot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class GamePlay extends AppCompatActivity{

    private SurfaceView surfaceView;
    private SurfaceHolder objSurfaceHolder;
    private final Paint objPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Canvas objCanvas;
    private int blueDotRandomCoordinateX;
    private int blueDotRandomCoordinateY;
    private int redDotRandomCoordinateX;
    private int redDotRandomCoordinateY;
    private int greenDotRandomCoordinateX;
    private int greenDotRandomCoordinateY;
    private int yellowDotRandomCoordinateX;
    private int yellowDotRandomCoordinateY;
    private int darkgrayDotRandomCoordinateX;
    private int darkgrayDotRandomCoordinateY;
    private Random objRandomCoordinates;
    private Chronometer gameTimer;
    private Vibrator vibrator;
    private int currentUserScore;
    private TextView targetNumber;
    private boolean isTimerRunning;
    private String [] userTime ;
    private int [] userTargets;
    private SharedPreferences myprefs;
    private int targetIndex;
    private int timeIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        //Retrieve values from the share preferences, set the user times and target values
        surfaceView = (SurfaceView) findViewById(R.id.svgame);
        targetNumber = (TextView) findViewById(R.id.tvTargetNumber);
        isTimerRunning = false;
        myprefs = this.getSharedPreferences("myPreference",MODE_PRIVATE);
        userTime = new String[]{"00:10", "00:20","00:30","00:40","00:50"};
        userTargets = new int[]{10,15,20,25,30,35,40,45,50,55,60};
        targetIndex = myprefs.getInt("target",0);
        timeIndex = myprefs.getInt("stoptime",0);

        //Set user score at the start of the game, and instantiate random object which generates random X & Y Coordinates
        objRandomCoordinates = new Random();
        currentUserScore = 0;
        gameTimer = (Chronometer) findViewById(R.id.crmGameTimer);
        objSurfaceHolder = surfaceView.getHolder();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setRandomCoordinates();

        //Display information the user
        Toast.makeText(GamePlay.this, "Get" + userTargets[targetIndex] + " Points\nBlue Dot = 1 Point\nGreen Dot = 10 Points\n"
                + "In " + userTime[timeIndex] + " seconds", Toast.LENGTH_LONG).show();
    }
    //************************************************************************************
    private void setDeviceVibrator(long [] vibratorPattern)
    {
        //Sets the vibrator properties
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
    private void setTarget(int targetNum)
    {
        //Sets the TextView to show the amount of points the user needs to get for the current level
        targetNumber.setText(String.valueOf(targetNum));
    }
    //************************************
    private void drawCircle(float x, float y)
    {
        TextView scoretv = (TextView) findViewById(R.id.tvScore);
        assert scoretv != null;
        scoretv.setText(String.valueOf(currentUserScore));

        //Draw on canvas the dots required in the game.
        //Sets the colours and random coordinates
        if(objSurfaceHolder.getSurface().isValid())
        {
            objCanvas = objSurfaceHolder.lockCanvas();
            objCanvas.drawColor(Color.CYAN);
            objPaint.setColor(Color.BLACK);
            objCanvas.drawCircle(x,y,80,objPaint);
            objPaint.setColor(Color.BLUE);
            objCanvas.drawCircle(blueDotRandomCoordinateX, blueDotRandomCoordinateY,40,objPaint);
            objPaint.setColor(Color.RED);
            objCanvas.drawCircle(redDotRandomCoordinateX, redDotRandomCoordinateY,90,objPaint);
            objPaint.setColor(0xff00ff00);
            objCanvas.drawCircle(greenDotRandomCoordinateX, greenDotRandomCoordinateY,20,objPaint);
            objPaint.setColor(0xffffff00);
            objCanvas.drawCircle(yellowDotRandomCoordinateX,yellowDotRandomCoordinateY,20,objPaint);
            objPaint.setColor(0xff444444);
            objCanvas.drawCircle(darkgrayDotRandomCoordinateX,darkgrayDotRandomCoordinateY,40,objPaint);
            objSurfaceHolder.unlockCanvasAndPost(objCanvas);
        }

        //Check if the User dot is checking any of the dots being displayed
        //Decrease or Increase the user score depending on which dot is being touched
        if(isCirclesTouching(x,y,80,90, redDotRandomCoordinateX, redDotRandomCoordinateY))
        {
            currentUserScore--;
            setRandomCoordinates();
        }
        else if(isCirclesTouching(x,y,80,40, blueDotRandomCoordinateX, blueDotRandomCoordinateY))
        {
            setRandomCoordinates();
            currentUserScore++;
        }
        else if(isCirclesTouching(x,y,80,20, greenDotRandomCoordinateX, greenDotRandomCoordinateY))
        {
            setRandomCoordinates();
            currentUserScore = currentUserScore + 10;
        }
        else if(isCirclesTouching(x,y,80,20, yellowDotRandomCoordinateX, yellowDotRandomCoordinateY))
        {
            setRandomCoordinates();
            currentUserScore = currentUserScore - 10;
        }
        else if(isCirclesTouching(x,y,80,40, darkgrayDotRandomCoordinateX, darkgrayDotRandomCoordinateY))
        {
            setRandomCoordinates();
            currentUserScore = currentUserScore - 50;
        }
    }
    //********************************************
    private void setRandomCoordinates()
    {
        //Generates the random coordinates for each dot
        blueDotRandomCoordinateY = objRandomCoordinates.nextInt(500);
        blueDotRandomCoordinateX = objRandomCoordinates.nextInt(500);
        redDotRandomCoordinateX = objRandomCoordinates.nextInt(500);
        redDotRandomCoordinateY = objRandomCoordinates.nextInt(500);
        greenDotRandomCoordinateX = objRandomCoordinates.nextInt(500);
        greenDotRandomCoordinateY = objRandomCoordinates.nextInt(500);
        yellowDotRandomCoordinateX = objRandomCoordinates.nextInt(500);
        yellowDotRandomCoordinateY = objRandomCoordinates.nextInt(500);
        darkgrayDotRandomCoordinateX = objRandomCoordinates.nextInt(500);
        darkgrayDotRandomCoordinateY = objRandomCoordinates.nextInt(500);
    }
    //********************************************
    private void startTimer()
    {
        //Starts the time and sets the listener
        //Timer will stop when the userTime element has been reached
        //Player Target Points is also set and displayed
        final long [] vibratorPattern = {0,200,500,200,700};
        if(!isTimerRunning)
        {
            gameTimer.setBase(SystemClock.elapsedRealtime());
            gameTimer.start();
            isTimerRunning = true;
            setTarget(userTargets[targetIndex]);
            gameTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
            {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (chronometer.getText().toString().equalsIgnoreCase(userTime[timeIndex])) {
                        chronometer.stop();
                        Toast.makeText(GamePlay.this, R.string.time_complete, Toast.LENGTH_SHORT).show();
                        surfaceView.setVisibility(View.GONE);
                        createAlertDialog();
                        setDeviceVibrator(vibratorPattern);
                    }
                }
            });
        }
    }
    //********************************************
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //Listens for the touch gestures and starts the time only if it has started yet
        //User Dots is dependant on the touch x and y coordinates
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            startTimer();
            drawCircle(event.getX(),event.getY());
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)
        {
            startTimer();
            drawCircle(event.getX(),event.getY());
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_MOVE)
        {
            startTimer();
            drawCircle(event.getX(),event.getY());
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_UP)
        {
            startTimer();
            drawCircle(event.getX(),event.getY());
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_UP)
        {
            startTimer();
            drawCircle(event.getX(),event.getY());
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_CANCEL)
        {
            startTimer();
            drawCircle(event.getX(),event.getY());
        }

        return super.onTouchEvent(event);
    }
    //********************************************
    private boolean isCirclesTouching(float userCircleX, float userCircleY, int userCircleRadius, int secCircleRadius
            , int secCircleRandomX, int secCircleRandomY)
    {
        //Checks if the User Dot is touching a specific dot
        //Geometric calculations is used
        boolean isTouching;
        double circleDistance = Math.pow((userCircleX - secCircleRandomX) * (userCircleX - secCircleRandomX) +
                                         (userCircleY - secCircleRandomY) * (userCircleY - secCircleRandomY) ,0.5);

        if(secCircleRadius >= userCircleRadius && circleDistance <= (secCircleRadius - userCircleRadius))
        {
            isTouching = true;
        }
        else if(userCircleRadius >= secCircleRadius && circleDistance <= (userCircleRadius - secCircleRadius))
        {
            isTouching = true;
        }
        else
        {
            isTouching = false;
        }
        return isTouching;
    }
    //************************************
    private boolean isWinner(int level)
    {
        //Checks if the Play has reached the target points
        //Relevant message is displayed
        FrameLayout layout = (FrameLayout) findViewById(R.id.game_play);
        boolean isWinner = false;
        if(currentUserScore >= Integer.parseInt(targetNumber.getText().toString()))
        {
            isWinner = true;

            if(level == 5)
            {
                if (layout != null) {
                    Snackbar.make(layout, R.string.fifty_awarded_points,Snackbar.LENGTH_LONG)
                            .show();
                }
            }
            else{
                if (layout != null) {
                    Snackbar.make(layout, R.string.twenty_awarded_points,Snackbar.LENGTH_LONG)
                            .show();
                }
            }

        }
        return isWinner;
    }
    //************************************
    private void createAlertDialog()
    {
        //If the player has won, the necessary Bonus points are awarded
        String userMessage;
        int playerLevel = timeIndex + 1;
        if(isWinner(playerLevel))
        {
            if(playerLevel != 5)
            {
                currentUserScore = currentUserScore + 20;
                userMessage = "You Scored " + currentUserScore +" Points. You Won" +
                        "\nWould you like to Continue to Level" + playerLevel + "?";

            }
            else
            {
                //Successfully reaching and completing the last level, awards and extra 50 points.
                currentUserScore = currentUserScore + 50;
                if(userTargets[targetIndex] == 60)
                {
                    userMessage = "You Have Reached the End of the Game.\nSelect Yes to Increase Your Total Points\n" +
                            "And Become Number 1 in the World Rankings";
                }
                else{
                    userMessage = "You Scored " + currentUserScore +" Points. You Won" +
                            "\nWould you like to Continue to\nThe Final Level!!!";
                }
            }
            setGamePlaySettings();
        }
        else{
            userMessage = "You Caught " + currentUserScore + " Dots. Sorry You Lost" +
                    "\nWould you like to play again?";
        }

       // AlertDialogForUser objAlertDialog = new AlertDialogForUser(this,currentUserScore,userMessage);
        //objAlertDialog.showDialog();
        //Set the properties of the Alert dialog
        //Which will prompt the user to play again or not
      AlertDialog.Builder objAlertBuilder = new AlertDialog.Builder(GamePlay.this);
        objAlertBuilder.setMessage(userMessage)
                .setTitle("Confirmation Message");

        //Add the button components to the dialog
        objAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                //Save player current score
                savePlayerScore();
                finish();
                startActivity(getIntent());

            }
        });

        //If player Decides to Quite, Accumulated score is added and sent to the MYSQL database
        objAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                savePlayerScore();
                new SendData().execute();
                finish();
            }
        });

        AlertDialog alertDialog = objAlertBuilder.create();
        alertDialog.show();
    }
    //************************************
    private void setGamePlaySettings()
    {
        //Changes the user target value, if the user wants to play again and reached the previous target successful
        SharedPreferences.Editor editor = myprefs.edit();
        if(targetIndex < userTargets.length-1)
        {
            targetIndex++;
        }
        else
        {
            targetIndex = 0;
        }

        if(timeIndex < userTime.length && targetIndex > 0)
        {
            //Change the users allocated time, after reaching the next target level
            if(userTargets[targetIndex] != 60)
            {
                if (userTargets[targetIndex] % 10 == 0)
                {
                    timeIndex++;
                }
            }
        }
        else
        {
            timeIndex = 0;
        }

        editor.putInt("target", targetIndex);
        editor.putInt("stoptime",timeIndex);
        editor.apply();
    }
    //************************************
    private void savePlayerScore()
    {
        //Retrieve total score of all games, and adds it by the current score made
        int userTotalGamePlayScore = myprefs.getInt("dotsCaught",0);
        userTotalGamePlayScore = userTotalGamePlayScore + currentUserScore;
        SharedPreferences.Editor editor = myprefs.edit();
        editor.putInt("dotsCaught",userTotalGamePlayScore);
        editor.apply();
    }
    //*************************************************************
    public class SendData extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(GamePlay.this, R.string.wait_message,Toast.LENGTH_LONG).show();
        }
        //********************************************************************************
        @Override
        protected String doInBackground(String... params) {

            String line;
            String entireLine = "";
            String app_data ;
            HttpURLConnection urlConnection;
            //Create connection to the url
            try {
                URL url = new URL("http://duran.dx.am/android_liv_update.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which is the student number to the url
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(myprefs.getString("Username",""),"UTF-8")+"&"+
                        URLEncoder.encode("userscore","UTF-8")+"="+URLEncoder.encode(String.valueOf(myprefs.getInt("dotsCaught",0)),"UTF-8");

                objWriter.write(app_data);
                objWriter.flush();
                objWriter.close();
                outputStream.close();

                //Retrieve the input from the url
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader objReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while ((line = objReader.readLine()) != null) {
                    entireLine += line;
                }

                objReader.close();
                inputStream.close();
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            if(s.trim().equalsIgnoreCase("Successfully Updated"))
            {
                Toast.makeText(GamePlay.this, R.string.score_saved_successfully,Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(GamePlay.this, R.string.error_message_score_not_updated,Toast.LENGTH_LONG).show();
            }
        }
    }
}
