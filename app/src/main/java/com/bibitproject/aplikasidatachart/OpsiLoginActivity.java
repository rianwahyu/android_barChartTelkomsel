package com.bibitproject.aplikasidatachart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bibitproject.aplikasidatachart.Admin.LoginAdminActivity;
import com.bibitproject.aplikasidatachart.Staff.LoginStaffActivity;
import com.bibitproject.aplikasidatachart.databinding.ActivityOpsiLoginBinding;

public class OpsiLoginActivity extends AppCompatActivity {
    Context context = this;

    ActivityOpsiLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_opsi_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_opsi_login);

        binding.btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginAdminActivity.class));
                //finish();
            }
        });

        binding.btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginStaffActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}