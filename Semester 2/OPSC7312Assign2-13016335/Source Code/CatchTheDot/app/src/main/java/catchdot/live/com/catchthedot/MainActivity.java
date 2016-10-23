/*
MainActivity.java
Displays 2 Menu Options, Game Option & LIV Options, Adds a new player to the MYSQL Database
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 2
Date Updated : 10/11/16
 */
package catchdot.live.com.catchthedot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , InputUsername.InputNameDialogListener {

    private SharedPreferences myprefs;
    private TextView amtOfDotstv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setMenuAdapter();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        amtOfDotstv = (TextView) findViewById(R.id.tvAmtDotsCaught);
        myprefs = this.getSharedPreferences("myPreference",MODE_PRIVATE);
        boolean isDataSent = myprefs.getBoolean("playerAdded",false);
        updateTotalDotsCaught();
        if(!isDataSent)
        {
            showInputNameDialog();
        }
    }
    //*************************************************************
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //*************************************************************
    @Override
    protected void onPause() {
        super.onPause();
        updateTotalDotsCaught();
    }
    //*************************************************************
    @Override
    protected void onResume() {
        super.onResume();
        updateTotalDotsCaught();
    }
    //*************************************************************
    private void updateTotalDotsCaught()
    {
        int TotalDotsCaught = myprefs.getInt("dotsCaught",0);
        amtOfDotstv.setText(String.valueOf(TotalDotsCaught));
    }
    //*************************************************************
    private void setMenuAdapter()
    {
        //Create array that contains the menu items
        String [] arrMenuItems = {"Play Game",
                "How to Play",
                "World Rankings"};
        ArrayAdapter<String> arrMenuAdapter;
        ListView menuList;

        //Set up list view adapter with array elements
        arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrMenuItems);
        menuList = (ListView) findViewById(R.id.lstGameOptions);
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
                        newActivity = new Intent(MainActivity.this, GamePlay.class);
                    }
                    else if(position == 1)
                    {
                        newActivity = new Intent(MainActivity.this,HowToPlay.class);
                    }
                    else if(position == 2)
                    {
                        newActivity = new Intent(MainActivity.this,WorldRankings.class);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_about) {
            Intent newActivity ;
            newActivity = new Intent(MainActivity.this,AboutLiv.class);
            startActivity(newActivity);
        }

        return super.onOptionsItemSelected(item);
    }
    //*************************************************************
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent newActivity = null;
        int id = item.getItemId();

        if (id == R.id.nav_about_live)
        {
            newActivity = new Intent(MainActivity.this,AboutLiv.class);
        }
        else if (id == R.id.nav_donate) {
            newActivity = new Intent(MainActivity.this,Donate.class);
        }
        else if (id == R.id.nav_volunteer_program)
        {
            newActivity = new Intent(MainActivity.this,Volunteer.class);
        }
        else if (id == R.id.nav_corporate_team_building)
        {
            newActivity = new Intent(MainActivity.this,CorporateTeamBuilding.class);
        }
        else if (id == R.id.nav_contact)
        {
            newActivity = new Intent(MainActivity.this,ContactUs.class);
        }

        startActivity(newActivity);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //*************************************************************
    @Override
    public void onFinishInputDialog(String inputText)
    {
        int stopPosition =  inputText.indexOf("@");
        String username = inputText.substring(0,stopPosition);
        SharedPreferences.Editor myNeweditor = myprefs.edit();
        myNeweditor.putString("Username",username);
        myNeweditor.apply();
        new SendData().execute();
        //Toast.makeText(MainActivity.this,username,Toast.LENGTH_LONG).show();
    }
    //*************************************************************
    private void showInputNameDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        InputUsername inputNameDialog = new InputUsername();
        inputNameDialog.setCancelable(false);
        inputNameDialog.setDialogTitle("Enter Email Address");
        inputNameDialog.show(fragmentManager, "input dialog");
    }
    //*************************************************************
    public class SendData extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            Toast.makeText(MainActivity.this,R.string.wait_message,Toast.LENGTH_LONG).show();
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
                URL url = new URL("http://www.duran.dx.am/liv_android_add.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which is the student number to the url
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(myprefs.getString("Username",""),"UTF-8");

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
            SharedPreferences.Editor editor = myprefs.edit();
            Toast.makeText(MainActivity.this, s,Toast.LENGTH_LONG).show();
            if(s.trim().equalsIgnoreCase("Successfully added"))
            {
                Toast.makeText(MainActivity.this, R.string.player_start_message,Toast.LENGTH_LONG).show();
                editor.putBoolean("playerAdded",true);
            }
            else{
                Toast.makeText(MainActivity.this, R.string.error_message,Toast.LENGTH_LONG).show();
                editor.putBoolean("playerAdded",false);
            }
            editor.apply();
        }
    }
}
