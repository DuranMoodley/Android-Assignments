/*
AboutLiv.java
Displays information from a text file about the NGO
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

public class AboutLiv extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_liv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Declare view variables
        TextView livExisttv = (TextView) findViewById(R.id.txtHelpContent);
        TextView creditabilitytv = (TextView) findViewById(R.id.tvfocus);
        TextView websitetv = (TextView) findViewById(R.id.tvWeb);
        assert websitetv != null;
        websitetv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setlink();
            }
        });

        //Retrieve data from text file and display in text view
        FactoryClass objFactory = new FactoryClass(this);
        assert livExisttv != null;
        livExisttv.setText(objFactory.readDate("aboutliv"));
        assert creditabilitytv != null;
        creditabilitytv.setText(objFactory.readDate("creditation"));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener()
        {
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
        //Redirect user to link
        Uri uriLivLink = Uri.parse("http://www.liv-village.com/about/");
        Intent openLiveLink = new Intent(Intent.ACTION_VIEW,uriLivLink);
        startActivity(openLiveLink);
    }
}
