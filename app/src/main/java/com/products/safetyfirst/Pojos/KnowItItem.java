package com.products.safetyfirst.Pojos;

import java.util.HashMap;

/**
 * Created by ishita sharma on 10/19/2017.
 */

public class KnowItItem {
    private String item_info;
    private  String item_name;
    private String safety_checklist;
    private String thumb_url;
    private   HashMap<String,String> types;
    public KnowItItem(){

    }
    public KnowItItem(String name,String url,String info,String checklist, HashMap<String, String> types){
        this.item_name=name;
        this.thumb_url=url;
        this.item_info =info;
        this.safety_checklist=checklist;
        this.types = types;
    }


    public String getItem_name(){
        return item_name;
    }
    public  String getThumb_url(){
        return thumb_url;
    }
    public  String getItem_info(){ return item_info;}
    public  String getSafety_checklist(){ return safety_checklist;}
    public  HashMap<String,String> getTypes(){ return types;}

    public void setItem_name(String s){ this.item_name=s;}

}
