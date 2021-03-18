package com.bibitproject.aplikasidatachart.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bibitproject.aplikasidatachart.R;
import com.bibitproject.aplikasidatachart.SplashScreenActivity;
import com.bibitproject.aplikasidatachart.Utils.SessionManager;
import com.bibitproject.aplikasidatachart.databinding.ActivityHomeAdminBinding;

public class HomeAdminActivity extends AppCompatActivity {

    Context context = this;

    ActivityHomeAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_admin);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_admin);


        binding.btnRegistrationUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RegistrationUserActivity.class));

            }
        });


        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLogout();
            }
        });
    }

    private void dialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Logout");
        builder.setMessage("Apakah anda ingin logout dari aplikasi ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                new SessionManager(context).logout();
                startActivity(new Intent(context, SplashScreenActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}