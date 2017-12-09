package com.products.safetyfirst.fragment.ItemsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HowToUseFragment extends Fragment {

    private View mainView;
    private JustifiedWebView informationView;
    //private int toolValue;
    //private int typeValue;

    public HowToUseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type_info, container, false);
        informationView = mainView.findViewById(R.id.post_body);

        String howToUse = ((ItemTypeInfoActivity) getActivity()).getKnowItItemHowtoUse();

        if(howToUse!=null) {

            informationView.setText(howToUse,
                    "<span style=\" color: #f1551a; font-size: 20px; \">"
                            + "HOW TO USE"
                            + "</span><hr>");
        } else {
            informationView.setText("Information Not Available",
                    "<span style=\" color: #f1551a; font-size: 20px; \">"
                            + "HOW TO USE"
                            + "</span><hr>");
        }

        //ta.recycle();
        return mainView;
    }

}
