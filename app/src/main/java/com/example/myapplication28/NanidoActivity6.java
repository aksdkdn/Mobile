package com.example.myapplication28;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NanidoActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nanido2);

        Button btnBeginner = findViewById(R.id.btnBeginner);
        Button btnIntermediate = findViewById(R.id.btnIntermediate);
        Button btnAdvanced = findViewById(R.id.btnAdvanced);

        // 초급자 버튼 클릭 이벤트
        btnBeginner.setOnClickListener(v -> startCountdown("beginner"));

        // 중급자 버튼 클릭 이벤트
        btnIntermediate.setOnClickListener(v -> startCountdown("intermediate"));

        // 상급자 버튼 클릭 이벤트
        btnAdvanced.setOnClickListener(v -> startCountdown("advanced"));
    }

    private void startCountdown(String difficulty) {
        int repeat;

        // 난이도에 따른 횟수
        switch (difficulty) {
            case "beginner":
                repeat = 8;
                break;
            case "intermediate":
                repeat = 10;
                break;
            case "advanced":
                repeat = 12;
                break;
            default:
                repeat = 8; // 기본값으로 초급자 설정
        }

        Intent intent = new Intent(NanidoActivity6.this, Count6Activity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("totalRepeat", repeat);
        startActivity(intent);
    }
}