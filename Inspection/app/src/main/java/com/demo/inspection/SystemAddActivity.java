package com.demo.inspection;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SystemAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemadd);


        Bundle bundle = this.getIntent ().getExtras ();
        String id = bundle.getString ("id");

        EditText editTextInput = findViewById (R.id.editTextInput);
        EditText editTextOutput = findViewById (R.id.editTextOutput001);
        EditText editTextWeight = findViewById (R.id.editTextWeight);
        EditText editTextAE = findViewById (R.id.editTextAmountExercise);

        editTextInput.setText (bundle.getString ("id"));
        editTextOutput.setText (bundle.getString ("detial"));
        editTextWeight.setText (bundle.getString ("opttime"));
        editTextAE.setText (bundle.getString ("score"));

    }
}
