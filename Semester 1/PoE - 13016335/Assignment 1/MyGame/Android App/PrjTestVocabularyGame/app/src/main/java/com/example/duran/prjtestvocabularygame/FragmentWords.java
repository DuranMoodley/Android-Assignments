/*
FragmentWords.java
Displays all valid words that the user has created
Check each word using an API
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class FragmentWords extends Fragment
{
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_words, container, false);
        getWords();
        return view;
    }
    //**********************************************************************************
    private void getWords()
    {
        //Opens db , and adds all tenants to cursor
        DatabaseAdapter database = new DatabaseAdapter(getActivity());
        database.open();
        String [][] wordArr;
        ArrayList objWords = new ArrayList();
        Cursor c = database.getAllWordsAndDefinitions();
        int countRow = 0;

        //Move through cursor and add records to array list
        if (c.moveToFirst())
        {
            do {
                objWords.add(c.getString(0));
                countRow++;
            }
            while (c.moveToNext());
        }

        //Fill records to 2d array
        wordArr = fillWords(countRow, c);
        DisplayWords(objWords, wordArr);
        database.close();
    }
    //***************************************************************
    private void createAlertDialog(String message)
    {
        //Set the properties of the Alert dialog
        //Which will prompt the user to play again or not
        AlertDialog.Builder objAlertBuilder = new AlertDialog.Builder(getActivity());
        objAlertBuilder.setMessage(message).setIcon(R.mipmap.ic_teacherface)
                .setTitle("Word Definition");

        objAlertBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = objAlertBuilder.create();
        alertDialog.show();
    }
    //************************************************************
    private String [][] fillWords(int rowAmount, Cursor cur)
    {
        //Use cursor to fill 2d array
        String [][] arrWords = new String [rowAmount][2];
        cur.moveToFirst();
        for(int row = 0 ; row< arrWords.length ; row++)
        {
            for(int col = 0 ; col < arrWords[row].length ; col++)
            {
                arrWords[row][col] = cur.getString(col);
            }

            cur.moveToNext();
        }

        return arrWords;
    }
    //************************************************************
    private void DisplayWords(ArrayList userWords, final String[][] arr)
    {
        //Fill the array list values into list view
        final String[] wordDetails = new String[1];
        ListView allIUserCreatedWords = (ListView) view.findViewById(R.id.lstUserWords);
        ArrayAdapter<String> arrMenuAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, userWords);

        //When list item is clicked open alert dialog
        //Get position of item click and use that to get 2d array value
        allIUserCreatedWords.setAdapter(arrMenuAdapter);
        allIUserCreatedWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wordDetails[0] = arr[position][1];
                createAlertDialog(wordDetails[0]);
            }
        });
    }
    //***************************************************
}
