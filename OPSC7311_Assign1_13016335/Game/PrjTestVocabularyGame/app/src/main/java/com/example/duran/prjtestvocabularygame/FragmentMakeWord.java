/*
FragmentMakeWord.java
Allows the user to create the word based on characters selected in the FragmentGame
Check each word using an API
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 4/13/16
 */
package com.example.duran.prjtestvocabularygame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FragmentMakeWord extends Fragment implements View.OnClickListener{

    ArrayList<String> word = new ArrayList<>();
    EditText wordMaker;
    Button addWord;
    ListView wordList;
    Chronometer wordMakerTimer;
    ProgressBar loadingBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate(R.layout.fragment_make_word, container, false);
        wordMaker = (EditText) view.findViewById(R.id.edtWord);
        addWord = (Button) view.findViewById(R.id.btnAddWord);
        wordList = (ListView) view.findViewById(R.id.lstWords);
        loadingBar = (ProgressBar) view.findViewById(R.id.prb);
        addWord.setOnClickListener(this);
        setTimerListener(view);
        return view;
    }
    //**********************************************************************
    public void displayLetter(char letterSelected)
    {
        //Letters selected in other fragment game, is now displayed in this fragment
        wordMaker.setFocusable(true);
        wordMaker.append(String.valueOf(letterSelected));
        wordMaker.setFocusable(false);
    }
    //**********************************************************************
    public void setTimerListener(View v)
    {
       wordMakerTimer = (Chronometer) v.findViewById(R.id.chFragment);
       wordMakerTimer.start();
       wordMakerTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
           @Override
           public void onChronometerTick(Chronometer chronometer) {
               if (chronometer.getText().toString().equalsIgnoreCase("00:15")) {
                   wordMakerTimer.stop();
                   wordMaker.setText("");
                   addWord.setVisibility(View.INVISIBLE);
                   //If user has not added a word, the user losses the game automatically
                   if (word.size() == 0) {
                       Toast.makeText(getActivity(), "No words have been made. You Have Lost", Toast.LENGTH_LONG).show();
                   } else {
                       //Calls the Async Task class to handle API operations
                       RetrieveFeedTask obj = new RetrieveFeedTask();
                       obj.execute(setUrlWordSearch());
                   }
               }
           }
       });
    }
    //**********************************************************************
    public String [] setUrlWordSearch()
    {
       //Return array that contains the api and the word to search for concatenated together
       //The array elements will be used in the Async Task Class
        String[] arrUrlWords = new String[word.size()];
        String url = "http://services.aonaware.com/DictService/DictService.asmx/Define?word=";
        for (int count = 0; count < arrUrlWords.length; count++)
        {
            arrUrlWords[count] = url + word.get(count);
        }
       return arrUrlWords;
    }
    //**********************************************************************
    @Override
    public void onClick(View v)
    {
        word.add(wordMaker.getText().toString());
        wordMaker.setText("");
        ArrayAdapter<String> arrWordListAdapter;

        //Set up list view adapter with array elements
        arrWordListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, word);
        wordList.setAdapter(arrWordListAdapter);
        arrWordListAdapter.notifyDataSetChanged();
    }
    //**********************************************************************
    public class RetrieveFeedTask extends AsyncTask<String, Void, String>
    {
        public boolean getWordsValid(InputStream input, HttpURLConnection urlConnection)
        {
            //This code is derived from a class project webServices , Given by Lecturer Rajesh Chanderman
            String wordDefinition = "";
            Document objDocument = null;
            DocumentBuilderFactory objDocBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder objDocumentBuilder;

            boolean validWord = false;
            try {
                //Retrieve the url connection stream
                input = urlConnection.getInputStream();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try {
                objDocumentBuilder = objDocBuilderFactory.newDocumentBuilder();
                objDocument = objDocumentBuilder.parse(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert objDocument != null;
            objDocument.getDocumentElement().normalize();

            //get all Definition elements
            NodeList allDefinitionElements = objDocument.getElementsByTagName("Definition");

            for(int count = 0 ; count < allDefinitionElements.getLength();count++)
            {
                //Get the Node
                Node itemNode = allDefinitionElements.item(count);
                if(itemNode.getNodeType() == Node.ELEMENT_NODE)
                {
                   //Check the node,
                   // After checking convert into An Element
                    Element definitionElement = (Element) itemNode;

                    //---get all the <WordDefinition> elements under
                    // the <Definition> element---
                    NodeList wordElementsDefinition = (definitionElement).getElementsByTagName("WordDefinition");

                    for(int i = 0 ; i< wordElementsDefinition.getLength();i++)
                    {
                        Element wordDefElement = (Element) wordElementsDefinition.item(i);
                        NodeList textNodes = wordDefElement.getChildNodes();

                        wordDefinition += textNodes.item(0).getNodeValue() + ". \n";
                    }
                }
            }

            //If a definition is return, word is valid
            if(!wordDefinition.isEmpty())
            {
                validWord = true;
            }
           return validWord;
        }
        //******************************************************************************************
        @Override
        protected String doInBackground(String... params)
        {
            int amountOfWordsValid = 0;
            FactoryClass objFactory = new FactoryClass(getActivity(),"UserValidWords");
            InputStream inputStream = null;
            String [] arrWordAndUrl ;
            HttpURLConnection urlConnection = null;
            try
            {
                for (String param : params)
                {
                    //Uses array of urls containing the user entered word and api concatenated
                    //This url is checked if a response is made
                    URL url = new URL(param);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    int urlResponse = urlConnection.getResponseCode();
                    if (urlResponse == HttpURLConnection.HTTP_OK)
                    {
                        //If the word is valid and contains a definition
                        if (getWordsValid(inputStream, urlConnection))
                        {
                            //Split the user created word attached to the url/Api
                            //get the user created word and write to text file
                            amountOfWordsValid++;
                            arrWordAndUrl = param.split("=");
                            objFactory.writeDate(arrWordAndUrl[1]);
                        }
                    }
                }
                return String.valueOf(amountOfWordsValid);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert urlConnection != null;
                urlConnection.disconnect();
            }

            return "";
        }
        //**********************************************************************
        protected void onPreExecute()
        {
            Snackbar.make(getView(),"Please Wait...Words are being Checked", Snackbar.LENGTH_SHORT).show();
            loadingBar.setVisibility(View.VISIBLE);
        }
        //**********************************************************************
        protected void onPostExecute(String numberOfCorrectAns)
        {
            FactoryClass objFactory = new FactoryClass(getActivity(),"UserResults");
            String currentDateAndTime = DateFormat.getDateTimeInstance().format(new Date());
            String userInformation;
            int userScore = 0;

            //if nothing is returned from doInBackground.
            //User scores nothings and loses
            if(numberOfCorrectAns.isEmpty())
            {
                numberOfCorrectAns = "0";
            }
            else
            {
                userScore = Integer.parseInt(numberOfCorrectAns) * 20;
            }

            loadingBar.setVisibility(View.GONE);
            addWord.setClickable(false);

            //Display and write relevant user information
            userInformation = String.valueOf(userScore) + "\tDate: " + currentDateAndTime;
            objFactory.writeDate(String.valueOf(userInformation));
            Snackbar.make(getView(),"You got " + numberOfCorrectAns + " words correct out of " + String.valueOf(word.size()) +
                                  "\nYour Score is " + String.valueOf(userScore), Snackbar.LENGTH_INDEFINITE).show();

        }
        //**********************************************************************
    }
}
