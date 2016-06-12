package com.zozocab.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zozocab.app.R;


public class ChoiceActivity extends AppCompatActivity {

    Button userlogin,driverlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        userlogin = (Button)findViewById(R.id.btnuserlogin);
        driverlogin = (Button)findViewById(R.id.btndriverlogin);

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this,LoginActivity.class);
                intent.putExtra("LOGINAS","USER");
                startActivity(intent);
            }
        });

        driverlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceActivity.this,LoginActivity.class);
                intent.putExtra("LOGINAS","DRIVER");
                startActivity(intent);
            }
        });
    }
}
