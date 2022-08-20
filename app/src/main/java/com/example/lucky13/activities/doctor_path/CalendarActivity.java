package com.example.lucky13.activities.doctor_path;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucky13.R;
import com.example.lucky13.adapters.CalendarAdapter;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearTextView;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private ArrayList<TextView> daysInMonth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        initWidgets();
        selectedDate = LocalDate.now();
        System.out.println(selectedDate);
        setMonthView();
    }

    private void initWidgets() {

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearTextView = findViewById(R.id.calendarMonthYearTextView);
    }

    public void setMonthView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            monthYearTextView.setText(getMonthFromLocalDate(selectedDate));
            daysInMonth = daysInMonthArray(selectedDate);

            CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);

            calendarRecyclerView.setLayoutManager(layoutManager);
            calendarRecyclerView.setAdapter(calendarAdapter);
        }
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<TextView> daysInMonthArray(LocalDate date) {

        ArrayList<TextView> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() - 1;

        for (int i = 1; i <= 42; i++) {

            TextView textView = new TextView(this);
//            Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/montserrat_basic.ttf");
//            textView.setTypeface(typeface);

            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                textView.setText("");
                daysInMonthArray.add(textView);
            }
            else {
                textView.setText(String.valueOf(i - dayOfWeek));
                daysInMonthArray.add(textView);
            }
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getMonthFromLocalDate(@NonNull LocalDate localDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return localDate.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {

        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {

        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, @NonNull String dayText) {

        Intent intent = new Intent(CalendarActivity.this, ApptEventFormActivity.class);

        Month monthObject = selectedDate.getMonth();
        Integer month = monthObject.getValue();
        Integer day = Integer.valueOf(daysInMonth.get(position).getText().toString());

        intent.putExtra("day", day);
        intent.putExtra("month", month);

        startActivity(intent);

//        TODO: o sa incerc sa fac la un moment dat sa poti da long click pe elementele din rv
//        ArrayList<TextView> textViews = daysInMonthArray(selectedDate);
//        TextView textView = textViews.get(position);
//        textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                Toast.makeText(CalendarActivity.this, "OOKKKKK DECI MERGEEEE", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }
}