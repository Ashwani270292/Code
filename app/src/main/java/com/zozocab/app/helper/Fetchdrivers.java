package com.zozocab.app.helper;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zozocab.app.Adapter.ApiService;
import com.zozocab.app.model.DriverModel;
import com.zozocab.app.ui.LoadingScreen;
import com.zozocab.app.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.job.JobService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by acrc on 6/12/2016.
 */
public class Fetchdrivers extends JobService {

   List<LatLng> driverslocation = new ArrayList<>();
    List<MarkerOptions> markers = new ArrayList<>();
    String TAG = "FetchDriver";

    @Override
    public boolean onStartJob(final JobParameters params) {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://104.197.214.216:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        final Call<List<DriverModel>> drivers = service.getDrivers();
        drivers.enqueue(new Callback<List<DriverModel>>() {
            @Override
            public void onResponse(Call<List<DriverModel>> call, Response<List<DriverModel>> response) {
                Log.d(TAG,"Fetching");
                if(response.isSuccessful()) {
                    Log.d(TAG,"SuccessFul");
                    List<DriverModel> driverModels = response.body();
                    for (int i = 0; i < driverModels.size(); i++) {
                        driverslocation.add(new LatLng(Double.parseDouble(driverModels.get(i).getLocation().getLatitude()),
                                Double.parseDouble(driverModels.get(i).getLocation().getLongitude())));
                    }


                    if (driverslocation.size() > 0) {
                        new AsyncTask<Void, Void, List<MarkerOptions>>() {
                            @Override
                            protected List<MarkerOptions> doInBackground(Void... params) {
                                Log.d(TAG,"SuccessFul");
                                if(driverslocation.size()>0) {
                                    for (int i = 0; i < driverslocation.size(); i++) {
                                        markers.add(new MarkerOptions().position(driverslocation.get(i)));
                                        /*if (Math.acos(Math.sin(LoadingScreen.latitude) * Math.sin(driverslocation.get(i).latitude) +
                                                Math.cos(LoadingScreen.latitude) * Math.cos(driverslocation.get(i).latitude) *
                                                        Math.cos(driverslocation.get(i).longitude - (LoadingScreen.longitude))) * 6371 <= 3000) {
                                            markers.add(new MarkerOptions().position(driverslocation.get(i)));

                                        }*/
                                    }
                                }
                                return markers;
                            }

                            @Override
                            protected void onPostExecute(List<MarkerOptions> markerOptionses) {
                                super.onPostExecute(markerOptionses);
                                if(markers.size()>0){
                                    Log.d(TAG,"Markerset");
                                    for(int i=0;i<markers.size();i++){
                                        MainActivity.mMap.addMarker(new MarkerOptions().position(markers.get(i).getPosition()));
                                    }
                                }

                            }
                        }.execute();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<DriverModel>> call, Throwable t) {

            }
        });
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }



}
