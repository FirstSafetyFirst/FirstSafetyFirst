package com.products.safetyfirst.fragment.ItemsFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.products.safetyfirst.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeVideoFragment extends Fragment {

    private View mainView;

    public TypeVideoFragment() {
        // Required empty public constructor
    }

    //Using http://stackoverflow.com/a/16199649


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_type_video, container, false);

        String url = "https://www.youtube.com/embed/Hx9i3L2eDzs";
        String frameVideo = "<html><body><iframe width=\"100%\" height=\"312px\" src=\""+url+"\" frameborder=\"0\" allowfullscreen onload=\"this.width=screen.width-20\"></iframe></body></html>";

        WebView webView = (WebView) mainView.findViewById(R.id.webview);
        //   wv.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(frameVideo, "text/html", "utf-8");

        return mainView;
    }



}
