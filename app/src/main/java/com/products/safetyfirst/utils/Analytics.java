package com.products.safetyfirst.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by ishita sharma on 10/29/2017.
 */

public class Analytics {

    public static void logEventLogin(Context context,int login){
        Bundle params= new Bundle();
        params.putString("login","1");
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.LOGIN,params);
    }
    public static void logEventSearch(Context context,String serach_term){
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SEARCH_TERM,serach_term);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SEARCH,params);
    }
    public static void logEventShare(Context context,String title,String key){
        Bundle bundle= new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,title);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,key);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SHARE,bundle);
    }
    public static void logEventSetSignUp(Context context,String signUpMethod){
        Bundle bundle= new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD,signUpMethod);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SIGN_UP,bundle);
    }
    public static void logEventTutorialBegin(Context context){
        Bundle bundle= new Bundle();
        bundle.putString("Tutorial_begin","1");
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.TUTORIAL_BEGIN,bundle);
    }
    public static void logEventViewItem(Context context,String postkey, String name,String type){
        Bundle bundle= new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,postkey);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,name);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,type);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.VIEW_ITEM,bundle);
    }
}
