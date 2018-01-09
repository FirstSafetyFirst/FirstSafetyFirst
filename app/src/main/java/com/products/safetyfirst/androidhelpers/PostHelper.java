package com.products.safetyfirst.androidhelpers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.products.safetyfirst.Pojos.PostModel;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 12/13/2017.
 */

public class PostHelper {

    public interface UpdateSnapshot {
        void updateList(ArrayList<PostDocument> mSnapshots);
    }

    public interface NotifyAdapter{
        void notifyChangeInData();
    }

    private static final String TAG = "PostHelper";
    private int THRESHOLD = 10,i=0;
    private UpdateSnapshot updateSnapshot;
    private ArrayList<PostDocument> mSnapshots = new ArrayList<>();
    private ArrayList<DocumentSnapshot> postdocuments = new ArrayList<>();
    private DocumentSnapshot postLastVisible, authorLastVisible, userSnapshot;
    private Query mQuery;

    public PostHelper(Query query, UpdateSnapshot updateSnapshot) {
        mQuery = query.limit(THRESHOLD);
        this.updateSnapshot = updateSnapshot;
    }

    private int document_count;
    private boolean isMakeQueryCalled = false;

    // Invoke makeQuery method when starting to load data.
    public void makeQuery() {
        isMakeQueryCalled = true;
        Log.v(TAG, mQuery.toString()+"makeQuery() called");
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    document_count = 0;
                    for (final DocumentSnapshot postdocument : task.getResult()) {
                        document_count++;
                        Log.v(TAG, postdocument.getId() + " => " + postdocument.getData());

                        postdocuments.add(postdocument);
                        if(document_count==THRESHOLD){
                            fetchUserDetails();
                        }
                    }
                } else {
                    Log.v(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        mSnapshots.clear();
    }

    int d=0;

    private void fetchUserDetails() {
        d=0;
        for(i=0; i<postdocuments.size(); i++){
            final DocumentSnapshot postdocument= postdocuments.get(i);
            PostModel postModel= new PostModel(postdocument.getData());
            String uid = postModel.getUid();
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", uid).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                userSnapshot = task.getResult().getDocuments().get(0);
                                mSnapshots.add(new PostDocument(postdocument, userSnapshot));
                                d=d+1;
                                Log.v(TAG,d+"");
                                if (d == THRESHOLD-1) {
                                    updateSnapshot.updateList(mSnapshots);
                                    Log.v(TAG,mSnapshots.toString());
                                    mSnapshots.clear();
                                    postdocuments.clear();
                                    postLastVisible = postdocument;
                                    authorLastVisible = userSnapshot;
                                }
                            } else
                                userSnapshot = null;
                        }
                    });
        }
    }

    //this method can be called as soon as we have to load more data with the same query
    //parameters as earlier. Here we can use the lastVisible documentSnapshot to start with.
    public void makeNextSetOfQuery() {
        Log.v(TAG, "makenextsetofQuery");
        if (!isMakeQueryCalled || postLastVisible == null) {
            Log.v(TAG, "Error: First invoke makeQuery");
            return;
        }
        mQuery.startAt(postLastVisible)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    document_count = 0;
                    for (final DocumentSnapshot postdocument : task.getResult()) {
                        document_count++;
                        Log.v(TAG, postdocument.getId() + " => " + postdocument.getData());
                        postdocuments.add(postdocument);
                        if(document_count==THRESHOLD) {
                            fetchUserDetails();
                        }
                    }
                }
                else {
                    Log.v(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        mSnapshots.clear();
    }

    DocumentSnapshot author;

    public DocumentSnapshot findAuthor(DocumentSnapshot d) {
        PostModel p = new PostModel(d.getData());
        String uid = p.getUid();
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("uid", uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            author = task.getResult().getDocuments().get(0);
                        }
                    }
                });
        return author;
    }

}

