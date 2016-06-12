package com.zozocab.app.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zozocab.app.R;

/**
 * Created by metro on 08-06-2016.
 */
public class UserDialog extends DialogFragment {


    public static DialogFragment newInstance(String title) {
        UserDialog userDialog = new UserDialog();

        return userDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
         if (container == null) {
             view = inflater.inflate(R.layout.popudialog, container, false);
         }else {
             view = container;
         }
        return view;
    }

}
