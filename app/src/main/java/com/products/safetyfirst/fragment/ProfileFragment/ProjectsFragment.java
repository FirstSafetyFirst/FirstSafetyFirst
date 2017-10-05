package com.products.safetyfirst.fragment.ProfileFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.AddProjectPresenterImpl;
import com.products.safetyfirst.interfaces.AddProjectPresenter;
import com.products.safetyfirst.interfaces.AddProjectView;

public class ProjectsFragment extends Fragment implements View.OnClickListener, AddProjectView {

    AddProjectPresenter presenter;
    private FloatingActionButton mAddProjectButton;
    private OnFragmentInteractionListener mListener;
    private ProgressBar mProgressbar;
    private boolean isFabOpen = false;
    private Animation rotate_forward, rotate_backward;

    public ProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);


        mAddProjectButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressBar);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);


        presenter = new AddProjectPresenterImpl(this);

        mAddProjectButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

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

    @Override
    public void onClick(View view) {

        mAddProjectButton.startAnimation(rotate_backward);
        isFabOpen = false;


        int i = view.getId();
        if (i == R.id.fab) {
            mAddProjectButton.startAnimation(rotate_forward);
            isFabOpen = true;
            showAddProjectDialog();
        }

    }

    @Override
    public void showAddProjectDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_project);
        Button btnDone = (Button) dialog.findViewById(R.id.btn_done);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        final EditText mName = (EditText) dialog.findViewById(R.id.username);
        final EditText mCompany = (EditText) dialog.findViewById(R.id.company);
        final EditText mDescription = (EditText) dialog.findViewById(R.id.description);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateCredentials(mName.getText().toString(), mCompany.getText().toString(),
                        mDescription.getText().toString());
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mAddProjectButton.startAnimation(rotate_backward);
                isFabOpen = false;
            }
        });

        dialog.show();
    }

    @Override
    public void setCompanyError() {

    }

    @Override
    public void setUsernameError() {

    }

    @Override
    public void setDesignationError() {

    }

    @Override
    public void showProgress() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
