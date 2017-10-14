package com.products.safetyfirst.modelhelper;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.products.safetyfirst.models.Discussion_model;
import com.products.safetyfirst.utils.DatabaseUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rishabh on 12/10/17.
 */

public class PostHelper {

    private UserHelper userhelper = new UserHelper();

    public void createNewPost(String postKey, String title, String body, String file,  List<String> imageList ) {
        int time = (int) System.currentTimeMillis();
        Discussion_model post = new Discussion_model(
                userhelper.getUserId(), title, userhelper.getUserImgUrl(), body, time, file, imageList
        );
        DatabaseUtil db = new DatabaseUtil();
        db.getDatabase().getReference().child("posts").child(postKey).setValue(post);  // Create post in /posts/
        db.getDatabase().getReference().child("user-posts").child(userhelper.getUserId()).child(postKey).setValue(true);  // push post key in /user-posts/
    }

    public String createPostKey() {
        DatabaseUtil db = new DatabaseUtil();
        return db.getDatabase().getReference().child("posts").push().getKey();
    }

    public void createImageUrls(final String postKey, final List<Bitmap> images, final List<String> imageList, final int position, final FinalCallback finalCallback){
        if(position == images.size()){
            finalCallback.onComplete(imageList);
            return;
        }
        uploadImage(images.get(position), postKey, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                imageList.add(downloadUrl);
                createImageUrls(postKey, images, imageList, position+1, finalCallback);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
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

    public interface FinalCallback {
        public void onComplete(List<String> imageList);
    }

}