package com.products.safetyfirst.models;

import java.net.URL;

/**
 * Created by ishita sharma on 10/19/2017.
 */

public class KnowItItemType {

    private static String item_type_name,item_info,how_to_use;
    private static URL item_thumb_url,checkList,video_url;

    public KnowItItemType(){}
    public KnowItItemType(String item_type,String info, String use,URL thumb_url,URL checklist, URL videoUrl ){
        item_type_name=item_type;
        item_info=info;
        how_to_use=use;
        item_thumb_url=thumb_url;
        checkList=checklist;
        video_url=videoUrl;
    }
    public static String getItem_type_name(){ return item_type_name;}

    public static String getItem_info(){ return item_info;}

    public static String getHow_to_use(){ return how_to_use;}

    public static URL getItem_thumb_url(){ return item_thumb_url;}

    public static URL getCheckList(){ return checkList;}

    public static URL getVideo_url(){ return video_url;}
}

