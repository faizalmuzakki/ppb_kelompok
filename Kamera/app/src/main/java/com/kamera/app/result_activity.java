package com.kamera.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;

public class result_activity extends AppCompatActivity {

    TextView alphabetResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_activity);

        Intent intent = getIntent();

        char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M' ,'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        alphabetResult = findViewById(R.id.alphabetResult);
        Integer label = Integer.parseInt(intent.getStringExtra("label"));

        alphabetResult.setText(String.valueOf(alphabet[label]));
    }
}
