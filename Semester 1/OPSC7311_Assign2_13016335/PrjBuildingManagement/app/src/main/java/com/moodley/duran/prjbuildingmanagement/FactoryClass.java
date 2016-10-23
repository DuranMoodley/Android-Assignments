/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : reads and writes data to a text file
Updated: 5/22/2016
FactoryClass.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FactoryClass
{
    private String textFileName;
    private final Context currentClass;
    //**************************************************************************
    public FactoryClass (Context con)
    {
        currentClass = con;
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
