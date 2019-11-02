package com.demo.inspection.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.ui.Fragment.barchart.BarChart;


public class StatusFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.activity_status,container,false);
        getChildFragmentManager ().beginTransaction ().add (R.id.barChartFL,new BarChart ()).commit ();





        return view;
    }
//构造数
    public static StatusFragment getInstances(String name){
        StatusFragment statusFragment = new StatusFragment ();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        statusFragment.setArguments(bundle);
        return  statusFragment;
    }
}
