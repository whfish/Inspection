package com.demo.inspection.ui.Fragment.barchart;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
        View view = inflater.inflate (R.layout.activity_pie_chart, container, false);
        pieChartView = view.findViewById (R.id.piechart);
        number = getArguments ().getIntArray ("number");
        initDatas ();
        int total = number[1] + number[2] + number[3] + number[4];

        TextView textViewP_fine = view.findViewById (R.id.textViewP_fine);
        TextView textViewP_normal = view.findViewById (R.id.textViewP_normal);
        TextView textViewP_alarm = view.findViewById (R.id.textViewP_alarm);
        TextView textViewP_error = view.findViewById (R.id.textViewP_error);

        textViewP_fine.setText ("正常状态:" + (number[1] * 100 / total) + "%");
        textViewP_normal.setText ("预警状态:" + (number[2] * 100 / total) + "%");
        textViewP_alarm.setText ("告警状态:" + (number[3] * 100 / total) + "%");
        textViewP_error.setText ("异常状态:" + (number[4] * 100 / total) + "%");


        ImageView imageViewBar = view.findViewById (R.id.imageViewBar);
        imageViewBar.setOnClickListener ((view1) -> {
            BarChart barChart = new BarChart ();
            Bundle bundle = new Bundle ();
            bundle.putIntArray ("number", number);
            barChart.setArguments (bundle);
            getFragmentManager ().beginTransaction ().replace (R.id.barChartFL, barChart).commit ();
        });

        return view;
    }

    private void initDatas() {

        //初始化数据
        List<SliceValue> values = new ArrayList<SliceValue> ();

        values.add (new SliceValue (number[1], Color.rgb (76, 175, 80)));
        values.add (new SliceValue (number[2], Color.rgb (228, 95, 95)));
        values.add (new SliceValue (number[3], Color.rgb (247, 201, 77)));
        values.add (new SliceValue (number[4], Color.rgb (154, 183, 224)));
        data = new PieChartData (values);
        //样式设置
        //是否显示文本内容(默认为false)
        data.setHasLabels (true);
        //是否点击饼模块才显示文本（默认为false,为true时，setHasLabels(true)无效）
        data.setHasLabelsOnlyForSelected (false);
        //文本内容是否显示在饼图外侧(默认为false)
        data.setHasLabelsOutside (false);
        //文本字体大小
        data.setValueLabelTextSize (18);
        //文本文字颜色
        data.setValueLabelsTextColor (Color.BLUE);

        data.setValueLabelBackgroundAuto (true);
        //设置是否有文字背景
        data.setValueLabelBackgroundEnabled (true);
/*****************************中心圆设置************************************/
        //饼图是空心圆环还是实心饼状（默认false,饼状）
        data.setHasCenterCircle (true);
        //中心圆的颜色（需setHasCenterCircle(true)，因为只有圆环才能看到中心圆）
        data.setCenterCircleColor (Color.WHITE);
        //中心圆所占饼图比例（0-1）
        data.setCenterCircleScale (0.4f);
		/*=====================中心圆文本（可以只设置一个文本）==========/
		/*--------------------第1个文本----------------------*/
        //中心圆中文本
        data.setCenterText1 ("设备状态");
        //中心圆的文本颜色
        data.setCenterText1Color (Color.BLUE);
        //中心圆的文本大小
        data.setCenterText1FontSize (20);


        //饼图各模块的间隔(默认为0)
        data.setSlicesSpacing (6);

        //整个饼图所占视图比例（0-1）
        pieChartView.setCircleFillRatio (0.7f);
        //饼图是否可以转动（默认为true）
        pieChartView.setChartRotationEnabled (true);

        pieChartView.startDataAnimation ();         //设置动画
        pieChartView.setCircleFillRatio (0.8f);//设置饼状图占整个view的比例
        pieChartView.setViewportCalculationEnabled (true);//设置饼图自动适应大小
        pieChartView.setChartRotationEnabled (true);//设置饼图是否可以手动旋转


        pieChartView.setPieChartData (data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        BarChart barChart = new BarChart ();
        Bundle bundle = new Bundle ();
        bundle.putIntArray ("number", number);
        barChart.setArguments (bundle);
        getFragmentManager ().beginTransaction ().replace (R.id.barChartFL, barChart). commitAllowingStateLoss();
    }
}

