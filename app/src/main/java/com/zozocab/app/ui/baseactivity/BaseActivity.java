package com.zozocab.app.ui.baseactivity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by metro on 28-05-2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        initizationView();
        if (enableTitle()) {
            getSupportActionBar().setTitle(getTitle());
        }
    }

    private void initizationView() {

    }

    protected abstract boolean enableTitle();



    protected abstract int getContentView();

    protected abstract String getTtile();

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
        }
    }
}
