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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentWords extends Fragment
{
    ListView allUserWords;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_words, container, false);
        allUserWords = (ListView) view.findViewById(R.id.lstUserWords);
        DisplayUserCreatedWords();
        return view;
    }
    //**************************************************************8
    public void DisplayUserCreatedWords()
    {
        //Retrieve user valid words from text file and display it in list view
        FactoryClass objFactory = new FactoryClass(getActivity(),"UserValidWords");
        ArrayList<String> userWords = objFactory.readData();
        ArrayAdapter<String> arrWordListAdapter;

        //Set up list view adapter with array elements
        arrWordListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, userWords);
        allUserWords.setAdapter(arrWordListAdapter);
        arrWordListAdapter.notifyDataSetChanged();
    }
}
