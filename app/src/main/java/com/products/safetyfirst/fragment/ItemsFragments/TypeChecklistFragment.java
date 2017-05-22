package com.products.safetyfirst.fragment.ItemsFragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeChecklistFragment extends Fragment {


    public TypeChecklistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_checklist, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        String url;

        int toolValue = getArguments().getInt(ItemTypeInfoActivity.tool, 0);
        int typeValue = getArguments().getInt(ItemTypeInfoActivity.typeNumber, 0);

        TypedArray ta = getResources().obtainTypedArray(R.array.checklist_embed);
        url = getResources().getStringArray(ta.getResourceId(toolValue, 0))[typeValue];
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse(url), "application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
// TODO : ANDROID PERMISSION WRITE_eXTERNAL_STORAGE