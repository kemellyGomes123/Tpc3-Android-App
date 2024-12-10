package com.example.tpc3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;

public class FormActivity2 extends AppCompatActivity {

    private EditText etName, etEmail, etBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etBirthday = findViewById(R.id.etBirthday);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String record = "Name: " + etName.getText().toString() +
                    "\nEmail: " + etEmail.getText().toString() +
                    "\nBirthday: " + etBirthday.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("record", record);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
    }
}