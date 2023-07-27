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
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
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
import com.example.imokapp.ImOKApp.Companion.isolatedDiastolic
import com.example.imokapp.ImOKApp.Companion.isolatedSystolic
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.systolic
import com.example.imokapp.ImOKApp.Companion.takeCareNotif
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.example.imokapp.ImOKApp.Companion.weightNotificationOn
import com.example.imokapp.ImOKApp.Companion.weightThreshold
import kotlinx.android.synthetic.main.activity_health_metrics.*

class HealthMetrics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_metrics)
        val lowColor = ContextCompat.getColor(this, R.color.blue)
        val normalColor = ContextCompat.getColor(this, R.color.green)
        val highColor = ContextCompat.getColor(this, R.color.red)
        val halfColor = ContextCompat.getColor(this, R.color.yellow_orange)
        val grade1HypertensionColor = ContextCompat.getColor(this, R.color.grade1Hypertension)
        val grade2HypertensionColor = ContextCompat.getColor(this, R.color.grade2Hypertension)
        val grade3HypertensionColor = ContextCompat.getColor(this, R.color.grade3Hypertension)
        val grade4HypertensionColor = ContextCompat.getColor(this, R.color.grade4Hypertension)
        val highRiskBmiColor = ContextCompat.getColor(this, R.color.highRiskBmi)
        val moderateRiskBmiColor = ContextCompat.getColor(this, R.color.moderateRiskBmi)

        val weightValue = intent.getFloatExtra("weight",0f)
        val muscleTV = findViewById<TextView>(R.id.musclePercentageTV)
        val weightTV = findViewById<TextView>(R.id.weightKgTV)
        val heightTV = findViewById<TextView>(R.id.heightCmTV)
        val bmiTV = findViewById<TextView>(R.id.bmiMeasurementTV)
        val bpTV = findViewById<TextView>(R.id.bpmmHgTV)
        val hrTV = findViewById<TextView>(R.id.hrBPMTV)
        val bpDiagnosis = findViewById<TextView>(R.id.bpRangeTV)
        val bmiWarningTV = findViewById<TextView>(R.id.bmiWarningTV)

        val alertIV = findViewById<ImageView>(R.id.mMAlertIV)
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
        alertIV.visibility = View.INVISIBLE
        weightAlertIV.visibility = View.INVISIBLE
        bmiAlertIV.visibility = View.INVISIBLE
        bpAlertIV.visibility = View.INVISIBLE
        hrAlertIV.visibility = View.INVISIBLE

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        if (systolic.isNotEmpty()){
            if (highBP){
                if (ImOKApp.grade1Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "Slightly High BP"
                    bpDiagnosis.setTextColor(grade1HypertensionColor)
                    if (bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        bpNotificationOn = true
                    }
                }
                else if(ImOKApp.grade2Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "High BP"
                    bpDiagnosis.setTextColor(grade2HypertensionColor)
                    if (bpNotificationOn) {
                        message += "Your blood pressure is high, take it easy and monitor your condition..\n"
                        bpNotificationOn = true
                    }
                }
                else if(ImOKApp.grade3Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "Very High BP"
                    bpDiagnosis.setTextColor(grade3HypertensionColor)
                    if (bpNotificationOn) {
                        message += "Your Blood Pressure is rising pretty high, we recommend you go see a clinic for medical advice.\n"
                        bpNotificationOn = true
                    }
                }
                else if(ImOKApp.grade4Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "GO TO A HOSPITAL"
                    bpDiagnosis.setTextColor(grade4HypertensionColor)
                    if (bpNotificationOn) {
                        message += "Your Blood Pressure is too high, Please Head To A Hospital Now. \n"
                        bpNotificationOn = true
                    }
                }
                else{
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "High BP (Unspecified)"
                    bpDiagnosis.setTextColor(highColor)
                    if (bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        bpNotificationOn = false
                    }
                }
            }
            else if (lowBP){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Low BP"
                bpDiagnosis.setTextColor(lowColor)
                if (bpNotificationOn){
                    message += "There's a decrease in blood pressure, are you feeling ok?\n Take a survey for some recommendations."
                    bpNotificationOn = false
                }
            }
            else if (isolatedSystolic){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Go see a doctor"
                bpDiagnosis.setTextColor(halfColor)
                if (bpNotificationOn){
                    message += "Your Blood Pressure is a little strange, please seek medical advice. \n"
                    bpNotificationOn = false
                }
            }
            else if (isolatedDiastolic){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Isolated Diastolic"
                bpDiagnosis.setTextColor(halfColor)
                if (bpNotificationOn){
                    message += "Your Blood Pressure is a little strange, please seek medical advice. \n"
                    bpNotificationOn = false
                }
            }
            else{
                bpDiagnosis.text = "Normal BP"
                bpDiagnosis.setTextColor(normalColor)
            }
        }
        if(weightArray.isNotEmpty()){
            if (ImOKApp.highRiskBmi) {
                bmiWarningTV.text = "High Risk BMI"
                bmiWarningTV.setTextColor(highRiskBmiColor)
                if (weightNotificationOn) {
                    message += "You're BMI is becoming high risk, go see a doctor for medical advice. \n Take our survey as well to get some recommendations!"
                    weightNotificationOn = true
                }
            } else if (ImOKApp.moderateRiskBmi) {
                bmiWarningTV.text = "Moderate Risk BMI"
                bmiWarningTV.setTextColor(moderateRiskBmiColor)
                if (weightNotificationOn) {
                    message += "You're BMI is getting a little high, which resulted in an increase in BMI. What changed? \n Take our survey to get some recommendations"
                    weightNotificationOn = false
                }
            }
            else if (ImOKApp.lowRiskBmi) {
                bmiWarningTV.text = "Normal BMI"
                bmiWarningTV.setTextColor(normalColor)
            }
            else if (uWeight) {
                bmiWarningTV.text = "Underweight"
                bmiWarningTV.setTextColor(lowColor)
                if (weightNotificationOn) {
                    message += "Your BMI is going a little low"
                    weightNotificationOn = false
                }
            }
            else {
                bmiWarningTV.text = "Unspecified"
                bmiWarningTV.setTextColor(normalColor)
            }
            if (weight >= ImOKApp.weightAverage + weightThreshold) {
                // The weight is far above the person's norm
                weightWarningTV.text = "Weight Above Norm"
                weightWarningTV.setTextColor(highColor)
                if (weightNotificationOn) {
                    message += "Your weight is significantly higher than your norm. If this is not normal, go see a doctor. \nTake our survey as well to get some recommendations!"
                    weightNotificationOn = true
                }
            } else if (weight <= ImOKApp.weightAverage - weightThreshold) {
                // The weight is far below the person's norm
                weightWarningTV.text = "Weight Below Norm"
                weightWarningTV.setTextColor(lowColor)
                if (weightNotificationOn) {
                    message += "Your weight is significantly lower than your norm. If this is not normal, go see a doctor. \nTake our survey to get some recommendations!"
                    weightNotificationOn = true
                }
            } else {
                // The weight is within the person's norm
                weightWarningTV.text = "Weight is Normal"
            }
        }
        if(takeCareNotif){
            message += "Monitor your condition, drink water and eat healthily! You should be fine in a few days."
            surveyBtn.visibility = View.GONE
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