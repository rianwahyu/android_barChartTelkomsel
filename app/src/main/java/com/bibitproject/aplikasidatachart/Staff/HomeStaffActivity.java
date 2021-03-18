package com.bibitproject.aplikasidatachart.Staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.Utils.MyConfig;
import com.bibitproject.aplikasidatachart.databinding.ActivityHomeStaffBinding;

public class HomeStaffActivity extends AppCompatActivity {

    Context context = this;

    ActivityHomeStaffBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_staff);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_staff);


        binding.btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AlarmMainActivity.class));
            }
        });

        binding.btnPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyConfig.showToast(context, "Dalam Pengembangan");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}