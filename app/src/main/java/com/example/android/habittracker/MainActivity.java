package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitDbHelper;
import com.example.android.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mHabitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mHabitDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private Cursor displayDatabaseInfo() {

        SQLiteDatabase db = mHabitDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_PERSON_NAME,
                HabitEntry.COLUMN_GYM,
                HabitEntry.COLUMN_FRUIT,
                HabitEntry.COLUMN_WATER,
                HabitEntry.COLUMN_CALORIES};

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.view);
        try {

            displayView.setText("Data the table contains " + cursor.getCount() + "\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_PERSON_NAME + " _ " +
                    HabitEntry.COLUMN_GYM + " - " +
                    HabitEntry.COLUMN_FRUIT + " - " +
                    HabitEntry.COLUMN_WATER + " - " +
                    HabitEntry.COLUMN_CALORIES + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_PERSON_NAME);
            int gymColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_GYM);
            int fruitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_FRUIT);
            int waterColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_WATER);
            int calColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_CALORIES);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentGym = cursor.getInt(gymColumnIndex);
                String currentFruit = cursor.getString(fruitColumnIndex);
                int currentWater = cursor.getInt(waterColumnIndex);
                int currentCal = cursor.getInt(calColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentGym + " - " +
                        currentFruit + " - " +
                        currentWater + " - " +
                        currentCal));
            }
        } finally {
            cursor.close();
        }
    return cursor;
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu options from the res/menu/menu_catalog.xml file.
            // This adds menu items to the app bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.action_insert_dummy_data:
                    displayDatabaseInfo();
                    return true;
                case R.id.action_delete_all_data:
                    return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
