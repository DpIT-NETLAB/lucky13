package com.example.lucky13.activities.patient_path.menu_options;

import static com.example.lucky13.utils.Utils.calculateBMIValue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucky13.R;
import com.example.lucky13.activities.patient_path.PatientHomeScreenActivity;
import com.example.lucky13.activities.patient_path.side_bar.DrawerBaseActivity;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.text.DecimalFormat;

public class UpdateBMIActivity extends DrawerBaseActivity {

    RulerValuePicker weightRulerPicker,
            heightRulerPicker;
    Button correctBMIButton;
    TextView bmiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bmiactivity);

//        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Update BMI");
//        setContentView(activityDashboardBinding.getRoot());

        bmiText = findViewById(R.id.update_bmi_bmiTextView);
        weightRulerPicker = findViewById(R.id.update_bmi_weightPicker);
        heightRulerPicker = findViewById(R.id.update_bmi_heightPicker);
        correctBMIButton = findViewById(R.id.update_bmi_updateBMIButton);

        DecimalFormat df = new DecimalFormat("#.#");

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

                //TODO: actually update the user
                Intent intent = new Intent(UpdateBMIActivity.this, PatientHomeScreenActivity.class);

                startActivity(intent);
            }
        });
    }
}