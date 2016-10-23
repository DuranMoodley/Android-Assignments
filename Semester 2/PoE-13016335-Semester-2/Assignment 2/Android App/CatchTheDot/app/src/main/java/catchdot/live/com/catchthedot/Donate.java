/*
Donate.java
Displays Donation information from a text file about the NGO
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

public class Donate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView directdeposittv = (TextView) findViewById(R.id.tvDirectDeposit);
        TextView donateWebpagetv = (TextView) findViewById(R.id.tvWebDonate);

        //Redirects to web link
        if (donateWebpagetv != null) {
            donateWebpagetv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    setlink();
                }
            });
        }
        FactoryClass objFactory = new FactoryClass(this);
        if (directdeposittv != null) {
            directdeposittv.setText(objFactory.readDate("donatedirectdeposit"));
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    setlink();
                }
            });
        }
    }
    //******************************************************************************
    private void setlink()
    {
        //Redirect to the necessary link
        Uri uriLivLink = Uri.parse("http://www.liv-village.com/donate/");
        Intent openLiveLink = new Intent(Intent.ACTION_VIEW,uriLivLink);
        startActivity(openLiveLink);
    }
}
