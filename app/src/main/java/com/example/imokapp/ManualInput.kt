package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.uWeight
import kotlinx.android.synthetic.main.activity_manual_input.*
import kotlinx.android.synthetic.main.activity_survey.*

class ManualInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_input)

        saveBtn.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
        }

        cancelBtn.setOnClickListener() {
            var myIntent = Intent(this, HealthMetrics::class.java)
            startActivity(myIntent)
        }
        //Alert System input
        submit_button.setOnClickListener{
            val bloodPressureSystolicValue = bloodPressureSystolicET.text.toString()
            val bloodPressureDiastolicValue = bloodPressureDiastolicET.text.toString()
//            bmiValue = bmi(weight, height)
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
//            if(bmiValue < 18.5){
//                uWeight = true
//            }
            var newValuesSubmit = Intent(this,HealthMetrics::class.java)
            startActivity(newValuesSubmit)
        }
    }
    fun bmi(weight: Float, height: Float): Float{
        return weight / (height*height)
    }
}