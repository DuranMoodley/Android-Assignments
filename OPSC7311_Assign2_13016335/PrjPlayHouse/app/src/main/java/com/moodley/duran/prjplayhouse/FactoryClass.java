/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : reads and writes data to a text file
Updated: 5/22/2016
FactoryClass.java
Assignment : 2
 */
package com.moodley.duran.prjplayhouse;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class FactoryClass
{
    private String textFileName;
    private final Context currentClass;
    //**************************************************************************
    public FactoryClass(Context con ,String nameOfTextFile)
    {
        currentClass = con;
        textFileName = nameOfTextFile;
    }
    //**************************************************************************
    public FactoryClass (Context con)
    {
        currentClass = con;
    }
    //**************************************************************************
    public void writeDate(String userVal) {
        //Writes the string data defined in the previous method to a text file
        FileOutputStream outputStream = null;
        try
        {
            outputStream = currentClass.openFileOutput(textFileName, Context.MODE_APPEND);
            byte[] userData = userVal.getBytes();
            outputStream.write(userData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert outputStream != null;
                outputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    //**************************************************************************
    public ArrayList<String> readData()
    {
        BufferedReader objRead = null;
        ArrayList<String> objData = new ArrayList<>();
        try {
            //Get Stream from file
            FileInputStream objInput = currentClass.openFileInput(textFileName);
            objRead = new BufferedReader(new InputStreamReader(objInput));
            String line;

            //Read each line and insert it to the array list
            while ((line = objRead.readLine()) != null)
            {
                //Read the string from text file, format it , add to array list
                objData.add(splitLine(line));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (objRead != null)
                {
                    objRead.close();
                }
            }
            catch (IOException E)
            {
                E.printStackTrace();
            }
        }
        return objData;
    }
    //**************************************************************************
    private String splitLine(String line)
    {
        //Split a string parameter and format a new string, putting each value on a new line
        String [] arrLine = line.split(",");
        String speratedLine = "";

        for (String anArrLine : arrLine)
        {
            speratedLine += anArrLine + "\n";
        }

        return speratedLine;
    }
    //**************************************************************************
    public String readDate(String nameOfTextFile)
    {
        //Reads from a text file located in the assets folder
        BufferedReader objRead = null;
        String textFileData = "";
        String line;
        try {
            objRead = new BufferedReader(new InputStreamReader(currentClass.getAssets().open(nameOfTextFile)));

            while((line = objRead.readLine()) != null)
            {
                textFileData += line + "\n";
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (objRead != null)
                {
                    objRead.close();
                }
            }
            catch (IOException E)
            {
                E.printStackTrace();
            }
        }
        return textFileData;
    }
}
