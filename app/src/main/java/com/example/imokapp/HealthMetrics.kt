package com.example.imokapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.bpNotificationOn
import com.example.imokapp.ImOKApp.Companion.firstRun
import com.example.imokapp.ImOKApp.Companion.heartRate
import com.example.imokapp.ImOKApp.Companion.heightCM
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.muscleMass
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.systolic
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.example.imokapp.ImOKApp.Companion.weightNotificationOn

class HealthMetrics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_metrics)
        val lowColor = ContextCompat.getColor(this, R.color.blue)
        val normalColor = ContextCompat.getColor(this, R.color.green)
        val highColor = ContextCompat.getColor(this, R.color.red)

        val weightValue = intent.getFloatExtra("weight",0f)
        val muscleTV = findViewById<TextView>(R.id.musclePercentageTV)
        val weightTV = findViewById<TextView>(R.id.weightKgTV)
        val heightTV = findViewById<TextView>(R.id.heightCmTV)
        val bmiTV = findViewById<TextView>(R.id.bmiMeasurementTV)
        val bpTV = findViewById<TextView>(R.id.bpmmHgTV)
        val hrTV = findViewById<TextView>(R.id.hrBPMTV)
        val bpDiagnosis = findViewById<TextView>(R.id.bpRangeTV)
        val bmiDiagnosis = findViewById<TextView>(R.id.bmiRangeTV)

        val AlertIV = findViewById<ImageView>(R.id.mMAlertIV)
        val weightAlertIV = findViewById<ImageView>(R.id.weightAlertIV)
        val bmiAlertIV = findViewById<ImageView>(R.id.bmiAlertIV)
        val bpAlertIV = findViewById<ImageView>(R.id.bpAlertIV)
        val hrAlertIV = findViewById<ImageView>(R.id.hrAlertIV)
        val bloodPressure = findViewById<LinearLayout>(R.id.bloodPressure)
        val weightIV = findViewById<LinearLayout>(R.id.weightLayout)
        val profilePictureIV = findViewById<ImageView>(R.id.profilePictureIV)
        val surveyBtn = findViewById<Button>(R.id.surveyBtn)


        muscleTV.text = "$muscleMass%"
        weightTV.text = weight.toString()
        heightTV.text = heightCM.toString()
        var bmiVal = weight / (heightMeter * heightMeter)
        var formattedBMI = String.format("%.2f", bmiVal)
        bmiTV.text = formattedBMI
        bpTV.text = "$bloodPressureSystolic / $bloodPressureDiastolic"
        hrTV.text = "$heartRate bpm"
        AlertIV.visibility = View.INVISIBLE
        weightAlertIV.visibility = View.INVISIBLE
        bmiAlertIV.visibility = View.INVISIBLE
        bpAlertIV.visibility = View.INVISIBLE
        hrAlertIV.visibility = View.INVISIBLE

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        if (systolic.isNotEmpty()){
            if (highBP){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "High BP"
                bpDiagnosis.setTextColor(highColor)
                if (bpNotificationOn) {
                    message += "There's a slight increase in blood pressure, take it easy.\n"
                    bpNotificationOn = false
                }
            }
            else if (lowBP){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Low BP"
                bpDiagnosis.setTextColor(lowColor)
                if (bpNotificationOn){
                    message += "There's a slight decrease in blood pressure, are you feeling ok?\n"
                    bpNotificationOn = false
                }
            }
            else if(!highBP || !lowBP){
                bpDiagnosis.text = "Normal BP"
                bpDiagnosis.setTextColor(normalColor)
            }
        }
        if(weightArray.isNotEmpty()){
            if (uWeight){
                weightAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bmiDiagnosis.text = "Underweight"
                bmiDiagnosis.setTextColor(lowColor)
                if (weightNotificationOn){
                    message += "Your weight has dropped, how are you?"
                    weightNotificationOn = false
                }
            }
            else{
                bmiDiagnosis.text = "Normal"
                bmiDiagnosis.setTextColor(normalColor)
            }
        }
        alertDialogBuilder.setMessage(message)
        if (message != "") {
            alertDialogBuilder.show()
        }
        var inputBtn = findViewById<Button>(R.id.manualInputBtn)
        inputBtn.setOnClickListener() {
            var myIntent = Intent(this, ManualInput::class.java)
            startActivity(myIntent)
        }

        surveyBtn.setOnClickListener(){
            var toSurvey = Intent(this, Survey::class.java)
            startActivity(toSurvey)
        }
        profilePictureIV.setOnClickListener(){
            var toProfile = Intent(this, PatientProfile::class.java)
            startActivity(toProfile)
        }
        //to be changed to linear layout of whole item
        bloodPressure.setOnClickListener() {
            var toBpGraph = Intent(this, BpGraph::class.java)
            startActivity(toBpGraph)
        }
        //to be changed to linear layout of whole item
        weightIV.setOnClickListener() {
            var toWeightGraph = Intent(this, WeightGraph::class.java)
            toWeightGraph.putExtra("weight", weightValue)
            startActivity(toWeightGraph)
        }
    }
}