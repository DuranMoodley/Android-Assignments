/*
CorporateTeamBuilding.java
Displays information Team Building information from a text file about the NGO
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

public class CorporateTeamBuilding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporate_team_building);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Display text view with data from the XML Strings
        TextView corporateWebpagetv = (TextView) findViewById(R.id.tvWebCorporate);
        assert corporateWebpagetv != null;
        corporateWebpagetv.setOnClickListener(new View.OnClickListener() {
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
        Uri uriLivLink = Uri.parse("http://www.liv-village.com/corporate-team-building/");
        Intent  openLiveLink = new Intent(Intent.ACTION_VIEW,uriLivLink);
        startActivity(openLiveLink);
    }
}
