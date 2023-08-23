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
import com.example.imokapp.ImOKApp.Companion.graphData
import com.example.imokapp.ImOKApp.Companion.healthMetrics
import com.example.imokapp.ImOKApp.Companion.healthNotification
import com.example.imokapp.ImOKApp.Companion.healthStatus
import com.example.imokapp.ImOKApp.Companion.personInfo
import kotlinx.android.synthetic.main.activity_health_metrics.*

class HealthMetrics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_metrics)

        val infoData = ImOKApp.pullInfo(filesDir)
        personInfo = infoData?.personInfo ?: ImOKApp.Companion.PersonInfo()
        healthMetrics = infoData?.healthMetrics ?: ImOKApp.Companion.HealthMetrics()
        healthStatus = infoData?.healthStatus ?: ImOKApp.Companion.HealthStatus()
        healthNotification = infoData?.healthNotifications ?: ImOKApp.Companion.HealthNotifications()
        graphData = infoData?.graphData ?: ImOKApp.Companion.GraphData()

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
        val weightKgNormTV = findViewById<TextView>(R.id.weightKgNormTV)
        val bmiAlertIV = findViewById<ImageView>(R.id.bmiAlertIV)
        val bpAlertIV = findViewById<ImageView>(R.id.bpAlertIV)
        val hrAlertIV = findViewById<ImageView>(R.id.hrAlertIV)
        val bloodPressure = findViewById<LinearLayout>(R.id.bloodPressure)
        val weightIV = findViewById<LinearLayout>(R.id.weightLayout)
        val profilePictureIV = findViewById<ImageView>(R.id.profilePictureIV)
        val surveyBtn = findViewById<Button>(R.id.surveyBtn)

        val healthMetrics = ImOKApp.healthMetrics
        val healthStatus = ImOKApp.healthStatus
        val healthNotifications = ImOKApp.healthNotification
        val graphData = ImOKApp.graphData

        if (graphData.systolic.isNotEmpty()) {
            healthMetrics.bloodPressureSystolic = graphData.systolic.last().y.toInt()
        }
        if (graphData.diastolic.isNotEmpty()) {
            healthMetrics.bloodPressureDiastolic = graphData.diastolic.last().y.toInt()
        }
        if (graphData.weightArray.isNotEmpty()){
            healthMetrics.weight = (graphData.weightArray).last().y
        }
        muscleTV.text = "${healthMetrics.muscleMass}%"
        weightTV.text = healthMetrics.weight.toString()
        weightKgNormTV.text = "(${healthMetrics.weightAverage})"
        heightTV.text = healthMetrics.heightCM.toString()
        var bmiVal = healthMetrics.BMI
        var formattedBMI = String.format("%.2f", bmiVal)
        bmiTV.text = formattedBMI
        bpTV.text = "${healthMetrics.bloodPressureSystolic} / ${healthMetrics.bloodPressureDiastolic}"
        hrTV.text = "${healthMetrics.heartRate} bpm"
        alertIV.visibility = View.INVISIBLE
        weightAlertIV.visibility = View.INVISIBLE
        weightKgNormTV.visibility = View.INVISIBLE
        bmiAlertIV.visibility = View.INVISIBLE
        bpAlertIV.visibility = View.INVISIBLE
        hrAlertIV.visibility = View.INVISIBLE

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        if (healthMetrics.bloodPressureSystolic >= 130 && healthMetrics.bloodPressureDiastolic < 80){
            healthStatus.highBP = false
            healthStatus.lowBP = false
            healthStatus.isolatedSystolic = true
            healthStatus.isolatedDiastolic = false
        }
        else if(healthMetrics.bloodPressureSystolic < 130 && healthMetrics.bloodPressureDiastolic >= 80){
            healthStatus.highBP = false
            healthStatus.lowBP = false
            healthStatus.isolatedSystolic = false
            healthStatus.isolatedDiastolic = true
        }
        else if (healthMetrics.bloodPressureSystolic >= 120 || healthMetrics.bloodPressureDiastolic >= 80){
            healthStatus.highBP = true
            if ((healthMetrics.bloodPressureSystolic >= 180 || healthMetrics.bloodPressureDiastolic >= 120) || (healthMetrics.bloodPressureSystolic >= 180 && healthMetrics.bloodPressureDiastolic >= 120)){
                healthStatus.grade1Hypertension = false
                healthStatus.grade2Hypertension = false
                healthStatus.grade3Hypertension = false
                healthStatus.grade4Hypertension = true

            }
            else if (healthMetrics.bloodPressureSystolic in 140..179 || healthMetrics.bloodPressureDiastolic in 90..119){
                healthStatus.grade1Hypertension = false
                healthStatus.grade2Hypertension = false
                healthStatus.grade3Hypertension = true
                healthStatus.grade4Hypertension = false
            }
            else if (healthMetrics.bloodPressureSystolic in 130..139 || healthMetrics.bloodPressureDiastolic in 80..89){
                healthStatus.grade1Hypertension = false
                healthStatus.grade2Hypertension = true
                healthStatus.grade3Hypertension = false
                healthStatus.grade4Hypertension = false
            }
            else if (healthMetrics.bloodPressureSystolic in 120..129 &&  healthMetrics.bloodPressureDiastolic < 80){
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
            if(healthMetrics.bloodPressureSystolic < 90 || healthMetrics.bloodPressureDiastolic < 60){
                healthStatus.highBP = false
                healthStatus.lowBP = true
                healthStatus.isolatedSystolic = false
                healthStatus.isolatedDiastolic = false
            }
            else{
                healthStatus.highBP = false
                healthStatus.lowBP = false
                healthStatus.isolatedSystolic = false
                healthStatus.isolatedDiastolic = false
            }
        }
        if (graphData.systolic.isNotEmpty()){
            if (healthStatus.highBP){
                if (healthStatus.grade1Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "Slightly High BP"
                    bpDiagnosis.setTextColor(grade1HypertensionColor)
                    if (healthNotifications.bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        healthNotifications.bpNotificationOn = true
                    }
                }
                else if(healthStatus.grade2Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "High BP"
                    bpDiagnosis.setTextColor(grade2HypertensionColor)
                    if (healthNotifications.bpNotificationOn) {
                        message += "Your blood pressure is high, take it easy and monitor your condition..\n"
                        healthNotifications.bpNotificationOn = true
                    }
                }
                else if(healthStatus.grade3Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "Very High BP"
                    bpDiagnosis.setTextColor(grade3HypertensionColor)
                    if (healthNotifications.bpNotificationOn) {
                        message += "Your Blood Pressure is rising pretty high, we recommend you go see a clinic for medical advice.\n"
                        healthNotifications.bpNotificationOn = true
                    }
                }
                else if(healthStatus.grade4Hypertension){
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "GO TO A HOSPITAL"
                    bpDiagnosis.setTextColor(grade4HypertensionColor)
                    if (healthNotifications.bpNotificationOn) {
                        message += "Your Blood Pressure is too high, Please Head To A Hospital Now. \n"
                        healthNotifications.bpNotificationOn = true
                    }
                }
                else{
                    bpAlertIV.visibility = View.VISIBLE
                    surveyBtn.visibility = View.VISIBLE
                    bpDiagnosis.text = "High BP (Unspecified)"
                    bpDiagnosis.setTextColor(highColor)
                    if (healthNotifications.bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        healthNotifications.bpNotificationOn = false
                    }
                }
            }
            else if (healthStatus.lowBP){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Low BP"
                bpDiagnosis.setTextColor(lowColor)
                if (healthNotifications.bpNotificationOn){
                    message += "There's a decrease in blood pressure, are you feeling ok?\n Take a survey for some recommendations."
                    healthNotifications.bpNotificationOn = false
                }
            }
            else if (healthStatus.isolatedSystolic){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Isolated Systolic"
                bpDiagnosis.setTextColor(halfColor)
                if (healthNotifications.bpNotificationOn){
                    message += "Your Blood Pressure is a little strange, please seek medical advice. \n"
                    healthNotifications.bpNotificationOn = false
                }
            }
            else if (healthStatus.isolatedDiastolic){
                bpAlertIV.visibility = View.VISIBLE
                surveyBtn.visibility = View.VISIBLE
                bpDiagnosis.text = "Isolated Diastolic"
                bpDiagnosis.setTextColor(halfColor)
                if (healthNotifications.bpNotificationOn){
                    message += "Your Blood Pressure is a little strange, please seek medical advice. \n"
                    healthNotifications.bpNotificationOn = false
                }
            }
            else{
                bpDiagnosis.text = "Normal BP"
                bpDiagnosis.setTextColor(normalColor)
                healthNotifications.bpNotificationOn = false
            }
        }
        var bmiValue = ImOKApp.calculateBMI(healthMetrics.weight, healthMetrics.heightMeter, healthMetrics)
        if (bmiValue >= "27.5".toFloat()) {
            healthStatus.highRiskBmi = true
            healthStatus.moderateRiskBmi = false
            healthStatus.lowRiskBmi = false
            healthStatus.uWeight = false
        }
        else if (bmiValue.toInt() >= 23 && bmiValue.toInt() < 27.5) {
            healthStatus.highRiskBmi = false
            healthStatus.moderateRiskBmi = true
            healthStatus.lowRiskBmi = false
            healthStatus.uWeight = false
        }
        else if (bmiValue.toInt() >= 18.5 && bmiValue.toInt() < 23) {
            healthStatus.highRiskBmi = false
            healthStatus.moderateRiskBmi = false
            healthStatus.lowRiskBmi = true
            healthStatus.uWeight = false
        }
        else{
            healthStatus.highRiskBmi = false
            healthStatus.moderateRiskBmi = false
            healthStatus.lowRiskBmi = false
            healthStatus.uWeight = true
        }
        if(graphData.weightArray.isNotEmpty()){
            if (healthStatus.highRiskBmi) {
                bmiAlertIV.visibility = View.VISIBLE
                bmiWarningTV.text = "High Risk BMI"
                bmiWarningTV.setTextColor(highRiskBmiColor)
                if (healthNotifications.weightNotificationOn) {
                    message += "You're BMI is becoming high risk, go see a doctor for medical advice. \n Take our survey as well to get some recommendations!"
                    healthNotifications.weightNotificationOn = true
                }
            } else if (healthStatus.moderateRiskBmi) {
                bmiAlertIV.visibility = View.VISIBLE
                bmiWarningTV.text = "Moderate Risk BMI"
                bmiWarningTV.setTextColor(moderateRiskBmiColor)
                if (healthNotifications.weightNotificationOn) {
                    message += "You're BMI is getting a little high, which resulted in an increase in BMI. What changed? \n Take our survey to get some recommendations"
                    healthNotifications.weightNotificationOn = false
                }
            }
            else if (healthStatus.lowRiskBmi) {
                bmiWarningTV.text = "Normal BMI"
                bmiWarningTV.setTextColor(normalColor)
                healthNotifications.weightNotificationOn = false
            }
            else if (healthStatus.uWeight) {
                bmiAlertIV.visibility = View.VISIBLE
                bmiWarningTV.text = "Underweight"
                bmiWarningTV.setTextColor(lowColor)
                if (healthNotifications.weightNotificationOn) {
                    message += "Your BMI is going a little low"
                    healthNotifications.weightNotificationOn = false
                }
            }
            else {
                bmiWarningTV.text = "Unspecified"
                bmiWarningTV.setTextColor(normalColor)
                healthNotifications.weightNotificationOn = true
            }
            if(graphData.weightArray.size >= 5){
                if (healthMetrics.weight >= healthMetrics.weightAverage + healthMetrics.weightThreshold) {
                    // The weight is far above the person's norm
                    weightAlertIV.visibility = View.VISIBLE
                    weightKgNormTV.visibility = View.VISIBLE
                    weightWarningTV.text = "Weight Above Norm"
                    weightWarningTV.setTextColor(highColor)
                    if (healthNotifications.weightNotificationOn) {
                        message += "Your weight is significantly higher than your norm. If this is not normal, go see a doctor. \nTake our survey as well to get some recommendations!"
                        healthNotifications.weightNotificationOn = true
                    }
                } else if (healthMetrics.weight <= healthMetrics.weightAverage + healthMetrics.weightThreshold) {
                    // The weight is far below the person's norm
                    weightAlertIV.visibility = View.VISIBLE
                    weightKgNormTV.visibility = View.VISIBLE
                    weightWarningTV.text = "Weight Below Norm"
                    weightWarningTV.setTextColor(lowColor)
                    if (healthNotifications.weightNotificationOn) {
                        message += "Your weight is significantly lower than your norm. If this is not normal, go see a doctor. \nTake our survey to get some recommendations!"
                        healthNotifications.weightNotificationOn = true
                    }
                } else {
                    // The weight is within the person's norm
                    weightWarningTV.text = "Weight is Normal"
                    healthNotifications.weightNotificationOn = false
                }
            }
        }
        if(healthNotifications.takeCareNotif){
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
