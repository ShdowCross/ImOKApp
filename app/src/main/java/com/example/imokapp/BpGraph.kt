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
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.bpNotificationOn
import com.example.imokapp.ImOKApp.Companion.diastolic
import com.example.imokapp.ImOKApp.Companion.diastolicValues
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.systolic
import com.example.imokapp.ImOKApp.Companion.systolicValues
import com.example.imokapp.ImOKApp.Companion.timeList
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_bp_graph.*

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
                warningView.text = "High BP"
                warningView.setTextColor(highColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (bpNotificationOn) {
                    message += "There's a slight increase in blood pressure, take it easy.\n"
                    surveyClassName = "com.example.imokapp.Survey"
                    bpNotificationOn = false
                }
            }
            else if (lowBP){
                warningView.text = "Low BP"
                warningView.setTextColor(lowColor)
                warningSurveyView.text = "Click here to tell us how you're feeling!"
                warningLL.isVisible = true
                if (bpNotificationOn){
                    message += "There's a slight decrease in blood pressure, are you feeling ok?\n"
                    surveyClassName = "com.example.imokapp.Survey"
                    bpNotificationOn = false
                }
            }
            else{
                warningView.text = "Normal BP"
                warningView.setTextColor(normalColor)
                warningLL.isInvisible = true
            }
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
                if (systolicText.toInt() > 130 || diastolicText.toInt() > 85){
                    highBP = true
                    lowBP = false
                }
                else if(systolicText.toInt() < 90 || diastolicText.toInt() < 60){
                    highBP = false
                    lowBP = true
                }
                else{
                    highBP = false
                    lowBP = false
                    }
                bloodPressureSystolic = systolicText.toInt()
                bloodPressureDiastolic = diastolicText.toInt()
                addBpData(bloodPressureSystolic, bloodPressureDiastolic)
                systolicValues.add(bloodPressureSystolic)
                diastolicValues.add(bloodPressureDiastolic)
                bpNotificationOn = true
                timeList.add(generateTimeLabels())
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