package com.demo.inspection.ui.fragment.barchart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.utils.ComDef;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class PieChart extends Fragment {

    private PieChartView pieChartView;
    private PieChartData data;
    private int[] number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_pie_chart, container, false);
        pieChartView = view.findViewById(R.id.piechart);
        number = getArguments().getIntArray("number");
        initDatas();
        int total = number[1] + number[2] + number[3] + number[4];

        TextView textViewP_fine = view.findViewById(R.id.textViewP_fine);
        TextView textViewP_normal = view.findViewById(R.id.textViewP_normal);
        TextView textViewP_alarm = view.findViewById(R.id.textViewP_alarm);
        TextView textViewP_error = view.findViewById(R.id.textViewP_error);

        textViewP_fine.setText("正常状态:" + (number[1] * 100 / total) + "%");
        textViewP_normal.setText("预警状态:" + (number[2] * 100 / total) + "%");
        textViewP_alarm.setText("告警状态:" + (number[3] * 100 / total) + "%");
        textViewP_error.setText("异常状态:" + (number[4] * 100 / total) + "%");

        ImageView imageViewBar = view.findViewById(R.id.imageViewBar);
        imageViewBar.setOnClickListener((view1) -> {
            BarChart barChart = new BarChart();
            Bundle bundle = new Bundle();
            bundle.putIntArray("number", number);
            barChart.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.barChartFL, barChart).commit();
        });

        return view;
    }

    private void initDatas() {

        //初始化数据
        List<SliceValue> values = new ArrayList<SliceValue>();

        for (int i = 1; i < ComDef.STATE_COLORS.length; i++) {
            values.add(new SliceValue(number[i], ComDef.STATE_COLORS[i]));
        }
        data = new PieChartData(values);
        //文字样式设置
        data.setHasLabels(true);
        data.setValueLabelTextSize(18);
        data.setValueLabelsTextColor(Color.DKGRAY);
        data.setValueLabelBackgroundAuto(true);
        data.setValueLabelBackgroundEnabled(true);

        //饼图样式
        data.setHasCenterCircle(true);
        data.setCenterCircleColor(Color.WHITE);
        data.setCenterCircleScale(0.4f);
        data.setCenterText1("设备状态");
        data.setCenterText1Color(Color.DKGRAY);
        data.setCenterText1FontSize(20);
        data.setSlicesSpacing(6);
        pieChartView.setCircleFillRatio(0.7f);
        pieChartView.setChartRotationEnabled(true);

        pieChartView.setCircleFillRatio(0.8f);
        pieChartView.setViewportCalculationEnabled(true);
        pieChartView.setChartRotationEnabled(true);

        pieChartView.setPieChartData(data);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        BarChart barChart = new BarChart();
        Bundle bundle = new Bundle();
        bundle.putIntArray("number", number);
        barChart.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.barChartFL, barChart).commitAllowingStateLoss();

    }
}

