package com.zozocab.app.Utils;

/**
 * Created by metro on 28-05-2016.
 */
public class NetworkConnectionUtils {
    private static NetworkConnectionUtils ourInstance = new NetworkConnectionUtils();

    public static NetworkConnectionUtils getInstance() {
        return ourInstance;
    }

    private NetworkConnectionUtils() {
    }
}
