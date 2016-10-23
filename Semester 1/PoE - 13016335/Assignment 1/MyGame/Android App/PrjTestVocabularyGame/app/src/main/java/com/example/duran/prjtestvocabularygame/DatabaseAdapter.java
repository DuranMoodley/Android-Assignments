package com.example.duran.prjtestvocabularygame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {

    private static final String KEY_WORD = "Word";
    private static final String KEY_WORD_DEFINITION = "Definition";
    private static final String DATABASE_NAME = "Word_Maker";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_USER_WORDS = "tbl_UserWords";
    private static final String DATABASE_CREATE_USER_WORD =
            "create table tbl_UserWords( _id integer primary key autoincrement, " +
            "Word text not null, " +
            "Definition VARCHAR not null);";

    private final DatabaseHelper DBHelper;
    private SQLiteDatabase dbSQL;
    //***************************************************************
    public DatabaseAdapter(Context cont)
    {
        DBHelper = new DatabaseHelper(cont);
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
            db.execSQL(DATABASE_CREATE_USER_WORD);
        }
        //***************************************************************
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("My Db", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tbl_teams");
            onCreate(db);
        }
    }
    //End of Inner Class
    //***************************************************************
    //---opens the database---
    public DatabaseAdapter open() {
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
    public long insertWord(String word, String definition)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_WORD, word);
        initialValues.put(KEY_WORD_DEFINITION, definition);
        return dbSQL.insert(DATABASE_TABLE_USER_WORDS, null, initialValues);
    }
    //***************************************************************
    public Cursor getAllWordsAndDefinitions()
    {
        //---gets all words & definitions---
        return dbSQL.query(DATABASE_TABLE_USER_WORDS, new String[]{KEY_WORD,
                        KEY_WORD_DEFINITION},
                null, null, null, null, null);
    }
    //***************************************************************
    //---retrieves a particular word---
    public Cursor getWord(String word) {
        Cursor mCursor =
                dbSQL.query(true, DATABASE_TABLE_USER_WORDS, new String[] {KEY_WORD},
                        KEY_WORD + "=?", new String[]{word},
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //***************************************************************
}
