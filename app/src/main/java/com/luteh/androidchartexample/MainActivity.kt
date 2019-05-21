package com.luteh.androidchartexample

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLineChart()
        addEntry(null)
    }

    private fun initLineChart() {
        lineChart.apply {
            setDrawGridBackground(false)
            description.isEnabled = false
            setNoDataText("No chart data available. Use the menu to add entries and data sets!")
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.isEnabled = false
        }



        lineChart.invalidate()
    }

    fun addEntry(view: View?) {
        var data: LineData? = lineChart.data

        if (data == null) {
            data = LineData()
            lineChart.data = data
        }

        var set: ILineDataSet? = data.getDataSetByIndex(0)
        if (set == null) {
            set = createSet()
            data.addDataSet(set)
        }

        // Choose a random data set
        for (i in 0..5) {
            val randomDataSetIndex = (Math.random() * data.dataSetCount).toInt()
            val randomSet = data.getDataSetByIndex(randomDataSetIndex)
            val value = (Math.random() * 50).toFloat() + 50f * (randomDataSetIndex + 1)

            data.addEntry(Entry(randomSet.entryCount.toFloat(), value), randomDataSetIndex)
        }
        data.notifyDataChanged()

        // let the chart know its data has changed
        lineChart.notifyDataSetChanged()

        lineChart.setVisibleXRangeMaximum(6f)
        lineChart.moveViewTo((data.entryCount - 7).toFloat(), 50f, YAxis.AxisDependency.LEFT)

    }

    private fun createSet(): LineDataSet {

        val set = LineDataSet(null, "DataSet 1")
        set.lineWidth = 2.5f
        set.circleRadius = 4.5f
        set.color = Color.rgb(240, 99, 99)
        set.setCircleColor(Color.rgb(240, 99, 99))
        set.highLightColor = Color.rgb(190, 190, 190)
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.valueTextSize = 10f

        return set
    }

    private fun generateLineData(): LineData {
        val tf = Typeface.createFromAsset(this.assets, "OpenSans-Light.ttf")

        val sets = ArrayList<ILineDataSet>()
        val ds1 = LineDataSet(FileUtils.loadEntriesFromAssets(this.assets, "sine.txt"), "Sine function")
        val ds2 = LineDataSet(FileUtils.loadEntriesFromAssets(this.assets, "cosine.txt"), "Cosine function")

        ds1.lineWidth = 2f
        ds2.lineWidth = 2f

        ds1.setDrawCircles(false)
        ds2.setDrawCircles(false)

        ds1.color = ColorTemplate.VORDIPLOM_COLORS[0]
        ds2.color = ColorTemplate.VORDIPLOM_COLORS[1]

        // load DataSets from files in assets folder
        sets.add(ds1)
        sets.add(ds2)

        val d = LineData(sets)
        d.setValueTypeface(tf)
        return d
    }
}
