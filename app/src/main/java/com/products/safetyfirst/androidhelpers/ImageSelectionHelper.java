package com.products.safetyfirst.androidhelpers;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rishabh on 17/10/17.
 *
 * Singleton for selecting images
 */

public class ImageSelectionHelper {

    private static final ImageSelectionHelper instance = new ImageSelectionHelper();

    public static ImageSelectionHelper getInstance() {
        return instance;
    }

    private ImageSelectionHelper() {

    }


    public void pickMultipleImages(Activity userAct, MultipleImageResultCallback callback) {
        int PICK_IMAGE = (new Random()).nextInt(1000) + 100;
        ImageSelectionActivity activity = (ImageSelectionActivity) userAct;

        activity.assignData(PICK_IMAGE, callback);

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activity.startActivityForResult(Intent.createChooser(getIntent, "Select Picture"), PICK_IMAGE);
    }

    private class ImageSelectionActivity extends Activity {

        private int PICK_IMAGE;
        private List<Bitmap> imageList;
        private MultipleImageResultCallback callback;

        void assignData(int PICK_IMAGE, MultipleImageResultCallback callback) {
            this.PICK_IMAGE = PICK_IMAGE;
            this.imageList = new ArrayList<>();
            this.callback = callback;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
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
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        imageList.add(imageBitmap);
                    }

                    callback.onImageResult(imageList);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface MultipleImageResultCallback {
        void onImageResult(List<Bitmap> imageList);
    }

}
