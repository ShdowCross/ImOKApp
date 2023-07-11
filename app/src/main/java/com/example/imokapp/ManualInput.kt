package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.imokapp.ImOKApp.Companion.BMI
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.heartRate
import com.example.imokapp.ImOKApp.Companion.heightCM
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.muscleMass
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weight
import kotlinx.android.synthetic.main.activity_health_metrics.*
import kotlinx.android.synthetic.main.activity_manual_input.*
import kotlinx.android.synthetic.main.activity_survey.*

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
    fun bmi(weight: Float, height: Float): Float{
        return weight / (height*height)
    }
}