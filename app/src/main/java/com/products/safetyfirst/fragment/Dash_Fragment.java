package com.products.safetyfirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.HomeAdapter;
import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.models.News_model;
import com.products.safetyfirst.models.Slider_Model;

import java.util.ArrayList;

/**
 * Created by profileconnect on 20/04/17.
 */

public class Dash_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dash_fragment, container, false);

        return rootView;
    }



}
