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
import com.example.imokapp.ImOKApp.Companion.BMI
import com.example.imokapp.ImOKApp.Companion.addBpData
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.bpNotificationOn
import com.example.imokapp.ImOKApp.Companion.diastolic
import com.example.imokapp.ImOKApp.Companion.diastolicValues
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.grade1Hypertension
import com.example.imokapp.ImOKApp.Companion.grade2Hypertension
import com.example.imokapp.ImOKApp.Companion.grade3Hypertension
import com.example.imokapp.ImOKApp.Companion.grade4Hypertension
import com.example.imokapp.ImOKApp.Companion.heartRate
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.isolatedDiastolic
import com.example.imokapp.ImOKApp.Companion.isolatedSystolic
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.systolic
import com.example.imokapp.ImOKApp.Companion.systolicValues
import com.example.imokapp.ImOKApp.Companion.timeList
import com.example.imokapp.ImOKApp.Companion.updateDataJson
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.example.imokapp.ImOKApp.Companion.weightAverage
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

        if(systolic.isNotEmpty()){
            var systolicSize = systolicValues.size
            var diastolicSize = diastolicValues.size
            lastReadingSystolicTV.text = systolicValues[systolicSize - 1].toString()
            lastReadingDiastolicTV.text = diastolicValues[diastolicSize - 1].toString()
            if (highBP){
                if (grade1Hypertension){
                    warningView.text = "Slightly High BP"
                    warningView.setTextColor(grade1HypertensionColor)
                    warningSurveyView.text = "Click here to tell us how you're feeling!"
                    warningSurveyView.setTextColor(halfColor)
                    warningLL.isVisible = true
                    if (bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        bpNotificationOn = false
                    }
                }
                else if(grade2Hypertension){
                    warningView.text = "High BP"
                    warningView.setTextColor(grade2HypertensionColor)
                    warningSurveyView.text = "Click here to tell us how you're feeling!"
                    warningSurveyView.setTextColor(halfColor)
                    warningLL.isVisible = true
                    if (bpNotificationOn) {
                        message += "Your blood pressure is high. \n Take it easy and monitor your condition.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        bpNotificationOn = false
                    }
                }
                else if(grade3Hypertension){
                    warningView.text = "Very High BP"
                    warningView.setTextColor(grade3HypertensionColor)
                    warningSurveyView.text = "Please click here to tell us how you're feeling."
                    warningSurveyView.setTextColor(highColor)
                    warningLL.isVisible = true
                    if (bpNotificationOn) {
                        message += "Your Blood Pressure is rising pretty high. \n We recommend you go see a clinic for medical advice.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        bpNotificationOn = true
                    }
                }
                else if(grade4Hypertension){
                    warningView.text = "GO TO A HOSPITAL"
                    warningView.setTextColor(grade4HypertensionColor)
                    warningSurveyView.text = "Fill in this survey for recommendations"
                    warningSurveyView.setTextColor(highColor)
                    warningLL.isVisible = true
                    if (bpNotificationOn) {
                        message += "Your Blood Pressure is too high. \n Please Seek Immediate Medical Attention. \n Call 995 or Head Straight To A Hospital. \n"
                        surveyClassName = "com.example.imokapp.Survey"
                        bpNotificationOn = true
                    }
                }
                else{
                    warningView.text = "High BP (Unspecified)"
                    warningView.setTextColor(highColor)
                    warningSurveyView.text = "Click here to tell us how you're feeling!"
                    warningLL.isVisible = true
                    if (bpNotificationOn) {
                        message += "There's a slight increase in blood pressure, take it easy.\n"
                        surveyClassName = "com.example.imokapp.Survey"
                        bpNotificationOn = false
                    }
                }
            }
            else if (lowBP){
                warningView.text = "Low BP"
                warningView.setTextColor(lowColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningSurveyView.setTextColor(highColor)
                warningLL.isVisible = true
                if (bpNotificationOn){
                    message += "There's a decrease in blood pressure, are you feeling ok? \n"
                    surveyClassName = "com.example.imokapp.Survey"
                    bpNotificationOn = false
                }
            }
            else if (isolatedSystolic){
                warningView.text = "Go see a doctor"
                warningView.setTextColor(halfColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningSurveyView.setTextColor(halfColor)
                warningLL.isVisible = true
                if (bpNotificationOn){
                    message += "Your Blood Pressure is a little strange. \n Please seek medical advice at your local clinic. \n"
                    surveyClassName = "com.example.imokapp.Survey"
                    bpNotificationOn = false
                }
            }
            else if (isolatedDiastolic){
                warningView.text = "Isolated Diastolic"
                warningView.setTextColor(halfColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (bpNotificationOn){
                    message += "Your Blood Pressure is a little strange, please seek medical advice. \n"
                    surveyClassName = "com.example.imokapp.Survey"
                    bpNotificationOn = false
                }
            }
            else{
                warningView.text = "Normal BP"
                warningView.setTextColor(normalColor)
                warningLL.isInvisible = true
                bpNotificationOn = false
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
                grade1Hypertension = false
                grade2Hypertension = false
                grade3Hypertension = false
                grade4Hypertension = false
                if (systolicText.toInt() >= 130 && diastolicText.toInt() < 80){
                    highBP = false
                    lowBP = false
                    isolatedSystolic = true
                    isolatedDiastolic = false
                }
                else if(systolicText.toInt() < 130 && diastolicText.toInt() >= 80){
                    highBP = false
                    lowBP = false
                    isolatedSystolic = false
                    isolatedDiastolic = true
                }
                else if (systolicText.toInt() >= 120 || diastolicText.toInt() >= 80){
                    highBP = true
                    if ((systolicText.toInt() >= 180 || diastolicText.toInt() >= 120) || (systolicText.toInt() >= 180 && diastolicText.toInt() >= 120)){
                        grade1Hypertension = false
                        grade2Hypertension = false
                        grade3Hypertension = false
                        grade4Hypertension = true

                    }
                    else if (systolicText.toInt() in 140..179 || diastolicText.toInt() in 90..119){
                        grade1Hypertension = false
                        grade2Hypertension = false
                        grade3Hypertension = true
                        grade4Hypertension = false
                    }
                    else if (systolicText.toInt() in 130..139 || diastolicText.toInt() in 80..89){
                        grade1Hypertension = false
                        grade2Hypertension = true
                        grade3Hypertension = false
                        grade4Hypertension = false
                    }
                    else if (systolicText.toInt() in 120..129 &&  diastolicText.toInt() < 80){
                        grade1Hypertension = true
                        grade2Hypertension = false
                        grade3Hypertension = false
                        grade4Hypertension = false
                    }
                    lowBP = false
                    isolatedSystolic = false
                    isolatedDiastolic = false
                }
                else{
                    if(systolicText.toInt() < 90 || diastolicText.toInt() < 60){
                        highBP = false
                        lowBP = true
                        isolatedSystolic = false
                        isolatedDiastolic = false
                    }
                    else{
                        highBP = false
                        lowBP = false
                        isolatedSystolic = false
                        isolatedDiastolic = false
                    }
                }
                bloodPressureSystolic = systolicText.toInt()
                bloodPressureDiastolic = diastolicText.toInt()
                addBpData(bloodPressureSystolic, bloodPressureDiastolic)
                systolicValues.add(bloodPressureSystolic)
                diastolicValues.add(bloodPressureDiastolic)
                bpNotificationOn = true
                timeList.add(generateTimeLabels())
                updateDataJson(
                    systolic = systolic,
                    diastolic = diastolic,
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

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index in timeList.indices) {
                timeList[index]
            } else {
                ""
            }
        }
    }

    private fun setDataToBpChart() {
        val systolicDataset = LineDataSet(systolic, "Systolic")
        systolicDataset.lineWidth = 3f
        systolicDataset.valueTextSize = 12f
        systolicDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        systolicDataset.color = ContextCompat.getColor(this, R.color.red)
        systolicDataset.valueTextColor = ContextCompat.getColor(this, R.color.red)
//        systolicDataset.enableDashedLine(20F, 10F, 0F)
//        systolicDataset.disableDashedLine()

        val diastolicDataset = LineDataSet(diastolic, "Diastolic")
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