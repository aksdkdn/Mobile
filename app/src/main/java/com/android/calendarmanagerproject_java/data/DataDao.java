package com.android.calendarmanagerproject_java.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {


    @Insert
    void insertExercise(ExerciseData dataTable);

    @Update
    void updateExercise(ExerciseData dataTable);

    @Query("DELETE FROM ExerciseData WHERE id = :id")
    void deleteByExerciseId(int id);


    @Query("DELETE FROM ExerciseData")
    void deleteExercise();


    @Query("SELECT * FROM ExerciseData")
    List<ExerciseData> getExerciseAll();

    @Query("SELECT MAX(id) FROM ExerciseData")
    int getMaxId();
}




















