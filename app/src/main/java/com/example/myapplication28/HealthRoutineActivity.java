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

        // 이두 운동 루틴 버튼
        Button btnBicepsRoutine = findViewById(R.id.btnBicepsRoutine);
        btnBicepsRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NanidoActivity로 전환
                Intent intent = new Intent(HealthRoutineActivity.this, NanidoActivity.class);
                startActivity(intent);
            }
        });

        // 나머지 루틴 버튼들에 대해서도 위와 같은 방식으로 클릭 이벤트를 추가할 수 있습니다.
    }
}
