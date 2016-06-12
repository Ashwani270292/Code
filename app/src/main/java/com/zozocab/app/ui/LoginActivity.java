package com.zozocab.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zozocab.app.R;


public class LoginActivity extends AppCompatActivity {

    String login_as;
    private Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_as = getIntent().getExtras().getString("LOGINAS");

        btnlogin = (Button)findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login_as.contains("USER")){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(LoginActivity.this,DriverNavigationActivity.class));
                }
            }
        });
    }
}
