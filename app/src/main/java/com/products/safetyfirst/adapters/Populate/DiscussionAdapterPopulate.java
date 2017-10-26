package com.products.safetyfirst.adapters.Populate;

import android.util.Pair;

import com.products.safetyfirst.adapters.DiscussionAdapter;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.models.PostModel;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rishabh on 22/10/17.
 */

public class DiscussionAdapterPopulate implements DiscussionAdapter.DiscussionCallbacks {

    private PostHelper postHelper = PostHelper.getInstance();

    @Override
    public void updateData(final List<Pair<String, PostModel>> postList, final DiscussionAdapter adapter) {
        Single<String> subs;  // A dummy single
        if(postList == null || postList.size() == 0) {
            subs = postHelper.getLatestPost();
        } else {
            subs = Single.just(postList.get(postList.size() - 1).first);
        }
        subs.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String key) throws Exception {
                        recursePostLinkedList(key, 0, postList, adapter);
                    }
                });
    }

    private void recursePostLinkedList(final String key, final int count, final List<Pair<String, PostModel>> postList, final DiscussionAdapter adapter) {
        postHelper.getPost(key).subscribe(new Consumer<PostModel>() {
            @Override
            public void accept(PostModel postModel) throws Exception {
                Pair<String, PostModel> pair = new Pair<>(key, postModel);
                postList.add(pair);
                adapter.notifyDataSetChanged();
                if (count < 10 && postModel.getPreviousPost() == null) {
                    recursePostLinkedList(postModel.getPreviousPost(), count+1, postList, adapter);
                }
            }
        });
    }
}
