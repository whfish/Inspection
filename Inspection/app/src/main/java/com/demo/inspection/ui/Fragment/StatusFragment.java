package com.demo.inspection.ui.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.ui.Fragment.barchart.BarChart;
import com.demo.inspection.ui.StatusActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class StatusFragment extends Fragment implements View.OnClickListener {

    Calendar calendar = Calendar.getInstance (Locale.CHINA);
    private TextView textViewDate;
    Date date = new Date (System.currentTimeMillis ());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy年MM月dd日");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.activity_status, container, false);
        getChildFragmentManager ().beginTransaction ().add (R.id.barChartFL, new BarChart ()).commit ();

        textViewDate = view.findViewById (R.id.textViewDate);
        textViewDate.setText (simpleDateFormat.format (date));
        textViewDate.setOnClickListener (this);

        return view;
    }

    //构造数
    public static StatusFragment getInstances(String name) {
        StatusFragment statusFragment = new StatusFragment ();
        Bundle bundle = new Bundle ();
        bundle.putString ("name", name);
        statusFragment.setArguments (bundle);
        return statusFragment;
    }

    //日期选择
    public void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog (activity, themeResId, new DatePickerDialog.OnDateSetListener () {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                System.out.println (year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                textViewDate.setText (year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        }
                // 设置初始日期
                , calendar.get (Calendar.YEAR)
                , calendar.get (Calendar.MONTH)
                , calendar.get (Calendar.DAY_OF_MONTH)).show ();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId ()) {

            case R.id.textViewDate:
                showDatePickerDialog (getActivity (), 4, textViewDate, calendar);
                ;
                break;

        }
    }
}
