package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by garywhite61 on 19/07/2017.
 * API Contract for the Habit Tracker app.
 */

public final class HabitContract  {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {
    }

    /**
     * Inner class that defines constant values for the habit database table.
     * Each entry in the table represents a single person.
     */
    public static final class HabitEntry implements BaseColumns {

        // Name of database table
        public static final String TABLE_NAME = "habit";

        // Unique ID number for the person (only for use in the database table).
        public static final String _ID = BaseColumns._ID;

        // Name of the person.
        public static final String COLUMN_PERSON_NAME = "name";

        // Did they visit the gym today?
        public static final String COLUMN_GYM = "gym";

        // Which fruit did you eat today?
        public static final String COLUMN_FRUIT = "fruit";

        // Glasses of water drunk today
        public static final String COLUMN_WATER = "glasses_of_water";

        // Amount of calories consumed today
        public static final String COLUMN_CALORIES = "calories";

        // Possible values for Gym.
        public static final int GYM_YES = 1;
        public static final int GYM_NO = 0;
    }
}
