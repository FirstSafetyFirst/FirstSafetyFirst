package com.products.safetyfirst.adaptersnew;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.products.safetyfirst.androidhelpers.PostDocument;
import com.products.safetyfirst.androidhelpers.PostHelper;

import java.util.ArrayList;

/**
 * Created by vikas on 24/11/17.
 */

public abstract class FirestoreAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements EventListener<QuerySnapshot>{

    private static final String TAG = "FirestoreAdapter";

    private ListenerRegistration mRegistration;

    private ArrayList<PostDocument> mSnapshots = new ArrayList<>();

    private PostHelper postHelper;

    private Query mQuery;

    public FirestoreAdapter(final PostHelper.NotifyAdapter notifyAdapter) {
        mQuery= FirebaseFirestore.getInstance().collection("posts");
        postHelper= new PostHelper(mQuery, new PostHelper.UpdateSnapshot() {
            @Override
            public void updateList(ArrayList<PostDocument> snapshots) {
                mSnapshots.addAll(snapshots);
                Log.v("PostHelper","Snapshots added");
                notifyAdapter.notifyChangeInData();
                Log.v("PostHelper","CAlled notifyChange");
            }
        });
    }
    //To query a collection of documents, be it post or comment.
    // Invoke makeQuery method when starting to load data.
    public void makeQuery(){
       postHelper.makeQuery();
    }
    //this method can be called as soon as we have to load more data with the same query
    //parameters as earlier. Here we can use the lastVisible documentSnapshot to start with.
    public void makeNextSetOfQuery(){
        postHelper.makeNextSetOfQuery();
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

    public ArrayList<PostDocument> getmSnapshots() {
        return mSnapshots;
    }

    public void setmSnapshots(ArrayList<PostDocument> mSnapshots) {
        this.mSnapshots = mSnapshots;
    }

    @Override
    public int getItemCount() {
        return mSnapshots.size();
    }

    protected PostDocument getSnapshot(int index) {
        return mSnapshots.get(index);
    }

    protected void onDocumentAdded(DocumentChange change) {
        mSnapshots.add(new PostDocument(change.getDocument(),postHelper.findAuthor(change.getDocument())));
        notifyItemInserted(change.getNewIndex());
    }

    protected void onDocumentModified(DocumentChange change) {
        PostDocument p= new PostDocument(change.getDocument(),postHelper.findAuthor(change.getDocument()));
        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position

            mSnapshots.set(change.getOldIndex(), p);
            notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            mSnapshots.remove(change.getOldIndex());
            mSnapshots.add(change.getNewIndex(), p);
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
