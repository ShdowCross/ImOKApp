package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_manual_input.*

class ManualInput : AppCompatActivity() {

    val HEALTH_METRICS_RESULT_CODE = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)

//        saveBtn.setOnClickListener() {
//            var myIntent = Intent(this, HealthMetrics::class.java)
//            startActivity(myIntent)
//        }

        cancelBtn.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
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

        if (bloodPressureET.text.isEmpty()) {
            bloodPressureET.error = "Field empty"
            errorExist = true
        }

        if (errorExist)
            return
            // Convert text to string
            var weight = weightET.text.toString()
            var heartRate = heartRateET.text.toString()
            var bloodPressure = bloodPressureET.text.toString()

            var myIntent = Intent(this, HealthMetrics::class.java)
            var extras =  Bundle();
            extras.putString("enter_weight",weight)
            extras.putString("enter_heart_rate",heartRate)
            extras.putString("enter_blood_pressure",bloodPressure)
            myIntent.putExtras(extras)
            startActivityForResult(myIntent,HEALTH_METRICS_RESULT_CODE)

    }
}