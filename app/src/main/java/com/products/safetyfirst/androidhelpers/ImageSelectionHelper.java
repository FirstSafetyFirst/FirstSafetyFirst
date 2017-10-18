package com.products.safetyfirst.androidhelpers;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rishabh on 17/10/17.
 *
 * Selecting multiple images from gallery
 *
 * Usage :  1. create an object of ImageSelectionHelper
 *          2. call "pickMultipleImage"
 *          3. call onReceiveResult from onActivityResult and give the parameters as it is
 */

public class ImageSelectionHelper {

    private Activity activity;
    private int PICK_IMAGE;
    private List<Bitmap> imageList;
    private boolean allowed;

    private int REQUEST_READ = 12;

    public ImageSelectionHelper(Activity activity) {
        this.activity = activity;
        this.imageList = new ArrayList<>();
        this.PICK_IMAGE = (new Random()).nextInt(1000) + 100;
        this.allowed = false;

        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                allowed = true;
            } else {
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
                allowed = true;
            }
        } else {
            allowed = true;
        }
    }

    public void pickMultipleImages() {
        if(!allowed){
            return;
        }
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activity.startActivityForResult(Intent.createChooser(getIntent, "Select Picture"), PICK_IMAGE);
    }

    public void pickSingleImage() {
        if(!allowed) {
            return;
        }
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(getIntent, "Select Picture"), PICK_IMAGE);
    }

    public List<Bitmap> onReceiveResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                    /* Retrieve data from intent */
                ClipData clipData = data.getClipData();
                List<Uri> uriList = new ArrayList<>();
                if(clipData == null) {
                    uriList.add(data.getData());
                } else {
                    for(int i=0; i<clipData.getItemCount(); i++){
                        ClipData.Item item = clipData.getItemAt(i);
                        uriList.add(item.getUri());
                    }
                }

                    /* Get bitmap from uri */
                for (Uri uri: uriList) {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                    imageList.add(imageBitmap);
                }

                return imageList;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

}
