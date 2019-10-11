package com.example.sber_app5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FinanceProgressView financeProgressView = findViewById(R.id.finance_progress);
        int mProgress = 180;
        financeProgressView.setmProgress(mProgress);

    }
}
