package com.demo.inspection.ui.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;


public class MeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.activity_me,container,false);
        return view;
    }

    public static MeFragment getInstances(String name){
        MeFragment meFragment = new MeFragment ();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        meFragment.setArguments(bundle);
        return  meFragment;
    }
}
