package com.example.myapplication28;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

public class SeatedActivity extends AppCompatActivity {

    private TextView timerTextView;
    private TextView set1TextView;
    private TextView set2TextView;
    private TextView set3TextView;
    private TextView set4TextView;
    private ImageView babelImage;
    private Handler handler;
    private int currentSet = 1;
    private int currentRepetition = 0;
    private final int totalSets = 4;
    private final int totalRepetitions = 3;
    private final long setRestTimeMillis = 1 * 1000; // 1분 휴식
    private final long initialCountdownMillis = 1 * 1000; // 초기 카운트다운 시간
    private final long exerciseTimeMillis = 1 * 1000; // 1세트당 운동 시간

    private int[] imageResources = {R.drawable.seated1, R.drawable.seated2};
    private int currentImageIndex = 0;

    private static final long layoutChangeDelayMillis = 5 * 1000; // 5초

    private boolean set1Completed = false;
    private boolean set2Completed = false;
    private boolean set3Completed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seated);
        set1TextView = findViewById(R.id.set1TextView);
        set2TextView = findViewById(R.id.set2TextView);
        set3TextView = findViewById(R.id.set3TextView);
        set4TextView = findViewById(R.id.set4TextView);
        timerTextView = findViewById(R.id.timerTextView);
        babelImage = findViewById(R.id.babelImage);
        handler = new Handler();

        // Count1Activity에서 전달된 반복 횟수 받아오기
        int totalRepetitions = getIntent().getIntExtra("totalRepeat", 8);

        // 초기 카운트다운 시작
        startInitialCountdown(initialCountdownMillis, totalRepetitions);
    }

    private void startInitialCountdown(long millisUntilFinished, final int totalRepetitions) {
        // CountDownTimer를 사용하여 초기 카운트다운을 표시
        new CountDownTimer(millisUntilFinished, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished / 1000) + "초 뒤 운동을 시작하겠습니다!");
            }

            public void onFinish() {
                // 초기 카운트다운이 끝나면 운동을 시작
                timerTextView.setText("운동 시작!");
                startSetTimer(0, totalRepetitions);
                startImageChangeTimer();
            }
        }.start();
    }

    private void startSetTimer(final int seconds, final int totalRepetitions) {
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
                else if (currentSet == 4) {
                    set4TextView.setBackgroundResource(R.drawable.healthsetview_border2);
                }
                // 타이머가 종료되면 세트와 반복 횟수를 증가
                currentRepetition++;

                if (currentRepetition <= totalRepetitions) {
                    // 반복 횟수가 완료되지 않았으면 다음 반복 시작
                    startSetTimer((int) (exerciseTimeMillis / 1000), totalRepetitions);
                } else {
                    // 반복 횟수가 완료되면
                    currentRepetition = 0;  // 반복 횟수 초기화

                    // 마지막 세트까지 완료되면 종료
                    if (currentSet < totalSets) {
                        timerTextView.setText("운동 종료! " + (setRestTimeMillis / 1000) + "초동안 휴식합니다!");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                timerTextView.setText("휴식 중");
                                currentSet++;
                                startSetTimer(0, totalRepetitions);  // 휴식이 끝나면 다음 세트 시작
                            }
                        }, setRestTimeMillis);
                    } else {
                        // 여기에서 새로운 레이아웃으로 전환하는 코드를 추가
                        timerTextView.setText("운동 종료! " + (layoutChangeDelayMillis / 1000) + "초 후 다음 루틴으로 이동합니다!");

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 여기에서 새로운 레이아웃으로 전환하는 코드를 추가
                                startActivity(new Intent(SeatedActivity.this, ConcentActivity.class));
                                finish(); // 현재 액티비티 종료
                            }
                        }, layoutChangeDelayMillis);
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