package com.products.safetyfirst.androidhelpers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.products.safetyfirst.utils.DatabaseUtil.getFireStore;
/**
 * Created by ishita sharma on 12/23/2017.
 */

public class TagHelper {

    private static final TagHelper tagHelper= new TagHelper();

    public TagHelper(){
        loadTags();
    }

    public TagHelper getInstance(){

        return tagHelper;
    }

    private ArrayList<String> tags= new ArrayList<>();

    public void loadTags(){

        getFireStore().getInstance().collection("tags").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult()){
                        tags.add(documentSnapshot.getReference().getId());

                    }
                }
            }
        });

        if(tags.size()==0){
            Log.v("tags","no tags");
        }
    }

    public ArrayList<String> getTags(){
        return tags;
    }
}
