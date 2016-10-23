/*
FragmentScores.java
Reads the user scores from the text file and displays it to the user
Check each word using an API
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class FragmentScores extends Fragment {

    ListView  userTopScores;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_scores, container, false);
        userTopScores = (ListView) view.findViewById(R.id.lstvScores);
        DisplayUserScores();
        return view;
    }
    //******************************************************************
    public void DisplayUserScores()
    {
        //Retrieve user scores from text file and display it in list view
        FactoryClass objFactory = new FactoryClass(getActivity(),"UserResults");
        ArrayList<String> userScores = objFactory.readData();
        ArrayAdapter<String> arrScoresAdapter;

        //Set up list view adapter with array elements
        arrScoresAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, userScores);
        userTopScores.setAdapter(arrScoresAdapter);
        arrScoresAdapter.notifyDataSetChanged();
    }
}
