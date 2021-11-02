package com.example.codechefeventsapp.fragments.styling;

import android.content.Context;
import android.graphics.Color;

import com.example.codechefeventsapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;

public class LineChartStyling {

    LineChart lineChart;
    Context context;

    public LineChartStyling(LineChart lineChart, Context context) {
        this.lineChart = lineChart;
        this.context = context;
    }

    public void styleIt(){
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getXAxis().setDrawLabels(false);

        lineChart.animateX(1000);

        lineChart.getAxisRight().setDrawGridLines(false);


        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);

        lineChart.setElevation(10f);

        lineChart.setScaleEnabled(false);

        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setPinchZoom(false);
    }


}
