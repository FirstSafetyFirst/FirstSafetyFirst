package com.products.safetyfirst.fragment.ItemsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;
import com.products.safetyfirst.models.KnowItItemType;
import com.products.safetyfirst.utils.JustifiedWebView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeHowToUseFragment extends Fragment {

    private View mainView;
    private JustifiedWebView informationView;
    //private int toolValue;
    //private int typeValue;

    public TypeHowToUseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type_info, container, false);
        informationView = (JustifiedWebView) mainView.findViewById(R.id.post_body);

        //toolValue = getArguments().getInt(ItemTypeInfoActivity.tool, 0);
        // typeValue = getArguments().getInt(ItemTypeInfoActivity.typeNumber, 0);

        KnowItItemType knowItItemType = getArguments().getParcelable("KnowItItemType");
        //TypedArray ta = getResources().obtainTypedArray(R.array.fourth_how_to);

      /*  informationView.setText(knowItItemType.getHow_to_use(),
                "<span style=\" color: #f1551a; font-size: 20px; \">"
                        + "HOW TO USE"
                        + "</span><hr>");*/

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
