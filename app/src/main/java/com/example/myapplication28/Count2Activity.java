package com.example.myapplication28;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Count2Activity extends AppCompatActivity {

    private TextView countTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count2);

        countTextView = findViewById(R.id.countTextView);

        Intent intent = getIntent();
        if (intent != null) {
            String difficulty = intent.getStringExtra("difficulty");
            int totalRepeat = intent.getIntExtra("totalRepeat", 8); // 기본값은 8

            // CountDownTimer를 사용하여 1초마다 숫자를 감소시키면서 표시
            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished는 남은 시간을 밀리초로 나타냅니다.
                    int count = (int) (millisUntilFinished / 1000) + 1;
                    countTextView.setText(String.valueOf(count));
                }

                public void onFinish() {

                    // 1초가 지나면 VavelCulActivity로 전환
                    Intent nextIntent = new Intent(Count2Activity.this, DumbelShoulderActivity.class);
                    nextIntent.putExtra("difficulty", difficulty);
                    nextIntent.putExtra("totalRepeat", totalRepeat);
                    startActivity(nextIntent);
                    finish(); // 현재 액티비티 종료
                }
            }.start();
        }
    }
}
