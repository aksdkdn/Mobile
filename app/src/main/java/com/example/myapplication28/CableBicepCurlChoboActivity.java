package com.example.myapplication28;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class CableBicepCurlChoboActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView set1TextView;
    private TextView set2TextView;
    private TextView set3TextView;
    private ImageView babelImage;
    private Handler handler;
    private int currentSet = 1;
    private int currentRepetition = 0;
    private final int totalSets = 3;
    private final int totalRepetitions = 12;
    private final long setRestTimeMillis = 1 * 1000; // 1분 휴식
    private final long initialCountdownMillis = 1 * 1000; // 초기 카운트다운 시간
    private final long exerciseTimeMillis = 1 * 1000; // 1세트당 운동 시간

    private int[] imageResources = {R.drawable.cable1, R.drawable.cable2};
    private int currentImageIndex = 0;

    private boolean set1Completed = false;
    private boolean set2Completed = false;
    private boolean set3Completed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cablebicepcurl_chobo);
        set1TextView = findViewById(R.id.set1TextView);
        set2TextView = findViewById(R.id.set2TextView);
        set3TextView = findViewById(R.id.set3TextView);
        timerTextView = findViewById(R.id.timerTextView);
        babelImage = findViewById(R.id.babelImage);
        handler = new Handler();

        // 초기 카운트다운 시작
        startInitialCountdown(initialCountdownMillis);
    }

    private void startInitialCountdown(long millisUntilFinished) {
        // CountDownTimer를 사용하여 초기 카운트다운을 표시
        new CountDownTimer(millisUntilFinished, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished / 1000) + "초 뒤 운동을 시작하겠습니다!");
            }

            public void onFinish() {
                // 초기 카운트다운이 끝나면 운동을 시작
                timerTextView.setText("운동 시작!");
                startSetTimer(0);
                startImageChangeTimer();
            }
        }.start();
    }

    private void startSetTimer(final int seconds) {
        // CountDownTimer를 사용하여 남은 시간을 표시
        new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // 현재 세트와 반복 횟수를 표시
                timerTextView.setText(currentSet + "세트  " + currentRepetition + "/" + totalRepetitions);
            }

            public void onFinish() {
                if (currentSet == 1) {
                    set1TextView.setBackgroundResource(R.drawable.healthsetview_border2);
                } else if (currentSet == 2) {
                    set2TextView.setBackgroundResource(R.drawable.healthsetview_border2);
                } else if (currentSet == 3) {
                    set3TextView.setBackgroundResource(R.drawable.healthsetview_border2);
                }
                // 타이머가 종료되면 세트와 반복 횟수를 증가
                currentRepetition++;

                if (currentRepetition <= totalRepetitions) {
                    // 반복 횟수가 완료되지 않았으면 다음 반복 시작
                    startSetTimer((int) (exerciseTimeMillis / 1000));
                } else {
                    // 반복 횟수가 완료되면
                    currentRepetition = 0;  // 반복 횟수 초기화

                    // 마지막 세트가 아니면 휴식 후 다음 세트로 이동
                    if (currentSet < totalSets) {
                        timerTextView.setText("휴식 중");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                currentSet++;
                                startSetTimer(0);  // 휴식이 끝나면 다음 세트 시작
                            }
                        }, setRestTimeMillis);
                    } else {
                        // 마지막 세트까지 완료되면 종료
                        timerTextView.setText("운동 종료!");
                    }
                }
            }
        }.start();
    }

    private void startImageChangeTimer() {
        // 이미지를 2초마다 변경
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentImageIndex = (currentImageIndex + 1) % imageResources.length;
                babelImage.setImageResource(imageResources[currentImageIndex]);
                startImageChangeTimer();
            }
        }, 2000);
    }
}
