package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.EventModel;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;

/**
 * Created by vikas on 01/11/17.
 */

public interface PostPresenter {


    void onDestroy();

    void request();

    void getChildren(ArrayList<PostModel> postModels);

    void addAction(String mEventId, String mProfileKey, int mAction);

    void onActionError(String message);

    void onActionSuccess(String message);

    void getKeys(ArrayList<String> postArrayKey);


}
