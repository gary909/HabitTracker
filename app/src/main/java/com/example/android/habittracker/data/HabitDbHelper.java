package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by garywhite61 on 19/07/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    /// Name of the database file
    private static final String DATABASE_NAME = "habit.db";

    //Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     *
     * @param context of the app
     */
    public HabitDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the table
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_PERSON_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_GYM + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_FRUIT + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_WATER + " INTEGER, "
                + HabitEntry.COLUMN_CALORIES + " INTEGER NOT NULL);";

        Log.e("QUERY", "QUERY" + SQL_CREATE_ENTRIES );
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
