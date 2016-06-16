package com.zozocab.app.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.strongloop.android.loopback.LocalInstallation;
import com.strongloop.android.loopback.RestAdapter;
import com.zozocab.app.MainApp;
import com.zozocab.app.R;
import com.zozocab.app.Utils.DeviceId;
import com.zozocab.app.fragment.DriverInfo;
import com.zozocab.app.fragment.UserDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.zozocab.app.helper.Fetchdrivers;
import com.zozocab.app.helper.PlaceJSONParser;

import org.json.JSONObject;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener {



    private static final String TAG = MainActivity.class.getSimpleName();
    public static GoogleMap mMap;
    private static final long LOCATION_REFRESH_TIME = 3000;
    private static final float LOCATION_REFRESH_DISTANCE = 4;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String SENDER_ID = "870722126122";

    String LOOPBACK_APP_ID = "loopback-component-push-app";


    AutoCompleteTextView atvPlaces;
    PlacesTask placesTask;
    ParserTask parserTask;

    FragmentManager manager;

    ImageView car_type_one, car_type_two, car_type_three, car_type_four, driverimage;
    RelativeLayout driverDetails;
    LinearLayout carButtonHolder;
    Animation moveupbuttonholder, movedownbuttonholder, moveupdriversdetails, movedowndriverdetails;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private DriverInfo driverInfo;
    private LocationManager mLocationManager;

    private RelativeLayout mainlayout,editview;
    public static LatLng latLng;
    private Location mLocation;
    private CameraPosition cameraPosition;
    private LatLng center;
    private LatLng centerFromPoint;
    private EditText currentLocation;
    private GoogleCloudMessaging gcm;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        mainlayout = (RelativeLayout)findViewById(R.id.mainview);
        editview = (RelativeLayout)findViewById(R.id.enterplace);
        atvPlaces = (AutoCompleteTextView) findViewById(R.id.atv_places);
        atvPlaces.setThreshold(1);

        atvPlaces.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                placesTask = new PlacesTask();
                placesTask.execute(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //  Toast.makeText(Activity_ChangePlace.this, "String is: " + s, Toast.LENGTH_LONG).show();
            }
        });

        currentLocation = (EditText)findViewById(R.id.place_cordinates);
        manager = getSupportFragmentManager();
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else { // Google Play Services are available
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

        ImageView imageView = (ImageView) findViewById(R.id.picker);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    UserDialog userDialog = (UserDialog) UserDialog.newInstance("Alert Dialog");
                    userDialog.show(fragmentManager, userDialog.getTag());

                }
            });
        }


        latLng = new LatLng(LoadingScreen.latitude,LoadingScreen.longitude);
        currentLocation.setText(getCompleteAddressString(latLng.latitude,latLng.longitude));

        //linking components

        moveupbuttonholder = AnimationUtils.loadAnimation(MainActivity.this, R.anim.moveup);
        movedownbuttonholder = AnimationUtils.loadAnimation(MainActivity.this, R.anim.movedown);

        moveupdriversdetails = AnimationUtils.loadAnimation(MainActivity.this, R.anim.moveup);
        movedowndriverdetails = AnimationUtils.loadAnimation(MainActivity.this, R.anim.movedown);

        car_type_one = (ImageView) findViewById(R.id.car_type_one);
        car_type_two = (ImageView) findViewById(R.id.car_type_two);
        car_type_three = (ImageView) findViewById(R.id.car_type_three);
        car_type_four = (ImageView) findViewById(R.id.car_type_four);
        driverimage = (ImageView) findViewById(R.id.driverimage);

        driverDetails = (RelativeLayout) findViewById(R.id.driverDetails);

        carButtonHolder = (LinearLayout) findViewById(R.id.carButtonHolder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setting Listeners

        movedownbuttonholder.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                carButtonHolder.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        movedowndriverdetails.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                driverDetails.setVisibility(View.GONE);
                driverimage.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        driverimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverDetails.startAnimation(movedowndriverdetails);
                driverimage.startAnimation(movedowndriverdetails);
                carButtonHolder.startAnimation(moveupbuttonholder);

            }
        });

        car_type_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carButtonHolder.startAnimation(movedownbuttonholder);

                driverDetails.startAnimation(moveupdriversdetails);
                driverDetails.setVisibility(View.VISIBLE);

                driverimage.startAnimation(moveupdriversdetails);
                driverimage.setVisibility(View.VISIBLE);

            }
        });
        car_type_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carButtonHolder.startAnimation(movedownbuttonholder);

                driverDetails.startAnimation(moveupdriversdetails);
                driverDetails.setVisibility(View.VISIBLE);

                driverimage.startAnimation(moveupdriversdetails);
                driverimage.setVisibility(View.VISIBLE);
            }
        });
        car_type_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carButtonHolder.startAnimation(movedownbuttonholder);

                driverDetails.startAnimation(moveupdriversdetails);
                driverDetails.setVisibility(View.VISIBLE);

                driverimage.startAnimation(moveupdriversdetails);
                driverimage.setVisibility(View.VISIBLE);
            }
        });
        car_type_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carButtonHolder.startAnimation(movedownbuttonholder);

                driverDetails.startAnimation(moveupdriversdetails);
                driverDetails.setVisibility(View.VISIBLE);

                driverimage.startAnimation(moveupdriversdetails);
                driverimage.setVisibility(View.VISIBLE);
            }
        });



/*
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);*/

        JobScheduler jobScheduler = JobScheduler.getInstance(MainActivity.this);

        JobInfo job = new JobInfo.Builder(0,new ComponentName(MainActivity.this,Fetchdrivers.class))
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .build();

        jobScheduler.schedule(job);

        atvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            currentLocation.setText(atvPlaces.getText().toString());
                editview.setVisibility(View.GONE);

            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editview.setVisibility(View.VISIBLE);
            }
        });

        //TODO
        if (checkPlayServices()) {
            updateRegistration();
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check device for Play Services APK.
        checkPlayServices();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private boolean checkPlayServices() {
        final int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "OnActivity Running,", Toast.LENGTH_LONG).show();
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_userprofile) {
            startActivity(new Intent(MainActivity.this, UserProfileActivity.class));

        } else if (id == R.id.nav_login) {
            startActivity(new Intent(MainActivity.this, ChoiceActivity.class));
        } else if (id == R.id.nav_registerPage) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));

        } else if (id == R.id.nav_driver) {
            startActivity(new Intent(MainActivity.this, DriverActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_share_earn) {
            startActivity(new Intent(MainActivity.this, ShareAndEarn.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    @Override
    protected void onStop() {
        drawer.removeDrawerListener(toggle);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        drawer.removeDrawerListener(toggle);
        super.onDestroy();
    }


    //TODO


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            showSettingsAlert();
            return;
        }
        final ImageView imageViewCenter = (ImageView) findViewById(R.id.picker);
        if (imageViewCenter != null) {
            imageViewCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

                center = cameraPosition.target;
                center = mMap.getCameraPosition().target;
                VisibleRegion visibleRegion = mMap.getProjection()
                        .getVisibleRegion();

                Point x = mMap.getProjection().toScreenLocation(
                        visibleRegion.farRight);

                Point y = mMap.getProjection().toScreenLocation(
                        visibleRegion.nearLeft);

                Point centerPoint = new Point(x.x / 2, y.y / 2);


                centerFromPoint = mMap.getProjection().fromScreenLocation(
                        centerPoint);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentLocation.setText(getCompleteAddressString(centerFromPoint.latitude,centerFromPoint.longitude));
                    }
                });

                // Add a marker in Sydney and move the camera
//        latLng = new LatLng(-34, 151);

            }
        });

//        mMap.addCircle(new CircleOptions().center(latLng));
 //       currentLocation.setText("Center LatLng" + centerFromPoint);
        mMap.setMyLocationEnabled(true);
        latLng = new LatLng(LoadingScreen.latitude, LoadingScreen.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * *
     * public void stopUsingGPS(){
     * if(locationManager != null){
     * locationManager.removeUpdates(GPSTracker.this);
     * }
     * }
     */

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        String strAdd = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE,
                    LONGITUDE, 1);

            if (addresses != null) {

                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {

                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i)).append(
                            ",");
                }

                strAdd = strReturnedAddress.toString();

                Log.w(TAG, strReturnedAddress.toString());
            } else {
                Log.w(TAG, "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "Current location ", Toast.LENGTH_LONG).show();
    }


    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyATSwJSctT8pFhcwfGWAlRWfJthwzgROyU";

            String input="";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input+"&"+types+"&"+sensor+"&"+key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/"+output+"?"+parameters;

            try{
                // Fetching the data from we service
                data = downloadUrl(url);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
          //  Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }


        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_list_item_1, from, to);

            // Setting the adapter
            atvPlaces.setAdapter(adapter);
        }
    }

    private void registerInBackground(final LocalInstallation installation) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(final Void... params) {
                try {
                    final String regid = gcm.register(SENDER_ID);
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
                DeviceId.with(getApplicationContext()).saveInstallation(installation);
            }
        }.execute(null, null, null);
    }


    private void updateRegistration() {
        gcm = GoogleCloudMessaging.getInstance(this);

        // 1. Grab the shared RestAdapter instance.
        final MainApp app = (MainApp) getApplication();
        final RestAdapter adapter = app.getLoopBackAdapter();

        // 2. Create LocalInstallation instance

        final LocalInstallation installation =  new LocalInstallation(context, adapter);



        // 3. Update Installation properties that were not pre-filled

        // Enter the id of the LoopBack Application
        installation.setAppId(LOOPBACK_APP_ID);

        // Substitute a real id of the user logged in this application
        installation.setUserId("loopback-android");

        // 4. Check if we have a valid GCM registration id
        if (installation.getDeviceToken() != null) {
            // 5a. We have a valid GCM token, all we need to do now
            //     is to save the installation to the server
//            saveInstallation(installation);
            DeviceId.with(getApplicationContext()).saveInstallation(installation);
        } else {
            // 5b. We don't have a valid GCM token. Get one from GCM
            // and save the installation afterwards.
//            registerInBackground(installation);
            DeviceId.with(getApplicationContext()).registerInBackground(installation);
        }
    }

}
