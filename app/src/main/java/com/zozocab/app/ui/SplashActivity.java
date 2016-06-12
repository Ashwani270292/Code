package com.zozocab.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.zozocab.app.R;

public class SplashActivity extends AppCompatActivity {
    ImageView cabbackground;
    ImageView appname;
    Animation zoomin, zoomout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //loading animations
        zoomin = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoomin);
        zoomout = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoomout);

        //linking components

        appname = (ImageView) findViewById(R.id.appname);
        cabbackground = (ImageView) findViewById(R.id.taxiimage);

        //adding listeners to animations

        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cabbackground.setVisibility(View.VISIBLE);
                cabbackground.startAnimation(zoomin);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Thread th = new Thread(){
                    @Override
                    public void run() {

                        try {
                            sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            startActivity(new Intent(SplashActivity.this,LoadingScreen.class));
                            finish();
                        }
                    }
                };
                th.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        appname.startAnimation(zoomout);



    }
}
