package com.bibitproject.aplikasidatachart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bibitproject.aplikasidatachart.Admin.HomeAdminActivity;
import com.bibitproject.aplikasidatachart.Staff.HomeStaffActivity;
import com.bibitproject.aplikasidatachart.Utils.SessionManager;
import com.bibitproject.aplikasidatachart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Context context = this;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SessionManager(context).isLoggedin()){
                    if (new SessionManager(context).getUserAccess().equals("Admin")){
                        startActivity(new Intent(context, HomeAdminActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(context, HomeStaffActivity.class));
                        finish();
                    }
                }else {
                    startActivity(new Intent(context, OpsiLoginActivity.class));
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}