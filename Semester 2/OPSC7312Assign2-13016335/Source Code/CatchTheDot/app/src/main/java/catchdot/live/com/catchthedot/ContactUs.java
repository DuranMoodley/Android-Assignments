/*
ContactUs.java
Displays information Contact information from a text file about the NGO
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

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView findUstv = (TextView) findViewById(R.id.tvAddress);
        TextView contactUsWeb = (TextView) findViewById(R.id.tvWebContactUs);
        assert contactUsWeb != null;
        contactUsWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setlink("http://www.liv-village.com/contact-us/");
            }
        });

        //Retrieve data from text file and display in text view
        FactoryClass objFactory = new FactoryClass(this);
        assert findUstv != null;
        findUstv.setText(objFactory.readDate("findus"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setlink("https://www.facebook.com/LIV-Village-UK-110117809078010/");
            }
        });
    }
    //******************************************************************************
    private void setlink(String link)
    {
        //Redirect to the necessary link
        Uri uriLivLink = Uri.parse(link);
        Intent openLiveLink = new Intent(Intent.ACTION_VIEW,uriLivLink);
        startActivity(openLiveLink);
    }
}
