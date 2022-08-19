package com.example.lucky13.activities;

import static com.example.lucky13.utils.Utils.calculateBMIValue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucky13.R;
import com.example.lucky13.activities.WelcomePage;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.text.DecimalFormat;

public class BMICalculatorActivity extends AppCompatActivity {


    RulerValuePicker weightRulerPicker,
                    heightRulerPicker;
    Button correctBMIButton;
    TextView bmiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        weightRulerPicker = findViewById(R.id.bmi_calculator_weightPicker);
        heightRulerPicker = findViewById(R.id.bmi_calculator_heightPicker);
        bmiText = findViewById(R.id.bmi_calculator_bmiTextView);
        correctBMIButton = findViewById(R.id.bmi_calculator_correctBMIButton);

        DecimalFormat df =new DecimalFormat("#.#");

        weightRulerPicker.selectValue(60);
        heightRulerPicker.selectValue(150);

        final double[] weight = new double[1];
        final double[] height = new double[1];

        weightRulerPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {

                weight[0] = weightRulerPicker.getCurrentValue();

                bmiText.setText(df.format(calculateBMIValue(weight[0], height[0])));
            }

            @Override
            public void onIntermediateValueChange(final int selectedIntermediateValue) {

            }
        });

        heightRulerPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int i) {

                height[0] = (double) heightRulerPicker.getCurrentValue() / 100;

                bmiText.setText(df.format(calculateBMIValue(weight[0], height[0])));
            }

            @Override
            public void onIntermediateValueChange(int i) {

            }
        });

        correctBMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: trebuie sa mai modificam sa integram in path-ul pacientului (adica si extra urile)
                Intent intent = new Intent(BMICalculatorActivity.this, WelcomePage.class);
                startActivity(intent);
            }
        });
    }
}