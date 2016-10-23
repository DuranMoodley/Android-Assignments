package catchdot.live.com.catchthedot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
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

public class AlertDialogForUser {

    private SharedPreferences myprefs;
    private int currentUserScore;
    private String message;
    private Context context;
    private Vibrator vibrator;
    public AlertDialogForUser(Context con , int userCurScore, String mes )
    {
        myprefs = con.getSharedPreferences("myPreference",Context.MODE_PRIVATE);
        currentUserScore = userCurScore;
        message = mes;
        context = con;
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    //********
    public void showDialog()
    {
        AlertDialog.Builder objAlertBuilder = new AlertDialog.Builder(context);
        objAlertBuilder.setMessage(message)
                .setTitle("Confirmation Message");

        //Add the button components to the dialog
        objAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                //Save player current score
                savePlayerScore();
                context.startActivity(new Intent(context,GamePlay.class));
            }
        });

        //If player Decides to Quite, Accumulated score is added and sent to the MYSQL database
        objAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrator.cancel();
                savePlayerScore();
                new SendData().execute();
               // finish();
            }
        });

        AlertDialog alertDialog = objAlertBuilder.create();
        alertDialog.show();
    }
    //**************************************************************************************
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
            Toast.makeText(context, R.string.wait_message,Toast.LENGTH_LONG).show();
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
                Toast.makeText(context, R.string.score_saved_successfully,Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context, R.string.error_message_score_not_updated,Toast.LENGTH_LONG).show();
            }
        }
    }
}
