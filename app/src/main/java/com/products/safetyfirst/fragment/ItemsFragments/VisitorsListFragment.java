package com.products.safetyfirst.fragment.ItemsFragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.EventsDetailActivity;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitorsListFragment extends Fragment {


    private static final int VIEW_FILE_CODE = 10;
    private File openedFile;
    private ProgressBar progress;
    private Button btn;
    private ImageView sad;
    private TextView noVisitor;

    public VisitorsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_type_checklist, container, false);
        btn = mainView.findViewById(R.id.checklist);
        progress = mainView.findViewById(R.id.download_progress);
        noVisitor = mainView.findViewById(R.id.no_vistor_text);
        sad = mainView.findViewById(R.id.sad);
        final String url  = ((EventsDetailActivity) getActivity()).getVisitors();
        if (url==null || url == "NULL") {
            noVisitor.setVisibility(View.VISIBLE);
            sad.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(url);
                progress.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);


            }
        });
        return mainView;
    }

    private void openPdf(final String url){


        if(url == null || url =="NULL") return;

        if (url.equalsIgnoreCase("Not Available")){
            Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }

}
