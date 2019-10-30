package com.demo.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.demo.inspection.R;
import com.demo.inspection.bl.ComDef;
import com.demo.inspection.utils.ToastUtil;
import com.demo.inspection.utils.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    Tools tools=new Tools();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.editText);
        text.addTextChangedListener(editclick);

    }

    private TextWatcher editclick = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.i(ComDef.TAG,"afterTextChanged(");
            tools.CheckIP(text,MainActivity.this);

        }
    };
}
