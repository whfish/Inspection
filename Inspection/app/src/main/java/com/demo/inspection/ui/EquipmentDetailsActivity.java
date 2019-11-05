package com.demo.inspection.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.inspection.R;

public class EquipmentDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_equipmentdetails);


        Bundle bundle = this.getIntent ().getExtras ();

        TextView textID = findViewById (R.id.textID);
        TextView textName = findViewById (R.id.textName);
        TextView textIP = findViewById (R.id.textIP);
        TextView textScore = findViewById (R.id.textScore);
        TextView textDetail = findViewById (R.id.textDetail);

        textID.setText ("ID:"+bundle.getString ("id"));
        textName.setText ("设备名:"+bundle.getString ("dev_name"));
        textIP.setText ("IP:"+bundle.getString ("ip"));
        textScore.setText ("状态："+bundle.getString ("score"));
        textDetail.setText ("描述："+bundle.getString ("detail"));

    }
}
