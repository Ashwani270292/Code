package com.zozocab.app.Utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.strongloop.android.loopback.LocalInstallation;
import com.strongloop.android.loopback.Model;
import com.twilio.client.Twilio;
import com.zozocab.app.constants.Constants;

import java.io.IOException;

/**
 * Created by metro on 11-06-2016.
 */
public class DeviceId {

    private GoogleCloudMessaging gcm;
    String TAG = "DEVICEID";



    public static DeviceId with(Context context){

        return new DeviceId();
    }


    public void registerInBackground(final LocalInstallation installation) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {
                try {
                    final String regid = gcm.register(Constants.SENDER_ID);
                    installation.setDeviceToken(regid);
                    return "Device registered, registration ID=" + regid;
                } catch (final IOException ex) {
                    Log.e(TAG, "GCM registration failed.", ex);
                    return "Cannot register with GCM:" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
            }

            @Override
            protected void onPostExecute(final String msg) {
//                mDisplay.append(msg + "\n\n");
                saveInstallation(installation);
            }
        }.execute(null, null, null);
    }
   public void saveInstallation(final LocalInstallation installation) {
        installation.save(new Model.Callback() {

            @Override
            public void onSuccess() {
                final Object id = installation.getId();
                final String msg = "Installation saved with id " + id;
                Log.i(TAG, msg);
//                mDisplay.append(msg + "\n\n");
            }

            @Override
            public void onError(final Throwable t) {
                Log.e(TAG, "Cannot save Installation", t);

                final String msg = "Cannot save device registration,"
                        + " will re-try when restarted.\n"
                        + "Reason: " + t.getMessage();
//                mDisplay.append(msg + "\n\n");
            }
        });
    }


   
}
