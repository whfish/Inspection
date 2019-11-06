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
        String id = bundle.getString ("id");

        TextView textID = findViewById (R.id.textID);
        TextView textName = findViewById (R.id.textName);
        TextView textIP = findViewById (R.id.textIP);
        TextView textScore = findViewById (R.id.textScore);
        TextView textDetail = findViewById (R.id.textDetail);

        textID.setText (bundle.getString ("id"));
        textName.setText (bundle.getString ("dev_name"));
        textIP.setText (bundle.getString ("ip"));
        textScore.setText (bundle.getString ("score"));
        textDetail.setText (bundle.getString ("detail"));

    }
}
