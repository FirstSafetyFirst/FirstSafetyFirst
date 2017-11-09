package com.products.safetyfirst.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by profileconnect on 28/04/17.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    private final int mNumCol;

    public SpacesItemDecoration(int space, int numCol) {
        this.space = space;
        this.mNumCol=numCol;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        //outRect.right = space;
        outRect.bottom = space;
        //outRect.left = space;

        //Log.d("ttt", "item position" + parent.getChildLayoutPosition(view));
        int position=parent.getChildLayoutPosition(view);

        if(mNumCol<=2) {
            if (position == 0) {
                outRect.left = space;
                outRect.right = space / 2;
            } else {
                if ((position % mNumCol) != 0) {
                    outRect.left = space / 2;
                    outRect.right = space;
                } else {
                    outRect.left = space;
                    outRect.right = space / 2;
                }
            }
        }else{
            if (position == 0) {
                outRect.left = space;
                outRect.right = space / 2;
            } else {
                if ((position % mNumCol) == 0) {
                    outRect.left = space;
                    outRect.right = space/2;
                } else if((position % mNumCol) == (mNumCol-1)){
                    outRect.left = space/2;
                    outRect.right = space;
                }else{
                    outRect.left=space/2;
                    outRect.right=space/2;
                }
            }

        }

        if(position<mNumCol){
            outRect.top=space;
        }else{
            outRect.top=0;
        }
        // Add top margin only for the first item to avoid double space between items
        /*
        if (parent.getChildLayoutPosition(view) == 0 ) {

        } else {
            outRect.top = 0;
        }*/
    }
}


