package com.android.calendarmanagerproject_java.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



@Database(entities = ExerciseData.class, version = 2)

public abstract class ExerciseDataBase extends RoomDatabase {
    private static ExerciseDataBase INSTANCE = null;
    public abstract DataDao dataDao();

    public synchronized static ExerciseDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ExerciseDataBase.class, "exercise_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}




























