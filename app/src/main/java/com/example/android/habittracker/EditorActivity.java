package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by garywhite61 on 19/07/2017.
 */

//Allows user to create a new pet or edit an existing one.
public class EditorActivity extends AppCompatActivity {

    // EditText field to enter name
    @BindView(R.id.et_name)
    EditText mNameEditText;
    // EditText field to enter fruit name
    @BindView(R.id.et_fruit)
    EditText mFruitEditText;
    // EditText field to enter amount of water drunk
    @BindView(R.id.et_water)
    EditText mWaterEditText;
    // EditText field to enter amount of calories eaten
    @BindView(R.id.et_cal)
    EditText mCaloriesEditText;
    // EditText field to enter if visited the gym
    @BindView(R.id.spinner_gym)
    Spinner mGymSpinner;

    //  Gym Participation. The two possible valid values are in the habitContract.java file
    private int mGym = HabitEntry.GYM_NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);
        setupSpinner();
    }

    // Setup the dropdown spinner that allows the user to select the gender of the pet.
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter gymSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gym_option, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        gymSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGymSpinner.setAdapter(gymSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGymSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.option_yes))) {
                        mGym = HabitEntry.GYM_YES;
                    } else {
                        mGym = HabitEntry.GYM_NO;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mGym = HabitEntry.GYM_NO;
            }
        });
    }

    // Get user input from editor and save new person into database.
    private void insertData() {
        String nameString = mNameEditText.getText().toString().trim();
        String fruitString = mFruitEditText.getText().toString().trim();
        String waterString = mWaterEditText.getText().toString().trim();
        int water = Integer.parseInt(waterString);
        String calString = mCaloriesEditText.getText().toString().trim();
        int cal = Integer.parseInt(calString);

        HabitDbHelper habitDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and person attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_PERSON_NAME, nameString);
        values.put(HabitEntry.COLUMN_GYM, mGym);
        values.put(HabitEntry.COLUMN_FRUIT, fruitString);
        values.put(HabitEntry.COLUMN_WATER, water);
        values.put(HabitEntry.COLUMN_CALORIES, cal);

        // Inserts a new person & row ID
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        Log.e("EditorActivity", "New Row Id:" + newRowId);
        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save person to database
                insertData();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case R.id.home:
                // Navigate back to parent activity (MainActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

