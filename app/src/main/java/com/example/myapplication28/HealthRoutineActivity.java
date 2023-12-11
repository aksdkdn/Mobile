package com.example.myapplication28;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HealthRoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.healthroutine);

        // 각 루틴 버튼에 대한 클릭 이벤트 처리
        Button btnBicepsRoutine = findViewById(R.id.btnBicepsRoutine);
        btnBicepsRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이두 운동 루틴으로 전환
                startRoutineActivity(NanidoActivity.class);
            }
        });

        Button btnShoulderRoutine = findViewById(R.id.btnShoulderRoutine);
        btnShoulderRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 어깨 운동 루틴으로 전환
                startRoutineActivity(NanidoActivity2.class);
            }
        });

        Button btnTricepsRoutine = findViewById(R.id.btnTricepsRoutine);
        btnTricepsRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 삼두 운동 루틴으로 전환
                startRoutineActivity(NanidoActivity3.class);
            }
        });

        Button btnLegRoutine = findViewById(R.id.btnLegRoutine);
        btnLegRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 하체 운동 루틴으로 전환
                startRoutineActivity(NanidoActivity4.class);
            }
        });

        Button btnChestRoutine = findViewById(R.id.btnChestRoutine);
        btnChestRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 가슴 운동 루틴으로 전환
                startRoutineActivity(NanidoActivity5.class);
            }
        });

        Button btnBackRoutine = findViewById(R.id.btnBackRoutine);
        btnBackRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 등 운동 루틴으로 전환
                startRoutineActivity(NanidoActivity6.class);
            }
        });
    }

    private void startRoutineActivity(Class<?> routineActivityClass) {
        Intent intent = new Intent(HealthRoutineActivity.this, routineActivityClass);
        startActivity(intent);
    }
}
