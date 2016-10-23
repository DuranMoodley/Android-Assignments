/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : displays all tenants to the user
Updated: 5/22/2016
ShowTenant.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowTenant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tenant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTenants();
    }
    //**********************************************************************************
    private void getTenants()
    {
        //Opens db , and adds all tenants to cursor
        DatabaseAdapter database = new DatabaseAdapter(this);
        database.open();
        String [][] tenantArr;
        ArrayList objTenants = new ArrayList();
        Cursor c = database.getAllTenants();
        int countRow = 0;

        //Move through cursor and add records to array list
        if (c.moveToFirst())
        {
            do {
                objTenants.add("Name:\t" + c.getString(0) + "\n"+
                               "Tenant Id:\t" + c.getString(1));
                countRow++;
            }
            while (c.moveToNext());
        }

        //Fill records to 2d array
        tenantArr = fillTenants(countRow,c);
        DisplayTenants(objTenants, tenantArr);
        database.close();
    }
    //***************************************************************
    private void DisplayTenants(ArrayList tenant, final String[][] arr)
    {
        //Fill the array list values into list view
        final String[] tenantDetails = new String[1];
        ListView allIncidents = (ListView) findViewById(R.id.lstvReports);
        ArrayAdapter<String> arrMenuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tenant);

         //When list item is clicked open alert dialog
         //Get position of item click and use that to get 2d array value
        allIncidents.setAdapter(arrMenuAdapter);
        allIncidents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                    tenantDetails[0] = "Contact Number: \t" + arr[position][3] + "\n" +
                            "Flat Number:\t " + arr[position][5] + "\n" +
                            "Number Occupants:\t " + arr[position][2] + "\n" +
                            "Emergency Contact:\t " + arr[position][4] + "\n";
                    createAlertDialog(tenantDetails[0]);
                }
            });
    }
    //***************************************************
    private String [][] fillTenants(int rowAmount, Cursor cur)
    {
        //Use cursor to fill 2d array
        String [][] arrTenants = new String [rowAmount][6];
        cur.moveToFirst();
        for(int row = 0 ; row< arrTenants.length ; row++)
        {
            for(int col = 0 ; col < arrTenants[row].length ; col++)
            {
                arrTenants[row][col] = cur.getString(col);
            }

            cur.moveToNext();
        }

        return arrTenants;
    }
    //************************************************************
    private void createAlertDialog(String message)
    {
        //Set the properties of the Alert dialog
        //Which will prompt the user to play again or not
        AlertDialog.Builder objAlertBuilder = new AlertDialog.Builder(ShowTenant.this);
        objAlertBuilder.setMessage(message).setIcon(R.mipmap.ic_building)
                .setTitle("Tenant Details");

        objAlertBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = objAlertBuilder.create();
        alertDialog.show();
    }
}
