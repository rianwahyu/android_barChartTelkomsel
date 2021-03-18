package com.bibitproject.aplikasidatachart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibitproject.aplikasidatachart.Utils.MyConfig;

public class SplashScreenActivity extends AppCompatActivity {

    Context context = this;
    private static int SPLASH_SCREEN =  4000;

    Animation topAnim, bottomAnim;
    ImageView imageView;
    TextView textTitle, textSubTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imgLogoSplash);
        textTitle = findViewById(R.id.tvTitleSplash);


        imageView.setAnimation(topAnim);
        textTitle.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
               /* if (new SessionManager(context).isLoggedin()){
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        }, SPLASH_SCREEN);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            MyConfig.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            MyConfig.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }
}