package com.zozocab.app;

import android.app.Application;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.strongloop.android.loopback.RestAdapter;


/**
 * Created by metro on 04-06-2016.
 */
public class MainApp extends Application {


    RestAdapter adapter;

    public RestAdapter getLoopBackAdapter() {
        if (adapter == null) {
            // Instantiate the shared RestAdapter. In most circumstances,
            // you'll do this only once; putting that reference in a singleton
            // is recommended for the sake of simplicity.
            // However, some applications will need to talk to more than one
            // server - create as many Adapters as you need.
            adapter = new RestAdapter(
                    getApplicationContext(),
                    "http://104.197.214.216:3010/api/");
        }
        return adapter;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());

        this.startService(new Intent(getApplicationContext(), DialogService.class));


    }


}
