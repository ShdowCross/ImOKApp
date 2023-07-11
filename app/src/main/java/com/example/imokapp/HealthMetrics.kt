package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.heartRate
import com.example.imokapp.ImOKApp.Companion.heightCM
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.muscleMass
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.uWeight
import kotlinx.android.synthetic.main.activity_health_metrics.*

class HealthMetrics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_metrics)
        musclePercentageTV.text = "$muscleMass%"
        weightKgTV.text = weight.toString()
        heightCmTV.text = heightCM.toString()
        var bmiVal = (weight.toFloat() / (heightMeter * heightMeter)).toFloat()
        var formattedBMI = String.format("%.3f", bmiVal)
        bmiMessurementTV.text = formattedBMI
        bpmmHgTV.text = "$bloodPressureSystolic / $bloodPressureDiastolic"
        hrBPMTV.text = heartRate.toString()
        mMAlertIV.visibility = View.INVISIBLE
        weightAlertIV.visibility = View.INVISIBLE
        bmiAlertIV.visibility = View.INVISIBLE
        bpAlertIV.visibility = View.INVISIBLE
        hrAlertIV.visibility = View.INVISIBLE
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""

        if (highBP){
            bpAlertIV.visibility = View.VISIBLE
            message += "There's a slight increase in blood pressure, take it easy.\n"
        }
        if (lowBP){
            bpAlertIV.visibility = View.VISIBLE
            message += "There's a slight decrease in blood pressure, are you feeling ok?\n"
        }
        if (uWeight){
            weightAlertIV.visibility = View.VISIBLE
            message += "Your weight has dropped, how are you?"
        }

        var btn = findViewById<Button>(R.id.manualInputBtn)
        btn.setOnClickListener() {
            var myIntent = Intent(this, ManualInput::class.java)
            startActivity(myIntent)
        }

        //to be changed to linear layout of whole item
        bloodPressureIV.setOnClickListener() {
            var toBpGraph = Intent(this, BpGraph::class.java)
            startActivity(toBpGraph)
        }

        //to be changed to linear layout of whole item
        BmiIV.setOnClickListener() {
            var toBmiGraph = Intent(this, WeightGraph::class.java)
            startActivity(toBmiGraph)
        }
    }
}