package com.example.imokapp

import android.content.Intent
import android.graphics.Color.green
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.imokapp.ImOKApp.Companion.BMI
import com.example.imokapp.ImOKApp.Companion.addWeightData
import com.example.imokapp.ImOKApp.Companion.calculateBMI
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.heightMeter
import com.example.imokapp.ImOKApp.Companion.uWeight
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.example.imokapp.ImOKApp.Companion.weightValues
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
import kotlinx.android.synthetic.main.activity_weight_graph.*

class WeightGraph : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_weight_graph)

    var warningView = findViewById<TextView>(R.id.diagnosisTV)
    val lowColor = ContextCompat.getColor(this, R.color.blue)
    val normalColor = ContextCompat.getColor(this, R.color.green)
    val highColor = ContextCompat.getColor(this, R.color.red)
    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setTitle("Alert")
    var message = ""
    if(weightValues.isNotEmpty()){
        var weightSize = weightValues.size
        lastReadingWeightTV.text = weightValues[weightSize-1].toString()
        if (uWeight){
            warningView.text = "Underweight"
            warningView.setTextColor(lowColor)
            message += "Your weight has dropped, how are you?"
        }
        else{
            warningView.text = "Normal"
            warningView.setTextColor(normalColor)
        }
        alertDialogBuilder.setMessage(message)
        if (message != "") {
            alertDialogBuilder.show()
        }
        setUpWeightChart()
        setDataToWeightChart()
    }
    var submitBtn = findViewById<Button>(R.id.weightSubmitBtn)
    submitBtn.setOnClickListener {
        var weightET = findViewById<EditText>(R.id.weightEntryET)
        var weightText = weightET.text.toString()
        weightET.setText("")
        weightET.clearFocus()

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(submitBtn.windowToken, 0)

        if (weightText.isEmpty()) {
            weightET.error = "Weight Value is Empty"
        }
        else {
            weight = weightText.toFloat()
            uWeight = calculateBMI(weight, heightMeter) < 18.5
            addWeightData(weight)
            weightValues.add(weight)
            recreate()
        }
    }
    var back = findViewById<Button>(R.id.backBtn)
    back.setOnClickListener{
        var backToDashboard = Intent(this,HealthMetrics::class.java)
        startActivity(backToDashboard)
    }
    var profile = findViewById<ImageView>(R.id.profilePictureIV)
    profile.setOnClickListener{
        var toProfile = Intent(this, PatientProfile::class.java)
        startActivity(toProfile)
    }
}

private fun setUpWeightChart() {
    with(WeightChart) {

        axisRight.isEnabled = false
        animateX(1200, Easing.EaseInSine)

        description.isEnabled = false

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.granularity = 1F
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        axisLeft.setDrawGridLines(false)
        extraRightOffset = 30f

        legend.isEnabled = true
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
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

private fun setDataToWeightChart() {
    val weightDataset = LineDataSet(weightArray, "Weight")
    weightDataset.lineWidth = 3f
    weightDataset.valueTextSize = 15f
    weightDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
    weightDataset.color = ContextCompat.getColor(this, R.color.green)
    weightDataset.valueTextColor = ContextCompat.getColor(this, R.color.green)
    weightDataset.enableDashedLine(20F, 10F, 0F)
    weightDataset.disableDashedLine()

    val dataSet = ArrayList<ILineDataSet>()
    dataSet.add(weightDataset)

    val lineData = LineData(dataSet)
    WeightChart.data = lineData

    WeightChart.invalidate()
    }
}