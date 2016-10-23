/*
FactoryClass.java
Contains methods that reads data from text files stored in the assets folder
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 2
Date Updated : 10/11/16
 */
package catchdot.live.com.catchthedot;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FactoryClass
{
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
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (objRead != null)
                {
                    objRead.close();
                }
            } catch (IOException E) {
                E.printStackTrace();
            }
        }
        return textFileData;
    }
    //**************************************************************************
}
