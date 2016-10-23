/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : logs in a user to program
Updated: 5/22/2016
Login.java
Assignment : 2
 */
package myassignment.moodley.duran.prjplayhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.moodley.duran.prjplayhouse.R;

public class Login extends AppCompatActivity {

    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("credentialPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username","Duran");
        editor.putString("password", "Password1");
        editor.apply();
    }
    //*******************************************************************
    public void buttonClicked(View v)
    {
        //Takes user input Credentials
        Intent newActivity = null;
        EditText edtNameOfUser = (EditText) findViewById(R.id.edtUsername);
        EditText edtPasswordOfUser = (EditText) findViewById(R.id.edtPassword);
        String validUser = prefs.getString("username","");
        String validPassword = prefs.getString("password","");

        //Checks of the users data is correct
        assert edtNameOfUser != null;
        assert edtPasswordOfUser != null;
        if(validUser.equals(edtNameOfUser.getText().toString().trim()) && validPassword.equals(edtPasswordOfUser.getText().toString().trim()))
        {
            //Opens a activity if correct
            if(v.getId() == R.id.btnLogin)
            {
                newActivity = new Intent(Login.this,MenuScreenPlayHouse.class);
                finish();
            }
            startActivity(newActivity);
        }
        else
        {
            Toast.makeText(Login.this, R.string.title_incorrect_password, Toast.LENGTH_SHORT).show();
        }
    }
    //*******************************************************************
}
