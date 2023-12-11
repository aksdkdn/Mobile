package com.example.myapplication28;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class VavelCulExActivity extends AppCompatActivity {

    private ImageView babelImage;
    private Handler handler;
    private int currentImageIndex = 0;
    private int[] imageResources = {R.drawable.vavelcul1, R.drawable.vavelcul2};
    private static final long imageChangeDelayMillis = 2 * 1000; // 2초

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vavelcul_ex);

        // 여기에 다른 초기화 코드나 레이아웃과 관련된 작업을 추가할 수 있습니다.
        handler = new Handler();
        babelImage = findViewById(R.id.babelImage);

        // 이미지를 2초마다 변경
        startImageChangeTimer();

        // 뒤로 가기 이미지뷰 클릭 이벤트 설정
        ImageView backButton = findViewById(R.id.imageView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 액티비티를 종료하고 이전 화면으로 돌아감
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // VavelCulActivity로 돌아갈 때, 해당 액티비티를 초기 상태로 시작하도록 함
        Intent intent = new Intent(VavelCulExActivity.this, VavelCulActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 이전에 열려있던 모든 액티비티를 종료
        startActivity(intent);
        finish(); // 현재 액티비티 종료
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
        }, imageChangeDelayMillis);
    }
}
