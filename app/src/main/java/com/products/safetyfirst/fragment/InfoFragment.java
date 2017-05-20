package com.products.safetyfirst.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    private View mainView;
    private JustifiedWebView infoTextView;  // Use setInfoText() to set Text. Using WebView for justify text
    private JustifiedWebView checklistView;
    private int position;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_info, container, false);
        infoTextView = (JustifiedWebView) mainView.findViewById(R.id.info_main_text);
        checklistView = (JustifiedWebView) mainView.findViewById(R.id.info_checklist_text);
        position = getArguments().getInt(KnowIt_Fragment.position, 0);

        Resources res = getResources();

        infoTextView.setText(res.getStringArray(R.array.second_desc)[position]);
        checklistView.setText(res.getStringArray(R.array.second_safety_rules)[position],
                "<p style=\" color: #f1551a; font-size: 20px; \">"
                        + "Safety Checklist"
                        + "</p>");

        infoTextView.setVerticalScrollBarEnabled(false);
        checklistView.setVerticalScrollBarEnabled(false);
        return mainView;
    }
}
