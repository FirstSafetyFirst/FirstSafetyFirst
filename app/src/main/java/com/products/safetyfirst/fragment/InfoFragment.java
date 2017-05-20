package com.products.safetyfirst.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.products.safetyfirst.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private View mainView;
    private WebView infoTextView;  // Use setInfoText() to set Text. Using WebView for justify text
    private WebView checklistView;
    private int position;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_info, container, false);
        infoTextView = (WebView) mainView.findViewById(R.id.info_main_text);
        checklistView = (WebView) mainView.findViewById(R.id.info_checklist_text);
        position = getArguments().getInt(KnowIt_Fragment.position, 0);

        Resources res = getResources();

        setInfoText(res.getStringArray(R.array.second_desc)[position]);
        setChecklistText(res.getStringArray(R.array.second_safety_rules)[position]);

        infoTextView.setVerticalScrollBarEnabled(false);
        checklistView.setVerticalScrollBarEnabled(false);
        return mainView;
    }

    private void setInfoText(String textMain) {
        String text = "<html><body>"
                + "<p align=\"justify\" style=\" color: #2c3b42 \">"
                + textMain
                + "</p> "
                + "</body></html>";

        infoTextView.loadData(text, "text/html", "utf-8");
    }

    private void setChecklistText(String checkText) {
        String text = "<html><body>"
                + "<p style=\" color: #f1551a; font-size: 20px; \">"
                + "Safety Checklist"
                + "</p>"
                + "<p align=\"justify\" style=\" color: #2c3b42 \">"
                + checkText
                + "</p> "
                + "</body></html>";

        checklistView.loadData(text, "text/html", "utf-8");
    }
}
