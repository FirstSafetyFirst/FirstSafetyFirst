package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.impementations.presenter.PostDetailPresenterImpl;
import com.products.safetyfirst.interfaces.interactor.PostDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.PostDetailPresenter;
import com.products.safetyfirst.modelhelper.UserHelper;
import com.products.safetyfirst.models.Comment;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 30/10/17.
 */

public class PostDetailInteractorImpl implements PostDetailInteractor {

    private final PostDetailPresenter presenter;
    private final UserHelper user;

    public PostDetailInteractorImpl(PostDetailPresenterImpl presenter){
        this.presenter = presenter;
        this.user = UserHelper.getInstance();
    }

    @Override
    public void requestPost(String mPostKey, final OnPostQueryFinishedListener listener) {
        DatabaseReference mPostReference =getDatabase().getReference()
                .child("posts").child(mPostKey);

        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PostModel postModel = dataSnapshot.getValue(PostModel.class);
                presenter.getPost(postModel);
                assert postModel != null;
                if(postModel.getUid() != null){
                    listener.requestPostAuthor(postModel.getUid());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("PostDetailInteractor", "loadPost:onCancelled", databaseError.toException());

            }
        });
    }

    @Override
    public void requestAuthor(String mAuthorKey) {

        DatabaseReference mProfileReference;

            mProfileReference = getDatabase().getReference()
                    .child("users").child(mAuthorKey);

            mProfileReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    presenter.getAuthor(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("PostDetailInteractor", "loadProfile:onCancelled", databaseError.toException());
                }
            });

    }

    @Override
    public void setBookMark(String mPostKey) {

    }

    @Override
    public void setViews(String mPostKey) {

    }


    @Override
    public void setComment(String mPostKey, String mAnswer) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference  mCommentsRef = getDatabase().getReference().child("post-comments").child(mPostKey);

        if(user != null) {

            String key = mCommentsRef.push().getKey();

            Comment comment = new Comment(user.getUid(), user.getDisplayName(), mAnswer, 0);

            Map<String, Object> commentValues = comment.toMap();


            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/post-comments/" + mPostKey + "/" + key, commentValues);

            getDatabase().getReference().updateChildren(childUpdates);
        }
    }

    @Override
    public void requestComments(String mPostKey) {

        Query query;

        query = getDatabase().getReference()
                .child("post-comments").child(mPostKey).orderByChild("timestamp");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Comment> mListOfComments = new ArrayList<>();
                ArrayList<String> commentsArrayKey = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfComments.add(x.getValue(Comment.class));
                    commentsArrayKey.add(x.getKey());
                }
                presenter.getAnswers(mListOfComments, commentsArrayKey);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Post Detail Interacter", "Could not fetch comments");
            }
        });
    }

    @Override
    public void addLike(String mPostKey, String mCommentKey) {

    }
}
