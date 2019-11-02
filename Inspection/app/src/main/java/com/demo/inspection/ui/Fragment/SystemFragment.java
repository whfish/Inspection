package com.demo.inspection.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;


public class SystemFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.activity_system,container,false);
        return view;
    }

    public static SystemFragment getInstances(String name){
        SystemFragment systemFragment = new SystemFragment ();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        systemFragment.setArguments(bundle);
        return  systemFragment;
    }
}
