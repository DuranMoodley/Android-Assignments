/*
WorldRankings.java
Displays everyones scores and their positions, Data is retrieved from a MYSQL database
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 2
Date Updated : 10/11/16
 */
package catchdot.live.com.catchthedot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WorldRankings extends AppCompatActivity {

    private ListView scoresList;
    private ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_rankings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scoresList = (ListView) findViewById(R.id.lstScores);
        loadingBar = (ProgressBar) findViewById(R.id.pbScores);
        new RetrieveRankingsListData().execute();
    }
    //*********************************************************
    public class RetrieveRankingsListData extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(WorldRankings.this,R.string.wait_message,Toast.LENGTH_LONG).show();
            loadingBar.setVisibility(View.VISIBLE);
        }
        //*********************************************************
        final ArrayList objListOfScores = new ArrayList<>();
        @Override
        protected String doInBackground(String... params)
        {
            String line;
            String entireLine = "";
            JSONObject jsonObject;
            JSONArray jsonArray;
            HttpURLConnection urlConnection ;
            String scoreListData = "";

            URL url;
            try {
                //Creates the connections to the php file on awards space
                url = new URL("http://duran.dx.am/liv_android_rankings.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Reads the data that is echoed in the php script file
                BufferedReader objread = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = objread.readLine()) != null) {
                    entireLine += line;
                }

                //Get json elements
                jsonObject = new JSONObject(entireLine);
                jsonArray = jsonObject.optJSONArray("rankings");

                //Take each JSON element , concatenate to String Variable and add it to any Array List
                for(int count = 0 ; count < jsonArray.length();count++)
                {
                    JSONObject jsonEventData = jsonArray.getJSONObject(count);
                    scoreListData = "Position:\t" + (count + 1) + "\n" +
                            jsonEventData.optString("Username") + "\n" +
                            "Overall Points:\t" + jsonEventData.optString("score");
                    objListOfScores.add(scoreListData);
                }
                objread.close();
                return scoreListData;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return "Nothing Returned!!";
        }
        //**********************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            //If Something is returned, show the Array List in a List view
            if(s.trim().equalsIgnoreCase("Nothing Returned!!"))
            {
                Toast.makeText(WorldRankings.this, R.string.error_world_rankings,Toast.LENGTH_LONG).show();
            }
            else
            {
                ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(WorldRankings.this, android.R.layout.simple_list_item_1, objListOfScores);
                scoresList.setAdapter(arrAdapter);
            }
            loadingBar.setVisibility(View.GONE);
        }
    }
}
