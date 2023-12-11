package com.android.calendarmanagerproject_java;

import static java.lang.Thread.sleep;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.android.calendarmanagerproject_java.data.DbExercise;
import com.android.calendarmanagerproject_java.data.ExerciseData;
import com.android.calendarmanagerproject_java.data.ExerciseDataBase;
import com.android.calendarmanagerproject_java.databinding.ActivityExerciseBinding;
import com.android.calendarmanagerproject_java.databinding.SettingDlgBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    private ActivityExerciseBinding binding;
    private ExerciseDataBase db;
    private ArrayList<ExerciseData> exerciseList;
    private String curDate;
    private ExerciseAdapter exerciseAdapter;

    private ArrayList<DbExercise> dbExercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ExerciseDataBase.getAppDatabase(this);



        dbExercise = new ArrayList<>();
        dbExercise.add(new DbExercise("벤치프레스", ResourcesCompat.getDrawable(getResources(), R.drawable.benchpress, null)));
        dbExercise.add(new DbExercise("스쿼트", ResourcesCompat.getDrawable(getResources(), R.drawable.squat, null)));
        dbExercise.add(new DbExercise("데드리프트", ResourcesCompat.getDrawable(getResources(), R.drawable.deadlift, null)));

        init();
        receiveData();
        itemClickEvent();
        Log.i("##INFO", "onCreate(): dbExercise.get(spExerciseName.getSelectedItemPosition()).getName() = "+dbExercise.get(0).getName());
    }

    private void init() {
        exerciseList = new ArrayList<>();
        binding.reExerciseList.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(exerciseList,dbExercise, new OnItemListener() {
            @Override
            public void onItemAdd(ExerciseData exerciseData) {
                Log.i("##INFO", "onItemAdd(): exerciseData = " + exerciseData.getName() + ", " + exerciseData.getWeight() + ", " + exerciseData.getSet());
                updateExerciseData(exerciseData);
            }

            @Override
            public void onItemDelete(ExerciseData exerciseData) {
                deleteExerciseData(exerciseData);
            }
        });

        binding.reExerciseList.setAdapter(exerciseAdapter);

    }

    private void receiveData() {
        curDate = getIntent().getStringExtra("date");
        exerciseList = (ArrayList<ExerciseData>) getIntent().getSerializableExtra("exerciseList");
        binding.tvExerciseTitle.setText(curDate + "운동 루틴");

        if (exerciseList == null) {
            exerciseList = new ArrayList<>();
        } else {
            exerciseAdapter.setExerciseList(exerciseList);
        }
    }

    private void itemClickEvent() {
        binding.imAddExercise.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ArrayList<String> dbExerciseName = new ArrayList<>();
            for (int i = 0; i < dbExercise.size(); i++) {
                dbExerciseName.add(dbExercise.get(i).getName());
            }
            Spinner spExerciseName = new Spinner(this);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dbExerciseName);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spExerciseName.setAdapter(adapter);

            builder.setView(spExerciseName);
            builder.setTitle("운동 추가");
            builder.setPositiveButton("추가", (dialog, which) -> {
                saveExerciseData(new ExerciseData(curDate, dbExercise.get(spExerciseName.getSelectedItemPosition()).getName(), "", ""));
            });
            builder.setNegativeButton("취소", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        binding.imBackExercise.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void saveExerciseData(ExerciseData exerciseData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("##INFO", "run(): exercise = " + exerciseData.getId() + ", " + exerciseData.getDate() + ", " + exerciseData.getName() + ", " + exerciseData.getWeight() + ", " + exerciseData.getSet());

                db.dataDao().insertExercise(exerciseData);
                int insertedId = db.dataDao().getMaxId();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("##INFO", "run(): id = " + insertedId);
                        exerciseData.setId(insertedId);
                        exerciseList.add(exerciseData);
                        exerciseAdapter.setExerciseList(exerciseList);
                    }
                });

            }
        }).start();
    }

    private void updateExerciseData(ExerciseData exerciseData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.dataDao().updateExercise(exerciseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < exerciseList.size(); i++) {
                            if (exerciseList.get(i).id == exerciseData.id) {
                                Log.i("##INFO", "run(): update data // exerciseData = " + exerciseData.getName() + ", weight = " + exerciseData.getWeight() + ", set = " + exerciseData.getSet() + ", id = " + exerciseData.id);
                                exerciseList.set(i, exerciseData);
                            }
                        }
                        exerciseAdapter.setExerciseList(exerciseList);
                    }
                });
            }
        }).start();
    }

    private void deleteExerciseData(ExerciseData exerciseData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("##INFO", "run(): exercise = " + exerciseData.id + ", " + exerciseData.getDate() + ", " + exerciseData.getName() + ", " + exerciseData.getWeight() + ", " + exerciseData.getSet());
                db.dataDao().deleteByExerciseId(exerciseData.id);
            }
        }).start();
    }

}































