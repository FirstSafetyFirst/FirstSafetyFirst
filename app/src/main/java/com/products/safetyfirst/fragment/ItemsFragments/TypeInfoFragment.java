package com.products.safetyfirst.fragment.ItemsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.models.KnowItItemType;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeInfoFragment extends Fragment {

    private View mainView;
    private JustifiedWebView informationView;
    //private int toolValue;
    //private int typeValue;
    private KnowItItemType knowItItemType;
    public TypeInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type_info, container, false);
        informationView = (JustifiedWebView) mainView.findViewById(R.id.type_info);


        knowItItemType= getArguments().getParcelable("KnowItItemType");

        //TypedArray ta = getResources().obtainTypedArray(R.array.third_description);

        informationView.setText(knowItItemType.getItem_info(),
                "<span style=\" color: #f1551a; font-size: 20px; \">"
                        + "INFORMATION"
                        + "</span><hr>");

        return mainView;
    }

}
