package com.products.safetyfirst.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ishita sharma on 10/19/2017.
 */

public class KnowItItemType implements Parcelable{

    private  String item_info,how_to_use,item_thumb_url, checklist,video_url;

    public KnowItItemType(){}

    public KnowItItemType(String info, String use,String thumb_url,String checklist, String videoUrl ){
        //item_type_name=item_type;
        item_info=info;
        how_to_use=use;
        item_thumb_url=thumb_url;
        this.checklist =checklist;
        video_url=videoUrl;
    }
    //public static String getItem_type_name(){ return item_type_name;}

    public KnowItItemType(Parcel in) {
        item_info = in.readString();
        how_to_use = in.readString();
        item_thumb_url = in.readString();
        checklist = in.readString();
        video_url = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_info);
        dest.writeString(how_to_use);
        dest.writeString(item_thumb_url);
        dest.writeString(checklist);
        dest.writeString(video_url);
    }
    public static final Creator<KnowItItemType> CREATOR = new Creator<KnowItItemType>() {
        @Override
        public KnowItItemType createFromParcel(Parcel in) {
            return new KnowItItemType(in);
        }

        @Override
        public KnowItItemType[] newArray(int size) {
            return new KnowItItemType[size];
        }
    };

    public String getItem_info(){ return item_info;}

    public String getHow_to_use(){ return how_to_use;}

    public String getItem_thumb_url(){ return item_thumb_url;}

    public String getChecklist(){ return checklist;}

    public String getVideo_url(){ return video_url;}

    @Override
    public int describeContents() {
        return 0;
    }

}
