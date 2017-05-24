package com.products.safetyfirst.fragment.ItemsFragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        View mainView = inflater.inflate(R.layout.fragment_type_checklist, container, false);
        Button btn = (Button) mainView.findViewById(R.id.checklist);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf();
            }
        });
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void openPdf(){
        String url;

        int toolValue = getArguments().getInt(ItemTypeInfoActivity.tool, 0);
        int typeValue = getArguments().getInt(ItemTypeInfoActivity.typeNumber, 0);

        TypedArray ta = getResources().obtainTypedArray(R.array.checklist_embed);
        url = getResources().getStringArray(ta.getResourceId(toolValue, 0))[typeValue];
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse(url), "application/pdf");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        ta.recycle();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://drive.google.com/viewerng/viewer?embedded=true&url="+url));
        startActivity(intent);
    }
}