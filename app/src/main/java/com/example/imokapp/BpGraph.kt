package com.example.imokapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.imokapp.ImOKApp.Companion.addBpData
import com.example.imokapp.ImOKApp.Companion.bloodPressureDiastolic
import com.example.imokapp.ImOKApp.Companion.bloodPressureSystolic
import com.example.imokapp.ImOKApp.Companion.diastolic
import com.example.imokapp.ImOKApp.Companion.diastolicValues
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.highBP
import com.example.imokapp.ImOKApp.Companion.lowBP
import com.example.imokapp.ImOKApp.Companion.systolic
import com.example.imokapp.ImOKApp.Companion.systolicValues
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
import kotlinx.android.synthetic.main.activity_bp_graph.backBtn
import kotlinx.android.synthetic.main.activity_bp_graph.profilePictureIV
import kotlinx.android.synthetic.main.activity_health_metrics.*

class BpGraph : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bp_graph)
        var warningView = findViewById<TextView>(R.id.diagnosisTV)
        val lowColor = ContextCompat.getColor(this, R.color.blue)
        val normalColor = ContextCompat.getColor(this, R.color.green)
        val highColor = ContextCompat.getColor(this, R.color.red)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        var message = ""
        if(systolic.isNotEmpty()){
            var systolicSize = systolicValues.size
            var diastolicSize = diastolicValues.size
            lastReadingSystolicTV.text = systolicValues[systolicSize - 1].toString()
            lastReadingDiastolicTV.text = diastolicValues[diastolicSize - 1].toString()
            if (highBP){
                warningView.text = "High BP"
                warningView.setTextColor(highColor)
                message += "There's a slight increase in blood pressure, take it easy.\n"
            }
            else if (lowBP){
                warningView.text = "Low BP"
                warningView.setTextColor(lowColor)
                message += "There's a slight decrease in blood pressure, are you feeling ok?\n"
            }
            else{
                warningView.text = "Normal BP"
                warningView.setTextColor(normalColor)
            }
            alertDialogBuilder.setMessage(message)
            if (message != "") {
                alertDialogBuilder.show()
            }
            setUpBpChart()
            setDataToBpChart()
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

        private var time = generateTimeLabels()

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index in time.indices) {
                time[index]
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