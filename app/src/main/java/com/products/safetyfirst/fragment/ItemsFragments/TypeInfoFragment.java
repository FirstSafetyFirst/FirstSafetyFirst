package com.products.safetyfirst.fragment.ItemsFragments;


import android.content.res.TypedArray;
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
public class TypeInfoFragment extends Fragment {

    private View mainView;
    private JustifiedWebView informationView;
    private int positionValue;
    private int typeValue;
    public TypeInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type_info, container, false);
        informationView = (JustifiedWebView) mainView.findViewById(R.id.type_info);

        positionValue = getArguments().getInt(ItemTypeInfoActivity.position, 0);
        typeValue = getArguments().getInt(ItemTypeInfoActivity.typeNumber, 0);

        TypedArray ta = getResources().obtainTypedArray(R.array.third_description);

        informationView.setText(getResources().getStringArray(ta.getResourceId(positionValue, 0))[typeValue],
                "<span style=\" color: #f1551a; font-size: 20px; \">"
                        + "INFORMATION"
                        + "</span><hr>");

        ta.recycle();
        return mainView;
    }

}
