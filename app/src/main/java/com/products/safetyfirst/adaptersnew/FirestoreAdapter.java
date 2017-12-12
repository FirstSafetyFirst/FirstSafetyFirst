package com.products.safetyfirst.adaptersnew;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by vikas on 24/11/17.
 */

public abstract class FirestoreAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements EventListener<QuerySnapshot> {

    private static final String TAG = "FirestoreAdapter";

    private Query mQuery;
    private ListenerRegistration mRegistration;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    private ArrayList<DocumentSnapshot> mSnapshots = new ArrayList<>();

    private DocumentSnapshot lastVisible;
    boolean needToload= true;
    private static final int THRESHOLD =10;

    public FirestoreAdapter(Query query) {
        mQuery = query;
    }
/**
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItems) {

        if(firstVisibleItem + visibleItemCount + THRESHOLD > mSnapshots.size()){
            makeNextSetOfQuery();
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //do nothing for now...
        //because the logic for loading is already implemented in onScroll
    }
**/
    // load data according to scrolling
    /*
    public void LoadDataAccordingToScrolling(RecyclerView view,String key, String orderProperty, long limit){
        int currentPosition=0;
        if(needToload){
            makeQuery(key,orderProperty,limit);
            needToload=false;
        }
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

    }
**/
    //To query a collection of documents, be it post or comment.
    // Invoke makeQuery method when starting to load data.
    public void makeQuery(Query query){

        mQuery=  query.limit(THRESHOLD);
        Log.v(TAG,mQuery.toString());
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot documentSnapshot=null;
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.v(TAG, document.getId() + " => " + document.getData());
                        mSnapshots.add(document);
                        documentSnapshot=document;
                        Log.v(TAG, mSnapshots.toString());
                    }
                    lastVisible=documentSnapshot;
                } else {
                    Log.v(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        Log.v(TAG, mSnapshots.toString());
        Log.v(TAG,mSnapshots.size()+" ");

    }
    //this method can be called as soon as we have to load more data with the same query
    //parameters as earlier. Here we can use the lastVisible documentSnapshot to start with.
    public void makeNextSetOfQuery(){
        if(lastVisible!=null)
        mQuery=mQuery.startAt(lastVisible);
        mQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                ArrayList<DocumentSnapshot> nextSetOfData= (ArrayList<DocumentSnapshot>) documentSnapshots.getDocuments();
                if(nextSetOfData!=null)
                mSnapshots.addAll(nextSetOfData);
                lastVisible= documentSnapshots.getDocuments().get(documentSnapshots.size()-1);
            }
        });
        mQuery.startAt(lastVisible).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.v(TAG, document.getId() + " => " + document.getData());
                        mSnapshots.add(document);
                        Log.v(TAG, mSnapshots.toString());
                    }
                } else {
                    Log.v(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
        if (e != null) {
            Log.w(TAG, "onEvent:error", e);
            onError(e);
            return;
        }

        // Dispatch the event
        Log.d(TAG, "onEvent:numChanges:" + documentSnapshots.getDocumentChanges().size());
        for (DocumentChange change : documentSnapshots.getDocumentChanges()) {
            switch (change.getType()) {
                case ADDED:
                    onDocumentAdded(change);
                    break;
                case MODIFIED:
                    onDocumentModified(change);
                    break;
                case REMOVED:
                    onDocumentRemoved(change);
                    break;
            }
        }

        onDataChanged();
    }



    public void startListening() {
        if (mQuery != null && mRegistration == null) {
            mRegistration = mQuery.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (mRegistration != null) {
            mRegistration.remove();
            mRegistration = null;
        }

        mSnapshots.clear();
        notifyDataSetChanged();
    }

    public void setQuery(Query query) {
        // Stop listening
        stopListening();

        // Clear existing data
        mSnapshots.clear();
        notifyDataSetChanged();

        // Listen to new query
        mQuery = query;
        startListening();
    }

    public ArrayList<DocumentSnapshot> getmSnapshots() {
        return mSnapshots;
    }

    public void setmSnapshots(ArrayList<DocumentSnapshot> mSnapshots) {
        this.mSnapshots = mSnapshots;
    }

    @Override
    public int getItemCount() {
        return mSnapshots.size();
    }

    protected DocumentSnapshot getSnapshot(int index) {
        return mSnapshots.get(index);
    }

    protected void onDocumentAdded(DocumentChange change) {
        mSnapshots.add(change.getNewIndex(), change.getDocument());
        notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            mSnapshots.set(change.getOldIndex(), change.getDocument());
            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), change.getDocument());
            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    protected void onDocumentRemoved(DocumentChange change) {
        mSnapshots.remove(change.getOldIndex());
        notifyItemRemoved(change.getOldIndex());
    }

    protected void onError(FirebaseFirestoreException e) {};

    protected void onDataChanged() {}
}
