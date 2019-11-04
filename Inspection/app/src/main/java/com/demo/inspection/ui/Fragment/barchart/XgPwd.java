package com.demo.inspection.ui.Fragment.barchart;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class XgPwd extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_xgpwd);

        Log.i("xgmm","jinruxiugai");
        //View view = inflater.inflate (R.layout.activity_xgpwd, container, false);
        Log.i("xgmm","aaaaaaaaa");

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("xgmm","jinruxiugai");
        View view = inflater.inflate (R.layout.activity_xgpwd, container, false);
        Log.i("xgmm","aaaaaaaaa");




        return view;
    }



}

