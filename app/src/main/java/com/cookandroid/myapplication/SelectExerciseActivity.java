package com.cookandroid.myapplication;


import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

    public class SelectExerciseActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_exercise);

            // 운동 목록 생성
            ArrayList<String> exerciseList = new ArrayList<>();
            exerciseList.add("스쿼트");
            exerciseList.add("푸시업");
            exerciseList.add("런지");
            // ... 운동 목록을 필요한 만큼 추가

            // 리스트뷰 어댑터 설정
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseList);

            ListView exerciseListView = findViewById(R.id.exerciseListView);
            exerciseListView.setAdapter(adapter);

            // 리스트뷰 아이템 클릭 이벤트 처리
            exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                    String selectedExercise = exerciseList.get(position);
                    Toast.makeText(getApplicationContext(), "선택한 운동: " + selectedExercise, Toast.LENGTH_SHORT).show();
                    // 선택한 운동에 대한 추가 작업 수행
                }
            });
        }
    }


