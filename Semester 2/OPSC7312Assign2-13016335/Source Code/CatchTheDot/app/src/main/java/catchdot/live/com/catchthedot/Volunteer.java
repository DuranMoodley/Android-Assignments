/*
Volunteer.java
Displays Information from a textfile about a Typical Volunteer at LIV
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 2
Date Updated : 10/11/16
 */
package catchdot.live.com.catchthedot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Volunteer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Display information of a volunteer, from the XML Strings file
        TextView volunteerWebpagetv = (TextView) findViewById(R.id.tvWebVolunteer);
        assert volunteerWebpagetv != null;
        volunteerWebpagetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setlink();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setlink();
            }
        });
    }
    //******************************************************************************
    private void setlink()
    {
        //Redirect to the necessary link
        Uri uriLivLink = Uri.parse("http://www.liv-village.com/volunteer/");
        Intent openLiveLink = new Intent(Intent.ACTION_VIEW,uriLivLink);
        startActivity(openLiveLink);
    }
}
