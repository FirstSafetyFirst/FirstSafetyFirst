package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;

/**
 * Created by profileconnect on 20/04/17.
 */

public class International_Fragment extends Fragment {
    public static final String ARG_TITLE = "arg_title";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.slider_item, container, false);


        return rootView;
    }

}
