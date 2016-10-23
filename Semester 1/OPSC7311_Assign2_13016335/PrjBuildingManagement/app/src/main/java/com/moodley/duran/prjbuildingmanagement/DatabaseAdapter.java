/*
Lecturer :Rajesh Chanderman
Student : Duran Moodley 13016335
Description : contains all database functions CRUD
Updated: 5/22/2016
DatabaseAdapter.java
Assignment : 2
 */
package com.moodley.duran.prjbuildingmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {


    //Table One Colums
    private static final String KEY_TENANT_NAME = "tenantName";
    private static final String KEY_ID_NUMBER = "idNumber";
    private static final String KEY_NUMBER_OF_OCCUPANTS = "numberOccupants";
    private static final String KEY_CONTACT_NUMBER = "contactNumber";
    private static final String KEY_EMERGENCY_CONTACT = "emergencyContact";
    private static final String KEY_FLAT_NUMBER = "flatNumber";
    private static final String TAG = "DatabaseAdapter";
    //***************************************************************
    //Table two Collumns
    private static final String KEY_INCIDENT_NAME = "incidentName";
    private static final String KEY_TENANT_ID = "idTenant";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    private static final String DATABASE_NAME = "Gilroc";
    private static final String DATABASE_TABLE_TENANTS = "tbl_tenants";
    private static final String DATABASE_TABLE_INCIDENTS = "tbl_incidents";

    private static final int DATABASE_VERSION = 1;
    //***************************************************************
    private static final String DATABASE_CREATE_TENANTS =
            "create table tbl_tenants ( _id integer primary key autoincrement, " +
                                      "tenantName text not null, " +
                                      "idNumber text not null, " +
                                      "numberOccupants integer not null, " +
                                      "contactNumber text not null, " +
                                      "emergencyContact text not null, " +
                                      "flatNumber integer not null);";

    private static final String DATABASE_CREATE_INCIDENTS =
                        "create table tbl_incidents( _id integer primary key autoincrement, " +
                        "incidentName text not null, " +
                        "idTenant text not null, " +
                        "date text not null, " +
                        "time text not null);";

    private final Context context;
    private final DatabaseHelper DBHelper;
    private SQLiteDatabase dbSQL;
    //***************************************************************
    public DatabaseAdapter(Context cont)
    {
        this.context = cont;
        DBHelper = new DatabaseHelper(context);
    }
    //***************************************************************
    //Start of Inner Class
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context con)
        {
            super(con, DATABASE_NAME, null, DATABASE_VERSION);
        }
        //***************************************************************
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(DATABASE_CREATE_TENANTS);
                db.execSQL(DATABASE_CREATE_INCIDENTS);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        //***************************************************************
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tbl_teams");
            onCreate(db);
        }
    }
    //End of Inner Class
    //***************************************************************
    //---opens the database---
    public DatabaseAdapter open() throws SQLException
    {
        dbSQL = DBHelper.getWritableDatabase();
        return this;
    }
    //***************************************************************
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    //***************************************************************
    //---Inserts An incident and Tenants
    public long insertTenant(String tenantName, String idNum, int numOfOccupants , String contactNum,String emergencyContactNum , int flatNumber)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TENANT_NAME, tenantName);
        initialValues.put(KEY_ID_NUMBER, idNum);
        initialValues.put(KEY_NUMBER_OF_OCCUPANTS, numOfOccupants);
        initialValues.put(KEY_CONTACT_NUMBER, contactNum);
        initialValues.put(KEY_EMERGENCY_CONTACT, emergencyContactNum);
        initialValues.put(KEY_FLAT_NUMBER, flatNumber);
        return dbSQL.insert(DATABASE_TABLE_TENANTS, null, initialValues);
    }
    //***************************************************************
    public long insertIncident(String incidentName, String idNum , String date, String time)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_INCIDENT_NAME, incidentName);
        initialValues.put(KEY_TENANT_ID, idNum);
        initialValues.put(KEY_DATE,date);
        initialValues.put(KEY_TIME,time);
        return dbSQL.insert(DATABASE_TABLE_INCIDENTS, null, initialValues);
    }
    //***************************************************************
    public Cursor getAllIncidents()
    {
        //---gets all incident---
        return dbSQL.query(DATABASE_TABLE_INCIDENTS, new String[]{KEY_INCIDENT_NAME,
                        KEY_TENANT_ID,KEY_DATE,KEY_TIME},
                null, null, null, null, null);
    }
    //***************************************************************
    //---deletes a particular tenant---
    public boolean deleteTenant(long idNumber)
    {
        return dbSQL.delete(DATABASE_TABLE_TENANTS, KEY_ID_NUMBER + "=" + idNumber, null) > 0;
    }
    //***************************************************************
    //---retrieves a particular tenant---
    public Cursor getTenant(long rowId) throws SQLException
    {
        Cursor mCursor =
                dbSQL.query(true, DATABASE_TABLE_TENANTS, new String[] {KEY_TENANT_NAME,
                                KEY_ID_NUMBER, KEY_NUMBER_OF_OCCUPANTS, KEY_CONTACT_NUMBER,KEY_EMERGENCY_CONTACT,KEY_FLAT_NUMBER},
                        KEY_ID_NUMBER + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //***************************************************************
    //---retrieves all the tenants---
    public Cursor getAllTenants()
    {
        return dbSQL.query(DATABASE_TABLE_TENANTS, new String[] {KEY_TENANT_NAME,
                KEY_ID_NUMBER, KEY_NUMBER_OF_OCCUPANTS, KEY_CONTACT_NUMBER,KEY_EMERGENCY_CONTACT,KEY_FLAT_NUMBER},
                null, null, null, null, null);
    }
    //***************************************************************
    //---updates a tenants---
    public boolean updateTenant(long rowId, String name, int numOccupants, String contactNum, String emergencyContact, int flatNum)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TENANT_NAME, name);
        args.put(KEY_NUMBER_OF_OCCUPANTS, numOccupants);
        args.put(KEY_CONTACT_NUMBER, contactNum);
        args.put(KEY_EMERGENCY_CONTACT, emergencyContact);
        args.put(KEY_FLAT_NUMBER, flatNum);
        return dbSQL.update(DATABASE_TABLE_TENANTS, args, KEY_ID_NUMBER + "=" + rowId, null) > 0;
    }
    //***************************************************************
}
