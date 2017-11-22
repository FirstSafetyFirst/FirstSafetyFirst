package com.products.safetyfirst.fragment.ItemsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;
import com.products.safetyfirst.Pojos.KnowItItemType;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    private View mainView;
    private JustifiedWebView informationView;
    //private int toolValue;
    //private int typeValue;
    private KnowItItemType knowItItemType;
    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type_info, container, false);
        informationView = mainView.findViewById(R.id.post_body);

        String info = ((ItemTypeInfoActivity) getActivity()).getKnowItItemInfo();

        if(info!=null) {

            informationView.setText(info,
                    "<span style=\" color: #f1551a; font-size: 20px; \">"
                            + "INFORMATION"
                            + "</span><hr>");
        } else {
            informationView.setText("Information Not Available",
                    "<span style=\" color: #f1551a; font-size: 20px; \">"
                            + "INFORMATION"
                            + "</span><hr>");
        }

        return mainView;
    }

}
