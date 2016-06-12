package com.zozocab.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zozocab.app.R;


/**
 * Created by metro on 04-06-2016.
 */
public class DriverInfo extends Fragment {


    public static Fragment newInstance() {
        DriverInfo driverInfo = new DriverInfo();
        return driverInfo;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (container == null) {
            view = inflater.inflate(R.layout.driverinfo, container, false);
        } else {
            view = (View) container;
        }

        return view;

    }


}
