package com.products.safetyfirst.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.KnowItSecondActivity;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    private View mainView;
    private JustifiedWebView infoTextView;  // Use setInfoText() to set Text. Using WebView for justify text
    private JustifiedWebView checklistView;
    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_info, container, false);
        infoTextView = mainView.findViewById(R.id.info_main_text);
        infoTextView.setText(((KnowItSecondActivity) this.getActivity()).getInfo());
        infoTextView.setVerticalScrollBarEnabled(false);

        return mainView;
    }
}
