package com.example.khoukha.homework3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread myThread = new Thread(){

            @Override
            public void run()
            {
                try {
                    ImageView splashImageView = (ImageView) findViewById(R.id.splashImageView);
                    RotateAnimation rotateAnimation = new RotateAnimation(30.0f, 360.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.3f);
                    rotateAnimation.setDuration((long) 2*500);
                    rotateAnimation.setRepeatCount(0);
                    splashImageView.startAnimation(rotateAnimation);
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
