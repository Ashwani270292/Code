package com.zozocab.app;

import android.app.Application;
import android.support.multidex.MultiDex;


/**
 * Created by metro on 04-06-2016.
 */
public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());


//        http://104.197.214.216:3000/api/
//
//        RestAdapter adapter = new RestAdapter(getApplicationContext(), " http://104.197.214.216:3000/api");
//        adapter.connect(getApplicationContext(), "");
    }
}
