package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.imokapp.ImOKApp.Companion.BMI
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weight
import kotlinx.android.synthetic.main.activity_manual_input.*

class ManualInput : AppCompatActivity() {

    val HEALTH_METRICS_RESULT_CODE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)

        cancelBtn.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
        }

        //Alert System input
//        var save = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener{
            val bloodPressureSystolicValue = bloodPressureSystolicET.text.toString()
            val bloodPressureDiastolicValue = bloodPressureDiastolicET.text.toString()
            var weightValue = weightET.text.toString()
            weight = weightValue.toFloat()
            var bmiValue = bmi(weight, heightMeter)
            if (bloodPressureSystolicValue.toInt() > 130){
                highBP = true
            }
            else if(bloodPressureSystolicValue.toInt() < 90){
                lowBP = true
            }
            if(bloodPressureDiastolicValue.toInt() > 85){
                highBP = true
            }
            else if (bloodPressureDiastolicValue.toInt() < 60){
                lowBP = true
            }
            if(bmiValue < 18.5){
                uWeight = true
            }
            BMI = bmiValue
            bloodPressureSystolic = bloodPressureSystolicValue.toInt()
            bloodPressureDiastolic = bloodPressureDiastolicValue.toInt()
            var newValuesSubmit = Intent(this,HealthMetrics::class.java)
            startActivity(newValuesSubmit)
        }
    }

    fun saveManualInput(v: View) {

        var errorExist = false

        // Check for empty fields
        if (weightET.text.isEmpty()) {
            weightET.error = "Field empty"
            errorExist = true
        }

        if (heartRateET.text.isEmpty()) {
            heartRateET.error = "Field empty"
            errorExist = true
        }

        if (bloodPressureSystolicET.text.isEmpty()) {
            bloodPressureSystolicET.error = "Field empty"
            errorExist = true
        }

        if (bloodPressureDiastolicET.text.isEmpty()) {
            bloodPressureDiastolicET.error = "Field empty"
            errorExist = true
        }

        if (errorExist)
            return
            // Convert text to string
            // var weight = weightET.text.toString()
            var heartRate = heartRateET.text.toString()
            val bloodPressureSystolicValue = bloodPressureSystolicET.text.toString()
            val bloodPressureDiastolicValue = bloodPressureDiastolicET.text.toString()
            var weightValue = weightET.text.toString()
            ImOKApp.weight = weightValue.toFloat()
            var bmiValue = bmi(ImOKApp.weight, heightMeter)
            if (bloodPressureSystolicValue.toInt() > 130){
                highBP = true
            }
            else if(bloodPressureSystolicValue.toInt() < 90){
                lowBP = true
            }
            if(bloodPressureDiastolicValue.toInt() > 85){
                highBP = true
            }
            else if (bloodPressureDiastolicValue.toInt() < 60){
                lowBP = true
            }
            if(bmiValue < 18.5){
                uWeight = true
            }
            BMI = bmiValue
            bloodPressureSystolic = bloodPressureSystolicValue.toInt()
            bloodPressureDiastolic = bloodPressureDiastolicValue.toInt()

            var myIntent = Intent(this, HealthMetrics::class.java)
            var extras =  Bundle();
            extras.putString("enter_weight",weightValue)
            extras.putString("enter_heart_rate",heartRate)
            myIntent.putExtras(extras)
            startActivityForResult(myIntent,HEALTH_METRICS_RESULT_CODE)
//
//
//            var newValuesSubmit = Intent(this,HealthMetrics::class.java)
//            startActivity(newValuesSubmit)
    }
    fun bmi(weight: Float, height: Float): Float{
        return weight / (height*height)
    }
}