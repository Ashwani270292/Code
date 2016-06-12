package com.zozocab.app.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.zozocab.app.R;
import com.zozocab.app.fragment.DriverInfo;
import com.zozocab.app.fragment.UserDialog;

import java.util.List;
import java.util.Locale;

import com.zozocab.app.helper.Fetchdrivers;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static GoogleMap mMap;

    FragmentManager manager;

    ImageView car_type_one, car_type_two, car_type_three, car_type_four, driverimage;
    RelativeLayout driverDetails;
    LinearLayout carButtonHolder;
    Animation moveupbuttonholder, movedownbuttonholder, moveupdriversdetails, movedowndriverdetails;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private DriverInfo driverInfo;
    private LocationManager mLocationManager;


    public static LatLng latLng;
    private Location mLocation;
    private CameraPosition cameraPosition;
    private LatLng center;
    private LatLng centerFromPoint;
    private EditText currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
