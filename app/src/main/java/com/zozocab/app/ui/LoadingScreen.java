package com.zozocab.app.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.zozocab.app.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.zozocab.app.helper.GPSTracker;

public class LoadingScreen extends AppCompatActivity {
    GPSTracker gps;
    public static double latitude ;
    public static double longitude;
    Button retry;
    String res="";
    CardView cview;
    TextView status;

    SharedPreferences sharedPreferences;
    private String FIRST_NAME_KEY = "firstname";
    private String LAST_NAME_KEY="lastname";
    private String IMAGE_KEY = "image";
    private String CONTACT_KEY = "contact";
    private String EMAIL_KEY = "email";
    private String ID_KEY = "id";
    private String REGISTERATION_KEY = "registered";
    private String PREFERENCE_NAME = "zozocab_pref";

    public boolean isInternetWorking() {
        boolean success = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) LoadingScreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null) {
                // There are no active networks.
                return false;
            }
            success = ni.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        sharedPreferences = getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);

        gps = new GPSTracker(LoadingScreen.this);
        status   = (TextView)findViewById(R.id.currentAddress);
        cview = (CardView)findViewById(R.id.cv);
        cview.setVisibility(View.GONE);
        retry = (Button)findViewById(R.id.btnretry);

        //check if internet enabled

        if(isInternetWorking()) {
            // check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
                status.setText("Location not Found");
            }
            CityAsyncTask cst = new CityAsyncTask(LoadingScreen.this,
                    latitude, longitude);
            cst.execute();
        }else{
            status.setText("Please turn on Data Services and Retry again");
            cview.setVisibility(View.VISIBLE);
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternetWorking()) {
                    gps.getLocation();
                    if (gps.canGetLocation()) {

                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                       // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        CityAsyncTask ccst = new CityAsyncTask(LoadingScreen.this,
                                latitude, longitude);
                        ccst.execute();
//                    cst.execute();
                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                        status.setText("Location not Found");
                        cview.setVisibility(View.VISIBLE);
                    }
                }else{
                    status.setText("Please turn on Data Services and Retry again");
                    cview.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    ProgressDialog loading;
    class CityAsyncTask extends AsyncTask<String, String, String> {
        Activity act;
        double latitude;
        double longitude;

        public CityAsyncTask(Activity act, double latitude, double longitude) {
            // TODO Auto-generated constructor stub
            this.act = act;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(LoadingScreen.this, "Please Wait", "Finding information about your Locality...", true, false);

        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            Geocoder geocoder = new Geocoder(act, Locale.getDefault());
            try {

                List<Address> addresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                Log.e("Addresses", "-->" + addresses);
                if(addresses.size()>0) {
                    result = addresses.get(0).getAddressLine(0)+"\n"+addresses.get(0).getAddressLine(1)+
                            "\n"+addresses.get(0).getAddressLine(2);
                }else{

                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            loading.dismiss();
            res = result;
            if(result.length()>0) {
                Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(intent);
                /*if(sharedPreferences.getBoolean(REGISTERATION_KEY,false)) {
                    Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(LoadingScreen.this,RegisterActivity.class));
                }*/
            }else{
                status.setText("Unable to find address. Try Again!");
                cview.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(res.length()>0) {
            finish();
        }
    }
}
