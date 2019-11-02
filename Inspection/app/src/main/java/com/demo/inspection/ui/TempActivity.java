package com.demo.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.demo.inspection.R;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        //跳转到状态页面
        Button buttonS =findViewById (R.id.buttonToStatus);
        buttonS.setOnClickListener ((view)->{
            Intent intent =new Intent ();
            intent.setClass (this,StatusActivity.class);
            startActivity (intent);
        });
    }
}
