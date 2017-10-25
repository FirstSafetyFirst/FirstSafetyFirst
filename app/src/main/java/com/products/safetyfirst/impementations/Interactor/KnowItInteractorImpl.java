package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.KnowItInteractor;
import com.products.safetyfirst.interfaces.presenter.KnowItPresenter;
import com.products.safetyfirst.models.KnowItItem;
import com.products.safetyfirst.models.KnowItItemType;

import java.util.ArrayList;
import java.util.HashMap;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by ishita sharma on 10/18/2017.
 */

public class KnowItInteractorImpl implements KnowItInteractor {

    private KnowItPresenter presenter;
    private static ArrayList<KnowItItem> mListOfItems= new ArrayList<>();
    private static String item_name,info,safety_checklist,thumb_url;
    private static HashMap<String,KnowItItemType> mItemtypeMap;
    private ArrayList<String> itemNames= new ArrayList<>();

    public KnowItInteractorImpl(KnowItPresenter presenter){
        this.presenter= presenter;
    }

    @Override
    public void getKnowit() {
        final Query query = getDatabase().getReference().child("knowIt").orderByChild("timestamp");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d :dataSnapshot.getChildren()){
                    KnowItItem k= d.getValue(KnowItItem.class);
                    k.setItem_name(d.getKey());
                    mListOfItems.add(k);
                }
                presenter.getChildren(mListOfItems);
                Log.e("testing",mListOfItems.get(0).getItem_info()+" "+
                mListOfItems.get(0).getItem_name()+" "+
                mListOfItems.get(0).getSafety_checklist()+" "+
                mListOfItems.get(0).getKnow_it_item_types());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("KnowItInteractorImpl","Fetching of KnowItItems failed");

            }
        });
    }

    @Override
    public void getKnowitItem(int position) {
            for(KnowItItem item :mListOfItems){
                item_name=item.getItem_name();
                info= item.getItem_info();
                safety_checklist=item.getSafety_checklist();
                thumb_url= item.getThumb_url();
                mItemtypeMap=item.getKnow_it_item_types();
            }
    }
}