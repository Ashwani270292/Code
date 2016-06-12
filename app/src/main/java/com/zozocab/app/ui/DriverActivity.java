package com.zozocab.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zozocab.app.R;
public class DriverActivity extends AppCompatActivity {
    Circle circle;
    CircleAngleAnimation circleAngleAnimation;
    ImageButton closeSession;
    TextView timeleft;
    RelativeLayout onRequestLayout;
    int dot = 30;      // Length of a Morse Code "dot" in milliseconds
    int short_gap = 100;    // Length of Gap Between dots/dashes
    long[] pattern = {0, dot, short_gap};
    Vibrator mVibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //linking components;

        circle = (Circle)findViewById(R.id.circleanimation);
        circleAngleAnimation = new CircleAngleAnimation(circle,360);
        timeleft = (TextView)findViewById(R.id.timeLeft);
        closeSession = (ImageButton)findViewById(R.id.buttoncloseSession);
        onRequestLayout = (RelativeLayout)findViewById(R.id.onrequest);
        final CountDownTimer countDownTimer = new CountDownTimer(15000,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleft.setText(""+millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                onRequestLayout.setVisibility(View.GONE);
                closeSession.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Request Gone",Toast.LENGTH_LONG).show();
                mVibrator.cancel();
            }
        };

        timeleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request Accepted",Toast.LENGTH_LONG).show();
                onRequestLayout.setVisibility(View.GONE);
                closeSession.setVisibility(View.VISIBLE);
                countDownTimer.cancel();
                mVibrator.cancel();
            }
        });
        closeSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequestLayout.setVisibility(View.VISIBLE);
                closeSession.setVisibility(View.GONE);

                circleAngleAnimation.setDuration(15000);
                circle.startAnimation(circleAngleAnimation);
                countDownTimer.start();
                mVibrator.vibrate(pattern, 0);

            }
        });


        //

    }
}
