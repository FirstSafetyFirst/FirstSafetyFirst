package com.products.safetyfirst.fragment.ProfileFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ProfileActivity;
import com.products.safetyfirst.adapters.DiscussionAdapter;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class QuestionsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView home_recycler;

    private DatabaseReference mDatabase;
    private ProgressBar mpaginateprogbar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QuestionsFragment newInstance(String param1, String param2) {
        QuestionsFragment fragment = new QuestionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_questions, container, false);

        mDatabase = getDatabase().getReference();
        mpaginateprogbar= rootView.findViewById(R.id.newspaginateprogbar);


        home_recycler= rootView.findViewById(R.id.discussion_recycler);
        home_recycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        home_recycler.setLayoutManager(mLayoutManager);
        home_recycler.setItemAnimator(new DefaultItemAnimator());
      //  home_recycler.setAdapter(new DiscussionAdapter(mLayoutManager, new PeerPostPopulate(ProfileActivity.getProfileKey())));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressWarnings("EmptyMethod")
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
