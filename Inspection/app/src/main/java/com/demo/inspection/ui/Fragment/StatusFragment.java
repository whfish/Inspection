package com.demo.inspection.ui.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.bl.GetData;
import com.demo.inspection.bl.ReqParam;
import com.demo.inspection.ui.Fragment.barchart.BarChart;
import com.demo.inspection.ui.StatusActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class StatusFragment extends Fragment implements View.OnClickListener {

    Calendar calendar = Calendar.getInstance (Locale.CHINA);
    private TextView textViewDate;
    Date date = new Date (System.currentTimeMillis ());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat ("yyyy年MM月dd日");
    int[] numberF;
    String dateString;
    TextView textView_fine;
    TextView textView_normal;
    TextView textView_alarm;
    TextView textView_error;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.activity_status, container, false);


        textViewDate = view.findViewById (R.id.textViewDate);
        textViewDate.setText (simpleDateFormat1.format (date));
        textViewDate.setOnClickListener (this);
        dateString = simpleDateFormat.format (date);
        getData (0);


        textView_fine = view.findViewById (R.id.textView_fine);
        textView_normal = view.findViewById (R.id.textView_normal);
        textView_alarm = view.findViewById (R.id.textView_alarm);
        textView_error = view.findViewById (R.id.textView_error);


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
        // 绑定监听器(How the parent is notified that the date is set.)
        new DatePickerDialog (activity, themeResId, (view, year, monthOfYear, dayOfMonth) -> {

            monthOfYear++;
            StringBuffer sb = new StringBuffer ();
            sb.append (String.format ("%d-%02d-%02d", year, monthOfYear, dayOfMonth));
            Log.i (ComDef.TAG, "选择了：" + sb.toString ());
            textViewDate.setText ((year + "年" + (monthOfYear) + "月" + dayOfMonth + "日"));
            dateString = sb.toString ();
            getData (1);


            new Handler ().postDelayed (() -> {

                textView_fine.setText ("良好状态:" + numberF[1] + "台");
                textView_normal.setText ("正常状态:" + numberF[2] + "台");
                textView_alarm.setText ("告警状态:" + numberF[3] + "台");
                textView_error.setText ("异常状态:" + numberF[4] + "台");

            }, 300);

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
                break;
        }
    }


    public void getData(int id) {

        /**调用后台方法参考
         * 请安步骤修改为自己的方法
         */

        ReqParam req = new ReqParam ();
        req.setUrl (ComDef.INTF_QUERYSTATIC);//修改为实际接口
        HashMap map = new HashMap<String, String> ();
        System.out.println (dateString);
        map.put (ComDef.QUERY_DATE, dateString);//修改为实际请求参数
//        map.put(ComDef.QUERY_INDEX, "1");//修改为实际请求参数
        req.setMap (map);
        new GetData (req) {
            @Override
            public void dealResult(String result) throws JSONException {
                JSONArray array = new JSONArray (result);
                numberF = new int[]{40, 0, 0, 0, 0};
                for (int i = 0; i < array.length (); i++) {
                    JSONObject item = (JSONObject) array.get (i);
                    int index = item.getString ("score").equals ("") ? 4 : Integer.parseInt (item.getString ("score"));
                    int count = Integer.parseInt (item.getString ("count"));
                    numberF[index] = count;

                }
                numberF[0] = numberF[1] + 5;


                switch (id) {
                    case 0:

                        textView_fine.setText ("良好状态设备:" + numberF[1] + "台");
                        textView_normal.setText ("正常状态设备:" + numberF[2] + "台");
                        textView_alarm.setText ("警告状态设备:" + numberF[3] + "台");
                        textView_error.setText ("异常状态设备:" + numberF[4] + "台");

                        BarChart barChart = new BarChart ();
                        Bundle bundle = new Bundle ();
                        bundle.putIntArray ("number", numberF);
                        barChart.setArguments (bundle);
                        getChildFragmentManager ().beginTransaction ().add (R.id.barChartFL, barChart).commit ();
                        break;
                    case 1:


                        BarChart barChart1 = new BarChart ();
                        Bundle bundle1 = new Bundle ();
                        bundle1.putIntArray ("number", numberF);
                        barChart1.setArguments (bundle1);
                        getChildFragmentManager ().beginTransaction ().replace (R.id.barChartFL, barChart1).commit ();

                }

                //        getChildFragmentManager ().beginTransaction ().add (R.id.barChartFL, new BarChart ()).commit ();
                Log.i (ComDef.TAG, "解析后返回:" + numberF.toString ());
            }
        };

    }
}
