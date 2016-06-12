package com.zozocab.app.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by metro on 28-05-2016.
 */
public class NetworkUtils {


    private final Context context;

    private NetworkUtils(Context context) {
        this.context = context;
    }

    public void isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        connectivityManager.isActiveNetworkMetered();


    }

}
