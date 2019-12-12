package com.kamera.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class result_activity extends AppCompatActivity {

    TextView alphabetResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activity);

        Intent intent = getIntent();

        alphabetResult = findViewById(R.id.alphabetResult);
        alphabetResult.setText(intent.getStringExtra("message"));
    }
}
