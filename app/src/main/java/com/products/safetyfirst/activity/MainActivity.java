package com.products.safetyfirst.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapter.LondonEyeListAdapter;
import com.products.safetyfirst.circle_recycler.layoutmanager.LondonEyeLayoutManager;
import com.products.safetyfirst.circle_recycler.layoutmanager.scroller.IScrollHandler;
import com.products.safetyfirst.circle_recycler.utils.DebugRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        List<String> mList = new ArrayList<>(Arrays.asList(
                "News",
                "Discussion",
                "Laws",
                "Know It"));

        private DebugRecyclerView mRecyclerView;

        private LondonEyeLayoutManager mLondonEyeLayoutManager;

        private LondonEyeListAdapter mVideoRecyclerViewAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            int screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getActivity().getResources().getDisplayMetrics().heightPixels;

//            int circleRadius = screenHeight*2 + screenWidth/2;
//
//            int xOrigin = -screenHeight*2 + screenWidth/4;
            int circleRadius = screenWidth;

            int xOrigin = -200;
            int yOrigin = 0;
            mRecyclerView = (DebugRecyclerView) rootView.findViewById(R.id.recycler_view);
            mRecyclerView.setParameters(circleRadius, xOrigin, yOrigin);

//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            mLondonEyeLayoutManager = new LondonEyeLayoutManager(
                    circleRadius,
                    xOrigin,
                    yOrigin,
                    mRecyclerView,
                    IScrollHandler.Strategy.NATURAL);

            mRecyclerView.setLayoutManager(mLondonEyeLayoutManager);//new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            mVideoRecyclerViewAdapter = new LondonEyeListAdapter(getActivity(), mList);

            mRecyclerView.setAdapter(mVideoRecyclerViewAdapter);

            return rootView;
        }
    }
}
