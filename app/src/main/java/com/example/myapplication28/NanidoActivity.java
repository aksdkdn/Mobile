// NanidoActivity.java

package com.example.myapplication28;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NanidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nanido);

        // 초급자 버튼 클릭 이벤트
        Button btnBeginner = findViewById(R.id.btnBeginner);
        btnBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EduchoboActivity로 전환
                Intent intent = new Intent(NanidoActivity.this, EduchoboActivity.class);
                startActivity(intent);
            }
        });

        // 나머지 코드들...
    }
}
