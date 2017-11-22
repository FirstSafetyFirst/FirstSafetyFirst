package com.products.safetyfirst.fragment.ItemsFragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.products.safetyfirst.R;
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
public class ChecklistFragment extends Fragment {

    private static final int VIEW_FILE_CODE = 10;
    private File openedFile;
    private ProgressBar progress;
    private Button btn;

    public ChecklistFragment() {
        // Required empty public constructor
    }

    private static void downloadFile(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // swallow a 404
        } catch (IOException e) {
            //swallow 404
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_type_checklist, container, false);
        btn = mainView.findViewById(R.id.checklist);
        progress = mainView.findViewById(R.id.download_progress);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf();
                progress.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
            }
        });
        return mainView;
    }

    private void openPdf(){

       final  String url  = ((ItemTypeInfoActivity) getActivity()).getKnowItItemCheckList();

        if (url==null) return ;

        File dir = getContext().getCacheDir();
        String[] fileNameSplit = url.split("/");
        fileNameSplit = fileNameSplit[fileNameSplit.length-1].split("[?]");
        final String fileName = fileNameSplit[0];
        final File file = new File(dir, fileName);
        new Thread(new Runnable() {
            public void run() {
                if(!file.exists()) {  //check if file exists is cache
                    downloadFile(url, file);
                }
                final File externalFile = new File(Environment.getExternalStorageDirectory(), fileName);
                openedFile = externalFile;
                try {
                    copy(file, externalFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable(){
                    public void run() {
                        progress.setVisibility(View.GONE);
                        btn.setVisibility(View.VISIBLE);
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(externalFile));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivityForResult(intent, VIEW_FILE_CODE);
                        } catch (ActivityNotFoundException e){
                            Toast.makeText(getContext(), "Install PDF Viewer", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.pdfviewer&hl=en"));
                            startActivity(intent);
                        }
                    }
                });
            }
        }).start();

    }

    private void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == VIEW_FILE_CODE) {
            if(resultCode == RESULT_OK){
                openedFile.delete();
            }
        }
    }
}