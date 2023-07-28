package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.imokapp.ImOKApp.Companion.BMI
import com.example.imokapp.ImOKApp.Companion.addBpData
import com.example.imokapp.ImOKApp.Companion.addWeightData
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.bpNotificationOn
import com.example.imokapp.ImOKApp.Companion.calculateBMI
import com.example.imokapp.ImOKApp.Companion.diastolicValues
import com.example.imokapp.ImOKApp.Companion.heartRate
import com.example.imokapp.ImOKApp.Companion.heightCM
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.isolatedDiastolic
import com.example.imokapp.ImOKApp.Companion.isolatedSystolic
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.muscleMass
import com.example.imokapp.ImOKApp.Companion.systolicValues
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.weightNotificationOn
import com.example.imokapp.ImOKApp.Companion.weightValues
import kotlinx.android.synthetic.main.activity_manual_input.*


class ManualInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)

        var cancel = findViewById<Button>(R.id.cancelBtn)
        cancel.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
        }
        //Alert System input
        var save = findViewById<Button>(R.id.saveBtn)
        save.setOnClickListener{
            val bloodPressureSystolicValue = bloodPressureSystolicET.text.toString()
            val bloodPressureDiastolicValue = bloodPressureDiastolicET.text.toString()
            var weightValue = weightET.text.toString()
            weight = weightValue.toFloat()
            var heartRateValue = heartRateET.text.toString()
            heartRate = heartRateValue.toFloat()
            var bmiValue = calculateBMI(weight, heightMeter)
            ImOKApp.grade1Hypertension = false
            ImOKApp.grade2Hypertension = false
            ImOKApp.grade3Hypertension = false
            ImOKApp.grade4Hypertension = false
            // Isolated systolic hypertension
            if (bloodPressureSystolicValue.toInt() >= 130 && bloodPressureDiastolicValue.toInt() < 80){
                highBP = false
                lowBP = false
                isolatedSystolic = true
                isolatedDiastolic = false
            }
            // Isolated diastolic hypertension
            else if(bloodPressureSystolicValue.toInt() < 130 && bloodPressureDiastolicValue.toInt() >= 80){
                highBP = false
                lowBP = false
                isolatedSystolic = false
                isolatedDiastolic = true
            }
            else if (bloodPressureSystolicValue.toInt() >= 120 || bloodPressureDiastolicValue.toInt() >= 80){
                highBP = true
                // HYPERTENSIVE CRISIS
                if ((bloodPressureSystolicValue.toInt() >= 180 || bloodPressureDiastolicValue.toInt() >= 120) || (bloodPressureSystolicValue.toInt() >= 180 && bloodPressureDiastolicValue.toInt() >= 120)){
                    ImOKApp.grade1Hypertension = false
                    ImOKApp.grade2Hypertension = false
                    ImOKApp.grade3Hypertension = false
                    ImOKApp.grade4Hypertension = true

                }
                // HIGH BLOOD PRESSURE (HYPERTENSION) STAGE 2
                else if (bloodPressureSystolicValue.toInt() in 140..179 || bloodPressureDiastolicValue.toInt() in 90..119){
                    ImOKApp.grade1Hypertension = false
                    ImOKApp.grade2Hypertension = false
                    ImOKApp.grade3Hypertension = true
                    ImOKApp.grade4Hypertension = false
                }
                // HIGH BLOOD PRESSURE (HYPERTENSION) STAGE 1
                else if (bloodPressureSystolicValue.toInt() in 130..139 || bloodPressureDiastolicValue.toInt() in 80..89){
                    ImOKApp.grade1Hypertension = false
                    ImOKApp.grade2Hypertension = true
                    ImOKApp.grade3Hypertension = false
                    ImOKApp.grade4Hypertension = false
                }
                // ELEVATED
                else if (bloodPressureSystolicValue.toInt() in 120..129 && bloodPressureDiastolicValue.toInt() < 80){
                    ImOKApp.grade1Hypertension = true
                    ImOKApp.grade2Hypertension = false
                    ImOKApp.grade3Hypertension = false
                    ImOKApp.grade4Hypertension = false
                }
                lowBP = false
                isolatedSystolic = false
                isolatedDiastolic = false
            }
            else{
                // LOW BP
                if(bloodPressureSystolicValue.toInt() < 90 || bloodPressureDiastolicValue.toInt() < 60){
                    highBP = false
                    lowBP = true
                    isolatedSystolic = false
                    isolatedDiastolic = false
                }
                // NORMAL BP
                else{
                    highBP = false
                    lowBP = false
                    isolatedSystolic = false
                    isolatedDiastolic = false
                }
            }
            // High Risk BMI
            if (bmiValue >= "27.5".toFloat()) {
                ImOKApp.highRiskBmi = true
                ImOKApp.moderateRiskBmi = false
                ImOKApp.lowRiskBmi = false
                uWeight = false
            }
            // Moderate Risk BMI
            else if (bmiValue.toInt() >= 23 && bmiValue.toInt() < 27.5) {
                ImOKApp.highRiskBmi = false
                ImOKApp.moderateRiskBmi = true
                ImOKApp.lowRiskBmi = false
                uWeight = false
            }
            // Low Risk BMI
            else if (bmiValue.toInt() >= 18.5 && bmiValue.toInt() < 23) {
                ImOKApp.highRiskBmi = false
                ImOKApp.moderateRiskBmi = false
                ImOKApp.lowRiskBmi = true
                uWeight = false
            }
            // Underweight
            else{
                ImOKApp.highRiskBmi = false
                ImOKApp.moderateRiskBmi = false
                ImOKApp.lowRiskBmi = false
                uWeight = true
            }
            BMI = bmiValue
            bloodPressureSystolic = bloodPressureSystolicValue.toInt()
            bloodPressureDiastolic = bloodPressureDiastolicValue.toInt()

            systolicValues.add(bloodPressureSystolic)
            diastolicValues.add(bloodPressureDiastolic)
            weightValues.add(weight)

            addBpData(bloodPressureSystolic, bloodPressureDiastolic)
            addWeightData(weight)

            var newValuesSubmit = Intent(this,HealthMetrics::class.java)
            bpNotificationOn = true
            weightNotificationOn = true
            startActivity(newValuesSubmit)
        }
    }
}