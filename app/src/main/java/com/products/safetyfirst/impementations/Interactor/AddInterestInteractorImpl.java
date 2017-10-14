package com.products.safetyfirst.impementations.Interactor;

import com.products.safetyfirst.interfaces.interactor.AddInterestInteractor;
import com.products.safetyfirst.interfaces.presenter.AddInterestPresenter;
import com.products.safetyfirst.models.Interest_model;

import java.util.ArrayList;

/**
 * Created by vikas on 05/10/17.
 */

public class AddInterestInteractorImpl implements AddInterestInteractor {

    private AddInterestPresenter presenter;

    public AddInterestInteractorImpl(AddInterestPresenter pre) {
        this.presenter = pre;
    }

    @Override
    public void addInterest(ArrayList<Interest_model> interests, OnUpdateFinishedListener listener) {

    }

    @Override
    public void requestInterest() {
        ArrayList<Interest_model> mListOfInterests = new ArrayList<>();
        mListOfInterests.add(new Interest_model("PPE", false));
        mListOfInterests.add(new Interest_model("Fire Safety", true));
        mListOfInterests.add(new Interest_model("Ladder Safety", true));
        mListOfInterests.add(new Interest_model("Health Safety", true));
        mListOfInterests.add(new Interest_model("Chemical", false));
        mListOfInterests.add(new Interest_model("Others", false));
        presenter.getChildren(mListOfInterests);

       /* Query query;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String mProfileKey = null;

        if (user != null) {
            mProfileKey = user.getUid();

            query = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(mProfileKey).child("interests");

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Interest_model> mListOfInterests = new ArrayList<>();

                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        mListOfInterests.add(x.getValue(Interest_model.class));
                    }
                    presenter.getChildren(mListOfInterests);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Add Project Interacter", "Could not fetch projects");
                }
            });


        }*/
    }
}
