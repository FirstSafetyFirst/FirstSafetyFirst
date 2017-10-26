package com.products.safetyfirst.adapters.Populate;

import android.util.Pair;

import com.products.safetyfirst.adapters.DiscussionAdapter;
import com.products.safetyfirst.modelhelper.AuthorHelper;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.models.PostModel;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by rishabh on 22/10/17.
 */

public class PeerPostPopulate implements DiscussionAdapter.DiscussionCallbacks {

    private AuthorHelper authorHelper = AuthorHelper.getInstance();
    private PostHelper postHelper = PostHelper.getInstance();
    private String uid;

    public PeerPostPopulate(String uid) {
        this.uid = uid;
    }

    @Override
    public void updateData(final List<Pair<String, PostModel>> postList, final DiscussionAdapter adapter) {
        authorHelper.getPeerPosts(uid).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                recurseForPosts(strings, 0, postList, adapter);
            }
        });
    }

    private void recurseForPosts(final List<String> keys, final int position, final List<Pair<String, PostModel>> postList, final DiscussionAdapter adapter) {
        if(position == keys.size()){
            return;
        }
        postHelper.getPost(keys.get(position)).subscribe(new Consumer<PostModel>() {
            @Override
            public void accept(PostModel postModel) throws Exception {
                Pair<String, PostModel> pair = new Pair<>(keys.get(position), postModel);
                postList.add(pair);
                adapter.notifyDataSetChanged();
                recurseForPosts(keys, position+1, postList, adapter);
            }
        });
    }
}
