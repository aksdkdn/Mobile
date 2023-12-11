package com.android.calendarmanagerproject_java;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.calendarmanagerproject_java.data.ExerciseData;
import com.android.calendarmanagerproject_java.data.ExerciseDataBase;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private ExerciseDataBase db;
    private ArrayList<ExerciseData> allExerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        db = ExerciseDataBase.getAppDatabase(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
        }
        setMonthView();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        allExerciseList = getExerciseData();
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<ExerciseData> curMonthData = new ArrayList<>();
        for (int i = 0; i < allExerciseList.size(); i++) {
            String date = allExerciseList.get(i).getDate();

            if (date.substring(0, 7).equals(selectedDate.toString().substring(0, 7))) {
                Log.i("##INFO", "setMonthView(): date = "+date);
                curMonthData.add(allExerciseList.get(i));
            }
        }

        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(curMonthData,daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String today = dayText + "-" + monthYearFromDate(selectedDate);
            String newDateFormat = changeDateFormat(today);

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottomsheet_dialog);
            bottomSheetDialog.show();

            TextView dateTitle = bottomSheetDialog.findViewById(R.id.tvDateTitle);
            TextView emptyText = bottomSheetDialog.findViewById(R.id.tvEmptyExerciseList);



            ArrayList<ExerciseData> curData = new ArrayList<>();

            for (int i = 0; i < allExerciseList.size(); i++) {
                if (allExerciseList.get(i).getDate().equals(newDateFormat)) {
                    curData.add(allExerciseList.get(i));
                }
            }

            if (curData.isEmpty()) {
                emptyText.setVisibility(View.VISIBLE);
            }

            emptyText.setOnClickListener(v -> {
                Intent i = new Intent(this, ExerciseActivity.class);
                i.putExtra("date", newDateFormat);
                startActivity(i);
            });

            RecyclerView exerciseRecyclerView = bottomSheetDialog.findViewById(R.id.reDateExerciseList);
            MainAdapter mainAdapter = new MainAdapter(this,curData, newDateFormat);
            exerciseRecyclerView.setAdapter(mainAdapter);
            exerciseRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

            dateTitle.setText(newDateFormat + " " + "운동 루틴");
        }
    }

    private ArrayList<ExerciseData> getExerciseData() {
        ArrayList<ExerciseData> exerciseData = new ArrayList<>();

        try {
            Thread getDataThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ExerciseData> list = db.dataDao().getExerciseAll();
                    for (ExerciseData e : list) {
                        Log.i("##INFO", "run(): e.getName() = " + e.getName() + ","+ e.getId());
                        exerciseData.add(e);
                    }
                }
            });

            getDataThread.start();
            getDataThread.join();

        } catch (InterruptedException e) {
            Log.e("##ERROR", "getExerciseData: error = " + e.getMessage());
        }

        return exerciseData;
    }

    private String changeDateFormat(String inputDate) {
        String changeDate = "";
        String inputPattern = "dd-MM월 yyyy";
        String outputPattern = "yyyy-MM-dd";


        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.KOREA);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        try {

            Date date = inputFormat.parse(inputDate);


            String outputDate = outputFormat.format(date);
            changeDate = outputDate;


            System.out.println("변환 전: " + inputDate);
            System.out.println("변환 후: " + outputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return changeDate;
    }
}








