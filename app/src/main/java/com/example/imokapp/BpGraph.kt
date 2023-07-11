package com.example.imokapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.imokapp.ImOKApp.Companion.diastolic
import com.example.imokapp.ImOKApp.Companion.generateTimeLabels
import com.example.imokapp.ImOKApp.Companion.systolic
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

        setUpBpChart()

        setDataToBpChart()
    }

    private fun setUpBpChart() {
        with(BpChart) {

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

    private fun setDataToBpChart() {
        val systolicDataset = LineDataSet(systolic, "Systolic")
        systolicDataset.lineWidth = 3f
        systolicDataset.valueTextSize = 15f
        systolicDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        systolicDataset.color = ContextCompat.getColor(this, R.color.red)
        systolicDataset.valueTextColor = ContextCompat.getColor(this, R.color.red)
        systolicDataset.enableDashedLine(20F, 10F, 0F)
        systolicDataset.disableDashedLine()

        val diastolicDataset = LineDataSet(diastolic, "Diastolic")
        diastolicDataset.lineWidth = 3f
        diastolicDataset.valueTextSize = 15f
        diastolicDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        diastolicDataset.color = ContextCompat.getColor(this, R.color.blue)
        diastolicDataset.valueTextColor = ContextCompat.getColor(this, R.color.blue)
        diastolicDataset.enableDashedLine(20F, 10F, 0F)
        diastolicDataset.disableDashedLine()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(systolicDataset)
        dataSet.add(diastolicDataset)

        val lineData = LineData(dataSet)
        BpChart.data = lineData

        BpChart.invalidate()
    }
}