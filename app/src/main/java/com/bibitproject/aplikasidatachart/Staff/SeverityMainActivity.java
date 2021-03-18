package com.bibitproject.aplikasidatachart.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.databinding.ActivitySeverityMainBinding;

public class SeverityMainActivity extends AppCompatActivity {

    Context context = this;

    ActivitySeverityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_severity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_severity_main);

        binding.toolbar.setTitle("Severity");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_white);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnSeverityDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SeverityDailyActivity.class));
            }
        });

        binding.btnSeverityWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SeverityWeeklyActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}