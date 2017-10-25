package com.products.safetyfirst.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by ishita sharma on 10/19/2017.
 */

public class KnowItItem implements Parcelable{
    private static String info;
    private static String item_name;
    private static String safety_checklist;
    private static String thumb_url;
    private static HashMap<String,KnowItItemType> types;
    public KnowItItem(){

    }
    public KnowItItem(String name,String url,String info,String checklist,HashMap<String,KnowItItemType> list){
        item_name=name;
        thumb_url=url;
        this.info =info;
        safety_checklist=checklist;
        types =list;
    }
    public KnowItItem(Parcel p){
        item_name= p.readString();
        info =p.readString();
        thumb_url= p.readString();
        safety_checklist= p.readString();
        p.readMap(types,KnowItItemType.class.getClassLoader());
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
    public  String getThumb_url(){
        return thumb_url;
    }
    public  String getItem_info(){ return info;}
    public  String getSafety_checklist(){ return safety_checklist;}
    public  HashMap<String,KnowItItemType> getKnow_it_item_types(){ return types;}
    public void setItem_name(String s){ item_name=s;}
    public void setThumb_url(String s){thumb_url=s;}
    public void setItem_info(String s){
        info =s;}
    public void setSafety_checklist(String s){safety_checklist=s;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_name);
        dest.writeString(info);
        dest.writeString(thumb_url);
        dest.writeString(safety_checklist);
        dest.writeMap(types);
    }

    public void setItemTypes(HashMap<String,KnowItItemType> itemTypes) {
        types = itemTypes;
    }
}
