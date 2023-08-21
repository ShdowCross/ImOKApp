package com.example.imokapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.imokapp.ImOKApp.Companion.addBpData
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.graphData
import com.example.imokapp.ImOKApp.Companion.healthMetrics
import com.example.imokapp.ImOKApp.Companion.healthNotification
import com.example.imokapp.ImOKApp.Companion.healthStatus
import com.example.imokapp.ImOKApp.Companion.personInfo
import com.example.imokapp.ImOKApp.Companion.pullInfo
import com.example.imokapp.ImOKApp.Companion.writeGraphDataJson
import com.example.imokapp.ImOKApp.Companion.writeHealthMetricsJson
import com.example.imokapp.ImOKApp.Companion.writeHealthNotificationsJson
import com.example.imokapp.ImOKApp.Companion.writeHealthStatusJson
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_bp_graph.*
import java.io.File
import java.io.IOException

class BpGraph : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bp_graph)
        val infoData = pullInfo(filesDir)
        personInfo = infoData?.personInfo ?: ImOKApp.Companion.PersonInfo()
        healthMetrics = infoData?.healthMetrics ?: ImOKApp.Companion.HealthMetrics()
        healthStatus = infoData?.healthStatus ?: ImOKApp.Companion.HealthStatus()
        healthNotification = infoData?.healthNotifications ?: ImOKApp.Companion.HealthNotifications()
        graphData = infoData?.graphData ?: ImOKApp.Companion.GraphData()


        var warningLL = findViewById<LinearLayout>(R.id.diagnosisLL)
        var warningView = findViewById<TextView>(R.id.diagnosisTV)
        var warningSurveyView = findViewById<TextView>(R.id.diagnosisSurveyTV)

        val lowColor = ContextCompat.getColor(this, R.color.blue)
        val normalColor = ContextCompat.getColor(this, R.color.green)
        val highColor = ContextCompat.getColor(this, R.color.red)
        val halfColor = ContextCompat.getColor(this, R.color.yellow_orange)
        val grade1HypertensionColor = ContextCompat.getColor(this, R.color.grade1Hypertension)
        val grade2HypertensionColor = ContextCompat.getColor(this, R.color.grade2Hypertension)
        val grade3HypertensionColor = ContextCompat.getColor(this, R.color.grade3Hypertension)
        val grade4HypertensionColor = ContextCompat.getColor(this, R.color.grade4Hypertension)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        var surveyClassName = ""


        if(graphData.systolic.isNotEmpty()){
            var systolicSize = graphData.systolic.size
            var diastolicSize = graphData.systolic.size
            lastReadingSystolicTV.text = (graphData.systolic).last().y.toString()
            lastReadingDiastolicTV.text = (graphData.diastolic).last().y.toString()
            if (healthStatus.highBP){
                if (healthStatus.grade1Hypertension){
                    warningView.text = "Slightly High BP"
                    warningView.setTextColor(grade1HypertensionColor)
                    warningSurveyView.text = "Click here to tell us how you're feeling!"
                    warningSurveyView.setTextColor(halfColor)
                    warningLL.isVisible = true
                    if (healthNotification.bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        healthNotification.bpNotificationOn = false
                    }
                }
                else if(healthStatus.grade2Hypertension){
                    warningView.text = "High BP"
                    warningView.setTextColor(grade2HypertensionColor)
                    warningSurveyView.text = "Click here to tell us how you're feeling!"
                    warningSurveyView.setTextColor(halfColor)
                    warningLL.isVisible = true
                    if (healthNotification.bpNotificationOn) {
                        message += "Your blood pressure is high. \n Take it easy and monitor your condition.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        healthNotification.bpNotificationOn = false
                    }
                }
                else if(healthStatus.grade3Hypertension){
                    warningView.text = "Very High BP"
                    warningView.setTextColor(grade3HypertensionColor)
                    warningSurveyView.text = "Please click here to tell us how you're feeling."
                    warningSurveyView.setTextColor(highColor)
                    warningLL.isVisible = true
                    if (healthNotification.bpNotificationOn) {
                        message += "Your Blood Pressure is rising pretty high. \n We recommend you go see a clinic for medical advice.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        healthNotification.bpNotificationOn = true
                    }
                }
                else if(healthStatus.grade4Hypertension){
                    warningView.text = "GO TO A HOSPITAL"
                    warningView.setTextColor(grade4HypertensionColor)
                    warningSurveyView.text = "Fill in this survey for recommendations"
                    warningSurveyView.setTextColor(highColor)
                    warningLL.isVisible = true
                    if (healthNotification.bpNotificationOn) {
                        message += "Your Blood Pressure is too high. \n Please Seek Immediate Medical Attention. \n Call 995 or Head Straight To A Hospital. \n"
                        surveyClassName = "com.example.imokapp.Survey"
                        healthNotification.bpNotificationOn = true
                    }
                }
                else{
                    warningView.text = "High BP (Unspecified)"
                    warningView.setTextColor(highColor)
                    warningSurveyView.text = "Click here to tell us how you're feeling!"
                    warningLL.isVisible = true
                    if (healthNotification.bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        healthNotification.bpNotificationOn = false
                    }
                }
            }
            else if (healthStatus.lowBP){
                warningView.text = "Low BP"
                warningView.setTextColor(lowColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningSurveyView.setTextColor(highColor)
                warningLL.isVisible = true
                if (healthNotification.bpNotificationOn){
                    message += "There's a decrease in blood pressure, are you feeling ok? \n"
                    surveyClassName = "com.example.imokapp.Survey"
                    healthNotification.bpNotificationOn = false
                }
            }
            else if (healthStatus.isolatedSystolic){
                warningView.text = "Go see a doctor"
                warningView.setTextColor(halfColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningSurveyView.setTextColor(halfColor)
                warningLL.isVisible = true
                if (healthNotification.bpNotificationOn){
                    message += "Your Blood Pressure is a little strange. \n Please seek medical advice at your local clinic. \n"
                    surveyClassName = "com.example.imokapp.Survey"
                    healthNotification.bpNotificationOn = false
                }
            }
            else if (healthStatus.isolatedDiastolic){
                warningView.text = "Isolated Diastolic"
                warningView.setTextColor(halfColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (healthNotification.bpNotificationOn){
                    message += "Your Blood Pressure is a little strange, please seek medical advice. \n"
                    surveyClassName = "com.example.imokapp.Survey"
                    healthNotification.bpNotificationOn = false
                }
            }
            else{
                warningView.text = "Normal BP"
                warningView.setTextColor(normalColor)
                warningLL.isInvisible = true
                healthNotification.bpNotificationOn = false
            }
            message += ""
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton("Ok for now"){ dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Take Survey"){_, _ ->
                val surveyClass = Class.forName(surveyClassName)
                val toSurvey = Intent(this, surveyClass)
                startActivity(toSurvey)
            }
            if (message != "") {
                alertDialogBuilder.show()
            }
            setUpBpChart()
            setDataToBpChart()
        }

        warningLL.setOnClickListener{
            val surveyClass = Class.forName(surveyClassName)
            val toSurvey = Intent(this, surveyClass)
            startActivity(toSurvey)
        }

        var submitBtn = findViewById<Button>(R.id.submitBtn)
        submitBtn.setOnClickListener {
            var systolicET = findViewById<EditText>(R.id.EntrySystolicET)
            var diastolicET = findViewById<EditText>(R.id.EntryDiastolicET)
            var systolicText = systolicET.text.toString()
            var diastolicText = diastolicET.text.toString()
            systolicET.setText("")
            diastolicET.setText("")
            systolicET.clearFocus()
            diastolicET.clearFocus()

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(submitBtn.windowToken, 0)

            if (systolicText.isEmpty() || diastolicText.isEmpty()) {
                if (systolicText.isEmpty()) {
                    systolicET.error = "Systolic Value is Empty"
                }
                if (diastolicText.isEmpty()) {
                    diastolicET.error = "Diastolic Value is Empty"
                }
            }
            else {
                healthStatus.grade1Hypertension = false
                healthStatus.grade2Hypertension = false
                healthStatus.grade3Hypertension = false
                healthStatus.grade4Hypertension = false
                if (systolicText.toInt() >= 130 && diastolicText.toInt() < 80){
                    healthStatus.highBP = false
                    healthStatus.lowBP = false
                    healthStatus.isolatedSystolic = true
                    healthStatus.isolatedDiastolic = false
                }
                else if(systolicText.toInt() < 130 && diastolicText.toInt() >= 80){
                    healthStatus.highBP = false
                    healthStatus.lowBP = false
                    healthStatus.isolatedSystolic = false
                    healthStatus.isolatedDiastolic = true
                }
                else if (systolicText.toInt() >= 120 || diastolicText.toInt() >= 80){
                    healthStatus.highBP = true
                    if ((systolicText.toInt() >= 180 || diastolicText.toInt() >= 120) || (systolicText.toInt() >= 180 && diastolicText.toInt() >= 120)){
                        healthStatus.grade1Hypertension = false
                        healthStatus.grade2Hypertension = false
                        healthStatus.grade3Hypertension = false
                        healthStatus.grade4Hypertension = true

                    }
                    else if (systolicText.toInt() in 140..179 || diastolicText.toInt() in 90..119){
                        healthStatus.grade1Hypertension = false
                        healthStatus.grade2Hypertension = false
                        healthStatus.grade3Hypertension = true
                        healthStatus.grade4Hypertension = false
                    }
                    else if (systolicText.toInt() in 130..139 || diastolicText.toInt() in 80..89){
                        healthStatus.grade1Hypertension = false
                        healthStatus.grade2Hypertension = true
                        healthStatus.grade3Hypertension = false
                        healthStatus.grade4Hypertension = false
                    }
                    else if (systolicText.toInt() in 120..129 &&  diastolicText.toInt() < 80){
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
                    if(systolicText.toInt() < 90 || diastolicText.toInt() < 60){
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
                healthMetrics.bloodPressureSystolic = systolicText.toInt()
                healthMetrics.bloodPressureDiastolic = diastolicText.toInt()
                addBpData(healthMetrics.bloodPressureSystolic, healthMetrics.bloodPressureDiastolic, graphData)
                healthNotification.bpNotificationOn = true
                graphData.timeList.add(generateTimeLabels())
                writeHealthMetricsJson(
                    bloodPressureSystolic = healthMetrics.bloodPressureSystolic,
                    bloodPressureDiastolic = healthMetrics.bloodPressureDiastolic,
                    filesDir = filesDir
                )
                writeHealthStatusJson(
                    highBP = healthStatus.highBP,
                    grade1Hypertension = healthStatus.grade1Hypertension,
                    grade2Hypertension = healthStatus.grade2Hypertension,
                    grade3Hypertension = healthStatus.grade3Hypertension,
                    grade4Hypertension = healthStatus.grade4Hypertension,
                    lowBP = healthStatus.lowBP,
                    isolatedSystolic = healthStatus.isolatedSystolic,
                    isolatedDiastolic = healthStatus.isolatedDiastolic,
                    filesDir = filesDir
                )
                writeHealthNotificationsJson(
                    bpNotificationOn = healthNotification.bpNotificationOn,
                    filesDir = filesDir
                )
                writeGraphDataJson(
                    systolic = graphData.systolic,
                    diastolic = graphData.diastolic,
                    timeList = graphData.timeList,
                    filesDir = filesDir
                )
                recreate()
            }
        }
        backBtn.setOnClickListener{
            var backToDashboard = Intent(this,HealthMetrics::class.java)
            startActivity(backToDashboard)
        }
        profilePictureIV.setOnClickListener{
            var toProfile = Intent(this, PatientProfile::class.java)
            startActivity(toProfile)
        }
    }

    private fun setUpBpChart() {
        with(BpChart) {

            axisRight.isEnabled = true
            animateX(1200, Easing.EaseInSine)

            description.isEnabled = false

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = MyAxisFormatter()
            xAxis.granularity = 1F
            xAxis.setDrawGridLines(true)
            xAxis.setDrawAxisLine(true)
            axisLeft.setDrawGridLines(true)
            extraRightOffset = 30f

            legend.isEnabled = true
            legend.textSize = 15f
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.form = Legend.LegendForm.LINE

        }
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {
        private val graphData = ImOKApp.Companion.GraphData()
        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index in graphData.timeList.indices) {
                graphData.timeList[index]
            } else {
                ""
            }
        }
    }

    private fun setDataToBpChart() {
        val systolicDataset = LineDataSet(graphData.systolic, "Systolic")
        systolicDataset.lineWidth = 3f
        systolicDataset.valueTextSize = 12f
        systolicDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        systolicDataset.color = ContextCompat.getColor(this, R.color.red)
        systolicDataset.valueTextColor = ContextCompat.getColor(this, R.color.red)
//        systolicDataset.enableDashedLine(20F, 10F, 0F)
//        systolicDataset.disableDashedLine()

        val diastolicDataset = LineDataSet(graphData.diastolic, "Diastolic")
        diastolicDataset.lineWidth = 3f
        diastolicDataset.valueTextSize = 12f
        diastolicDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        diastolicDataset.color = ContextCompat.getColor(this, R.color.blue)
        diastolicDataset.valueTextColor = ContextCompat.getColor(this, R.color.blue)
//        diastolicDataset.enableDashedLine(20F, 10F, 0F)
//        diastolicDataset.disableDashedLine()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(systolicDataset)
        dataSet.add(diastolicDataset)

        val lineData = LineData(dataSet)
        BpChart.data = lineData

        BpChart.invalidate()
    }
}