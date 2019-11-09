package com.demo.inspection.bl;

import android.content.Context;
import android.graphics.Color;

import com.demo.inspection.utils.ComDef;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: Inspection
 * @Package: com.demo.inspection.bl
 * @ClassName: DealLineChart
 * @Description: 折线图的一些公共方法
 * @Author: 王欢
 * @CreateDate: 2019/11/5 17:17
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/11/5 17:17
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DealLineChart {

    private  Context context;

    public DealLineChart(Context context) {
        this.context = context;
    }

    /**
     * 功能：动态创建一条曲线
     */
    public  void createLine(int [] dataList,String label, int color, LineChart lineChart) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.length; i++) {
            Entry entry = new Entry(i, dataList[i]);// Entry(x,y)
            entries.add(entry);
        }
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        // 初始化线条
        initLineDataSet(lineDataSet, color);

        if (lineChart.getLineData() == null) {
            LineData lineData = new LineData();
            lineData.addDataSet(lineDataSet);
            lineChart.setData(lineData);
        } else {
            lineChart.getLineData().addDataSet(lineDataSet);
        }

        lineChart.invalidate();
    }

    /**
     * 设置 可以显示X Y 轴自定义值的 MarkerView
     */
    public void setMarkerView(LineChart lineChart) {
        MyMarkerView mv = new MyMarkerView(context);
        mv.setChartView(lineChart);
        lineChart.setMarker(mv);
        lineChart.invalidate();
    }

    /**
     * 初始化折线图控件属性
     */
    public void initLineChart(LineChart lineChart) {
        lineChart.setNoDataText("暂无记录");
        lineChart.setNoDataTextColor(Color.RED);//没有数据时显示文字的颜色
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
        lineChart.setBackgroundColor(Color.WHITE);

        lineChart.animateX(2000);//x轴动画效果

        // 设置图例
        createLegend(lineChart.getLegend());

        //X轴显示效果：
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15f);//设置字体
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int position = (int) value;
                return ComDef.XSTRS[position];
            }
        });

        //自定义适配器，适配于Y轴
//        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int)value + "%";
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setTextSize(15f);//设置字体

//        LimitLine limitLine = new LimitLine(75, "Limit"); //得到限制线
//        limitLine.setLineWidth(2f); //宽度
//        limitLine.setTextSize(15f);
//        limitLine.setTextColor(Color.RED);  //颜色
//        limitLine.setLineColor(Color.BLUE);
//        leftAxis.addLimitLine(limitLine); //Y轴添加限制线



        lineChart.getAxisRight().setEnabled(false);
        setMarkerView(lineChart);

    }

    /**
     * 功能：创建图例
     */
    private void createLegend(Legend legend) {
        /***折线图例 标签 设置***/
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }

    /**
     * 曲线初始化设置,一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     */
    public void initLineDataSet(LineDataSet lineDataSet, int color) {
        lineDataSet.setColor(color); // 设置曲线颜色
        lineDataSet.setCircleColor(color);  // 设置数据点圆形的颜色
        lineDataSet.setDrawCircleHole(false);// 设置曲线值的圆点是否是空心
        lineDataSet.setLineWidth(1f); // 设置折线宽度
        lineDataSet.setCircleRadius(3f); // 设置折现点圆点半径
        lineDataSet.setValueTextSize(10f);

        lineDataSet.setDrawFilled(true); //设置折线图填充
        lineDataSet.setFormLineWidth(1f);
//        lineDataSet.setFormSize(15.f);

        //设置曲线展示为圆滑曲线（如果不设置则默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);


    }
}
