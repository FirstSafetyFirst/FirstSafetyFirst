package com.products.safetyfirst.androidhelpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * Created by rishabh on 18/10/17.
 */

public class ImageHelper {

    private static ImageHelper instance = new ImageHelper();

    public static ImageHelper getInstance() {
        return instance;
    }

    public Bitmap downScale(Bitmap bitmap) {
//        int width = bitmap.getWidth(), height = bitmap.getHeight();
//        Bitmap newImg = bitmap;
//        if(width < height && width > 768 && height > 1024) {
//            float ratio = width / 768f;
//            height = Math.round(height / ratio);
//            newImg = Bitmap.createScaledBitmap(bitmap, 768, height, false);
//        } else if (width > height && width > 1024 && height > 768) {
//            float ratio = width / 1024f;
//            height = Math.round(height / ratio);
//            newImg = Bitmap.createScaledBitmap(bitmap, 1024, height, false);
//        }
//        Log.e("Image", bitmap.getByteCount() + " : " + newImg.getByteCount());
//        return newImg;
        Bitmap newImg;
        int size = bitmap.getByteCount();
        int maxSize = 50000;
        if(size > maxSize) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            float scale = (float)maxSize / (float)size;
            Log.e("Image", "" + scale);
            bitmap.compress(Bitmap.CompressFormat.JPEG, Math.round(scale*100), outStream);
            byte[] bitmapData = outStream.toByteArray();
            newImg = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
            Log.e("Image", bitmap.getRowBytes()*bitmap.getHeight() + " : " + newImg.getRowBytes()*newImg.getHeight());
        } else {
            newImg = bitmap;
        }
        return newImg;
    }

}
