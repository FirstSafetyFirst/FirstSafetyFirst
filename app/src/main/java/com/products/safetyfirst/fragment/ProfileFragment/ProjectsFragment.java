package com.products.safetyfirst.fragment.ProfileFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.ProjectsAdapter;
import com.products.safetyfirst.impementations.presenter.AddProjectPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.AddProjectPresenter;
import com.products.safetyfirst.interfaces.view.AddProjectView;

import static com.products.safetyfirst.utils.FirebaseUtils.getCurrentUserId;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class ProjectsFragment extends Fragment implements View.OnClickListener, AddProjectView {

    private AddProjectPresenter presenter;
    private FloatingActionButton mAddProjectButton;
    private OnFragmentInteractionListener mListener;
    private ProgressBar mProgressbar;
    private boolean isFabOpen = false;
    private Animation rotate_forward, rotate_backward;

    private RecyclerView mListOfProjects;
    private ProjectsAdapter adapter;
    private TextView noProjectsText;

    private String mProfileKey = null;

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

        if(this.getArguments() != null) {
            mProfileKey = this.getArguments().getString("PROFILE_KEY");
        }
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createUI(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillUI();
    }

    private void fillUI() {
        adapter = new ProjectsAdapter(getContext());

        if(mProfileKey != null){
            adapter.request(mProfileKey);
        }

        else if(getCurrentUserId() != null){
            adapter.request(getCurrentUserId());
        }

        mListOfProjects.setAdapter(adapter);
    }

    private void createUI(View view) {
        mListOfProjects = view.findViewById(R.id.recycler_view);
        mListOfProjects.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListOfProjects.setHasFixedSize(true);
        mListOfProjects.setItemAnimator(new DefaultItemAnimator());

        noProjectsText = view.findViewById(R.id.no_projects);

        mAddProjectButton = view.findViewById(R.id.fab);
        mProgressbar = view.findViewById(R.id.progressBar);
        rotate_forward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

        presenter = new AddProjectPresenterImpl(this);
        mAddProjectButton.setOnClickListener(this);

        mListOfProjects.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                //    mAddProjectButton.hide();
                } else {
                //    mAddProjectButton.show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        if(this.getArguments() != null){

            //Hides the fab but it reappears on scroll due to app:layout_scrollFlags="scroll|enterAlways" set
            //in layout. Remove that attribute first programatically and then set visibility to gone.
            mAddProjectButton.setVisibility(View.GONE);
        }

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
        Button btnDone = dialog.findViewById(R.id.btn_done);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        final EditText mName = dialog.findViewById(R.id.username);
        final EditText mCompany = dialog.findViewById(R.id.company);
        final EditText mDescription = dialog.findViewById(R.id.description);

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

    @Override
    public void noProjects() {
        noProjectsText.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("EmptyMethod")
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
