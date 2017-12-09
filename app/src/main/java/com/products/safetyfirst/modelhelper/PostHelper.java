package com.products.safetyfirst.modelhelper;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.Pojos.UserModel;
import com.products.safetyfirst.utils.Constants;
import com.products.safetyfirst.utils.DatabaseUtil;
import com.products.safetyfirst.utils.StringHelper;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

import static com.products.safetyfirst.utils.DatabaseUtil.getFireStore;

/**
 * Created by rishabh on 12/10/17.
 */

public class PostHelper {

    private static final PostHelper instance = new PostHelper();

    private PostHelper() {
    }

    public static PostHelper getInstance() {
        return instance;
    }

    private final UserHelper userhelper = UserHelper.getInstance();
    private StringHelper stringHelper = StringHelper.getInstance();

    public void createNewPost(
            final DocumentReference postKey,
            final String title,
            final String body,
            final List<String> fileList,
            final List<String> imageList) {

            PostModel post = new PostModel(
                    title, body, userhelper.getUserId(), userhelper.getUserName(), imageList, fileList
            );

            //WRITE TO FIREBASE
            postKey.set(post);



        Map<String, Boolean> keyValue = new HashMap<>();
        keyValue.put(postKey.getId(), true);

        getFireStore().collection(Constants.USERS_POSTS_LINK).document(userhelper.getUserId()).set(keyValue);
           // DatabaseUtil.getDatabase().getReference().child(Constants.POSTS_STARS_LINK).child(postKey).setValue(0);
           // DatabaseUtil.getDatabase().getReference().child(Constants.POSTS_VIEWS_LINK).child(postKey).setValue(0);
    }

    public DocumentReference createPostKey() {
        //return DatabaseUtil.getDatabase().getReference().child("posts").push().getKey();
        return getFireStore().collection("posts").document();

    }

    public void createImageUrls(final String postKey, final List<Bitmap> images, final List<String> imageList, final int position, final UploadCallbacks uploadCallbacks){
        if(position == images.size()){
            uploadCallbacks.onComplete(imageList);
            return;
        }
        if(position == 0) {
            uploadCallbacks.onStartUpload();
        }
        uploadImage(images.get(position), postKey, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                imageList.add(downloadUrl);
                uploadCallbacks.onProgress(position+1, images.size());
                createImageUrls(postKey, images, imageList, position+1, uploadCallbacks);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadCallbacks.onFail(e);
                imageList.clear();
            }
        });
    }

    private void uploadImage(Bitmap bitmap, String postKey, OnSuccessListener<UploadTask.TaskSnapshot> success, OnFailureListener fail) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("posts").child(postKey).child(randomName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(fail).addOnSuccessListener(success);
    }

    public Single<Integer> getStarCount(final String pid) {
        return Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull final SingleEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(0);
                DatabaseUtil.getDatabase().getReference().child(Constants.POSTS_STARS_LINK).child(pid)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Integer star = 0;
                                emitter.onSuccess(star);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    public Single<PostModel> getPost(final String key){
        return Single.create(new SingleOnSubscribe<PostModel>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull final SingleEmitter<PostModel> emitter) throws Exception {
                DatabaseUtil.getDatabase().getReference().child(Constants.POSTS_LINK).child(key)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                @SuppressWarnings("UnnecessaryLocalVariable")
                                DataSnapshot data = dataSnapshot;
                                Log.e("Data Received", data.toString());
                                emitter.onSuccess(dataSnapshot.getValue(PostModel.class)); // TODO: improve null ?
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }



    private static String randomName() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString()+".jpg";
    }

    public interface UploadCallbacks {

        void onComplete(List<String> imageList);

        void onStartUpload();

        void onProgress(int progress, int total);

        void onFail(Exception e);
    }

}
