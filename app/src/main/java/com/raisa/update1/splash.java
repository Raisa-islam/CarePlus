package com.raisa.update1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash extends AppCompatActivity {
    TextView wel,learning;

    private static int Splash_timeout=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        wel=findViewById(R.id.textview1);
        learning=findViewById(R.id.textview2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent=new Intent(splash.this, LogIn.class);
                startActivity(splashintent);
                finish();


            }
        },Splash_timeout);

        Animation myanimation = AnimationUtils.loadAnimation(splash.this,R.anim.animation2);
        wel.startAnimation(myanimation);

        Animation myanimation2 = AnimationUtils.loadAnimation(splash.this,R.anim.animation1);
        learning.startAnimation(myanimation2);
    }
}