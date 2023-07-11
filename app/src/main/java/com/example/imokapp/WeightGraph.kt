package com.example.imokapp

import android.graphics.Color.green
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.imokapp.ImOKApp.Companion.weight
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.weightArray
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_weight_graph.*

class WeightGraph : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_weight_graph)

    setUpWeightChart()

    setDataToWeightChart()
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