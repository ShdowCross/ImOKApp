package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.imokapp.ImOKApp.Companion.addBpData
import com.example.imokapp.ImOKApp.Companion.addWeightData
import com.example.imokapp.ImOKApp.Companion.calculateBMI
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_manual_input.*
import java.io.File
import java.io.IOException


class ManualInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)
        val healthMetrics = ImOKApp.Companion.HealthMetrics()
        val healthStatus = ImOKApp.Companion.HealthStatus()
        val healthNotification = ImOKApp.Companion.HealthNotifications()
        val graphData = ImOKApp.Companion.GraphData()
        
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
            healthMetrics.weight = weightValue.toFloat()
            var heartRateValue = heartRateET.text.toString()
            healthMetrics.heartRate = heartRateValue.toFloat()
            var bmiValue = calculateBMI(healthMetrics.weight, healthMetrics.heightMeter, healthMetrics)
            healthStatus.grade1Hypertension = false
            healthStatus.grade2Hypertension = false
            healthStatus.grade3Hypertension = false
            healthStatus.grade4Hypertension = false
            // Isolated systolic hypertension
            if (bloodPressureSystolicValue.toInt() >= 130 && bloodPressureDiastolicValue.toInt() < 80){
                healthStatus.highBP = false
                healthStatus.lowBP = false
                healthStatus.isolatedSystolic = true
                healthStatus.isolatedDiastolic = false
            }
            // Isolated diastolic hypertension
            else if(bloodPressureSystolicValue.toInt() < 130 && bloodPressureDiastolicValue.toInt() >= 80){
                healthStatus.highBP = false
                healthStatus.lowBP = false
                healthStatus.isolatedSystolic = false
                healthStatus.isolatedDiastolic = true
            }
            else if (bloodPressureSystolicValue.toInt() >= 120 || bloodPressureDiastolicValue.toInt() >= 80){
                healthStatus.highBP = true
                // HYPERTENSIVE CRISIS
                if ((bloodPressureSystolicValue.toInt() >= 180 || bloodPressureDiastolicValue.toInt() >= 120) || (bloodPressureSystolicValue.toInt() >= 180 && bloodPressureDiastolicValue.toInt() >= 120)){
                    healthStatus.grade1Hypertension = false
                    healthStatus.grade2Hypertension = false
                    healthStatus.grade3Hypertension = false
                    healthStatus.grade4Hypertension = true

                }
                // HIGH BLOOD PRESSURE (HYPERTENSION) STAGE 2
                else if (bloodPressureSystolicValue.toInt() in 140..179 || bloodPressureDiastolicValue.toInt() in 90..119){
                    healthStatus.grade1Hypertension = false
                    healthStatus.grade2Hypertension = false
                    healthStatus.grade3Hypertension = true
                    healthStatus.grade4Hypertension = false
                }
                // HIGH BLOOD PRESSURE (HYPERTENSION) STAGE 1
                else if (bloodPressureSystolicValue.toInt() in 130..139 || bloodPressureDiastolicValue.toInt() in 80..89){
                    healthStatus.grade1Hypertension = false
                    healthStatus.grade2Hypertension = true
                    healthStatus.grade3Hypertension = false
                    healthStatus.grade4Hypertension = false
                }
                // ELEVATED
                else if (bloodPressureSystolicValue.toInt() in 120..129 && bloodPressureDiastolicValue.toInt() < 80){
                    healthStatus.grade1Hypertension = true
                    healthStatus.grade2Hypertension = false
                    healthStatus.grade3Hypertension = false
                    healthStatus.grade4Hypertension = false
                }
                healthStatus.lowBP = false
                healthStatus.isolatedSystolic = false
                healthStatus.isolatedDiastolic = false
            }
            else{
                // LOW BP
                if(bloodPressureSystolicValue.toInt() < 90 || bloodPressureDiastolicValue.toInt() < 60){
                    healthStatus.highBP = false
                    healthStatus.lowBP = true
                    healthStatus.isolatedSystolic = false
                    healthStatus.isolatedDiastolic = false
                }
                // NORMAL BP
                else{
                    healthStatus.highBP = false
                    healthStatus.lowBP = false
                    healthStatus.isolatedSystolic = false
                    healthStatus.isolatedDiastolic = false
                }
            }
            // High Risk healthMetrics.BMI
            if (bmiValue >= "27.5".toFloat()) {
                healthStatus.highRiskBmi = true
                healthStatus.moderateRiskBmi = false
                healthStatus.lowRiskBmi = false
                healthStatus.uWeight = false
            }
            // Moderate Risk healthMetrics.BMI
            else if (bmiValue.toInt() >= 23 && bmiValue.toInt() < 27.5) {
                healthStatus.highRiskBmi = false
                healthStatus.moderateRiskBmi = true
                healthStatus.lowRiskBmi = false
                healthStatus.uWeight = false
            }
            // Low Risk healthMetrics.BMI
            else if (bmiValue.toInt() >= 18.5 && bmiValue.toInt() < 23) {
                healthStatus.highRiskBmi = false
                healthStatus.moderateRiskBmi = false
                healthStatus.lowRiskBmi = true
                healthStatus.uWeight = false
            }
            // Underweight
            else{
                healthStatus.highRiskBmi = false
                healthStatus.moderateRiskBmi = false
                healthStatus.lowRiskBmi = false
                healthStatus.uWeight = true
            }
            healthMetrics.BMI = bmiValue
            healthMetrics.bloodPressureSystolic = bloodPressureSystolicValue.toInt()
            healthMetrics.bloodPressureDiastolic = bloodPressureDiastolicValue.toInt()

            addBpData(healthMetrics.bloodPressureSystolic, healthMetrics.bloodPressureDiastolic, graphData)
            addWeightData(healthMetrics.weight, graphData)

            // Writing data to a file
            val gson = Gson()

// Convert ArrayList to regular List before writing
            val systolicList = graphData.systolic.toList()
            val diastolicList = graphData.diastolic.toList()
            val weightList = graphData.weightArray.toList()

            val data = mapOf(
                "systolic" to systolicList,
                "diastolic" to diastolicList,
                "weight" to weightList,
                "timeList" to graphData.timeList
            )

            val json = gson.toJson(data)

            try {
                val file = File(filesDir, "data.json")
                file.writeText(json)
            } catch (e: IOException) {
                e.printStackTrace()
            }


            var newValuesSubmit = Intent(this,HealthMetrics::class.java)
            healthNotification.bpNotificationOn = true
            healthNotification.weightNotificationOn = true
            startActivity(newValuesSubmit)
        }
    }
}