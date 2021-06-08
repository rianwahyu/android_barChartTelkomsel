package com.bibitproject.aplikasidatachart.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.databinding.ActivityAlarmMainBinding;

public class AlarmMainActivity extends AppCompatActivity {

    Context context = this;
    ActivityAlarmMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alarm_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_main);

        binding.toolbar.setTitle("Alarm");
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

        binding.btnAlarmSeverity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SeverityMainActivity.class));
            }
        });

        binding.btnAlarmDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnAlarmDevice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(context, DeviceAlarmActivity.class));
                    }
                });
            }
        });

        binding.btnAlarmCause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, CauseActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}