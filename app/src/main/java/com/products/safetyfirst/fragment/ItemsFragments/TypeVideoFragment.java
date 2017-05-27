package com.products.safetyfirst.fragment.ItemsFragments;


import android.content.res.TypedArray;
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
        WebView webView = (WebView) mainView.findViewById(R.id.webview);
        TextView notAvailable = (TextView) mainView.findViewById(R.id.not_available);

        int toolValue = getArguments().getInt(ItemTypeInfoActivity.tool, 0);
        int typeValue = getArguments().getInt(ItemTypeInfoActivity.typeNumber, 0);

        TypedArray ta = getResources().obtainTypedArray(R.array.youtubeEmbed);
        String url = getResources().getStringArray(ta.getResourceId(toolValue, 0))[typeValue];

        if(url.equals("no")) {
            notAvailable.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            Log.e("Video", "Not Available");
            return mainView;
        }

        String frameVideo = "<html><body><iframe width=\"100%\" height=\"312px\" src=\""+url+"\" frameborder=\"0\" allowfullscreen onload=\"this.width=screen.width-20\"></iframe></body></html>";

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
