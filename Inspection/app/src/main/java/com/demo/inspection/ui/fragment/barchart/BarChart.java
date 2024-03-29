package com.demo.inspection.ui.fragment.barchart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.utils.ComDef;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

public class BarChart extends Fragment {


    ColumnChartView columnChartView = null;
    View view;
    //统计图数据
    private ColumnChartData data;
    //数据标志
    private int[] numberF;
    //模拟数据
    private int[] colors = ComDef.STATE_COLORS;
    private String[] mold = {"", "正常", "预警", "告警", "异常"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_bar_chart, container, false);
        columnChartView = view.findViewById(R.id.columnchart);

        //获取数据
        numberF = getArguments().getIntArray("number");
        generateView();

        ImageView imageViewPie = view.findViewById(R.id.imageViewPie);
        imageViewPie.setOnClickListener((view) -> {

            //传递参数
            PieChart pieChart = new PieChart();
            Bundle bundle = new Bundle();
            bundle.putIntArray("number", numberF);
            pieChart.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.barChartFL, pieChart).commit();

        });

        return view;
    }

    private void generateView() {
        //定义有多少个柱子
        int numColumns = 5;
        //定义表格实现类
        ColumnChartData columnChartData;
        //Column 是下图中柱子的实现类
        List<Column> columns = new ArrayList<>();
        //SubcolumnValue 是下图中柱子中的小柱子的实现类，下面会解释我说的是什么
        List<SubcolumnValue> values;
        //循环初始化每根柱子，
        List<AxisValue> axisValues = new ArrayList<AxisValue>();

        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            values.add(new SubcolumnValue(numberF[i], colors[i]));
            Column column = new Column(values);
            //是否有数据标注
            column.setHasLabels(true);
            column.hasLabels();
            columns.add(column);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(mold[i]));
        }

        //给表格添加写好数据的柱子
        columnChartData = new ColumnChartData(columns);
        //给画表格的View添加要画的表格
        columnChartView.setColumnChartData(columnChartData);
        //创建一个带有之前圆柱对象column集合的ColumnChartData
        data = new ColumnChartData(columns);
        data.setValueLabelTextSize(15);
        data.setValueLabelBackgroundColor(Color.parseColor("#00000000"));
        data.setValueLabelBackgroundEnabled(true);
        data.setValueLabelBackgroundAuto(false);
        //柱状图的宽度
        data.setFillRatio(0.55f);
        data.setValueLabelsTextColor(Color.BLACK);
        //定义x轴y轴相应参数
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setTextSize(15);
        axisY.setTextColor(Color.GRAY);
        axisY.setLineColor(Color.GRAY);
        axisY.hasLines();//是否显示网格线
        axisX.hasLines();
        axisX.setTextColor(Color.parseColor("#525252"));
        axisX.setValues(axisValues);
        axisX.setTextSize(11);
        axisX.setTextColor(Color.GRAY);
        axisX.setLineColor(Color.RED);
        axisX.setHasSeparationLine(false);
        axisX.setMaxLabelChars(10);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        //把X轴Y轴数据设置到ColumnChartData 对象中
        //给表填充数据，显示出来
        columnChartView.setInteractive(false);
        columnChartView.setColumnChartData(data);

        //设置边框值
        Viewport viewport = new Viewport(0.5f, columnChartView.getMaximumViewport().height() * 1.3f, 7, 0);
        Viewport viewportMax = new Viewport(0.5f, columnChartView.getMaximumViewport().height() * 1.3f, 7, 0);

        columnChartView.setCurrentViewport(viewport);
        columnChartView.setMaximumViewport(viewportMax);
        columnChartView.moveTo(0, 0);

    }

}
