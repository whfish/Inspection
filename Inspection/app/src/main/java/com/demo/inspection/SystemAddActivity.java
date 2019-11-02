package com.demo.inspection;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SystemAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_systemdetails);


        Bundle bundle = this.getIntent ().getExtras ();
        String id = bundle.getString ("id");

        EditText editTextInput = findViewById (R.id.editTextInput);
        EditText editTextOutput = findViewById (R.id.editTextOutput);
        EditText editTextWeight = findViewById (R.id.editTextWeight);
        EditText editTextAE = findViewById (R.id.editTextAmountExercise);

        editTextInput.setText (bundle.getString ("input"));
        editTextOutput.setText (bundle.getString ("output"));
        editTextWeight.setText (bundle.getString ("weight"));
        editTextAE.setText (bundle.getString ("AE"));

    }
}
