package com.products.safetyfirst.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by ishita sharma on 10/19/2017.
 */

public class KnowItItem implements Parcelable{
    private static String item_name;
    private static URL thumb_url;
    private static String item_info;
    private static URL safety_checklist;
    private static HashMap<String,KnowItItemType> knowItItemTypeMap;
    public KnowItItem(){

    }
    public KnowItItem(String name, URL url,String info,URL checklist,HashMap<String,KnowItItemType> list){
        item_name=name;
        thumb_url=url;
        item_info=info;
        safety_checklist=checklist;
        knowItItemTypeMap=list;
    }
    public KnowItItem(Parcel p){
        item_name= p.readString();
        item_info=p.readString();
        try {
            thumb_url= new URL(p.readString());
            safety_checklist= new URL(p.readString());
        } catch (MalformedURLException e) {
            Log.e("KnowItItem","MalformedURLException");
        }
        p.readMap(knowItItemTypeMap,KnowItItemType.class.getClassLoader());
    }
    public static final Creator<KnowItItem> CREATOR = new Creator<KnowItItem>() {
        @Override
        public KnowItItem createFromParcel(Parcel in) {
            return new KnowItItem(in);
        }

        @Override
        public KnowItItem[] newArray(int size) {
            return new KnowItItem[size];
        }
    };

    public String getItem_name(){
        return item_name;
    }
    public  URL getThumb_url(){
        return thumb_url;
    }
    public  String getItem_info(){ return item_info;}
    public  URL getSafety_checklist(){ return safety_checklist;}
    public  HashMap<String,KnowItItemType> getKnow_it_item_types(){ return knowItItemTypeMap;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_name);
        dest.writeString(item_info);
        dest.writeString(thumb_url.toString());
        dest.writeString(safety_checklist.toString());
        dest.writeMap(knowItItemTypeMap);
    }
}
