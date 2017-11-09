package com.products.safetyfirst.fragment.ItemsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ItemTypeInfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private View mainView;

    public VideoFragment() {
        // Required empty public constructor
    }

    //Using http://stackoverflow.com/a/16199649


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_video, container, false);
        WebView webView = (WebView) mainView.findViewById(R.id.webview);
        TextView notAvailable = (TextView) mainView.findViewById(R.id.not_available);


        String url =  ((ItemTypeInfoActivity) getActivity()).getKnowItItemVideo();



        if( url == null||url.equals("no")) {
            notAvailable.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Log.e("Video", "Not Available");
            return mainView;
        }

        String frameVideo = "<html><body><iframe width=\"100%\" height=\"312px\" src=\""+url+"\" frameborder=\"0\" " + "allowfullscreen onload=\"this.width=screen.width-20\"></iframe></body></html>";

          //webView.loadUrl(url);
          webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadData(frameVideo, "text/html", "utf-8");
       // webView.setOnClickListener(new View.OnClickListener() {
         //   @Override
        // public void  onClick(View v) {
        //   Analytics.logEventTutorialBegin(getContext());
      //      }
        // });
        return mainView;
    }
}
