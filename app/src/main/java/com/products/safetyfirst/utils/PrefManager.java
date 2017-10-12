package com.products.safetyfirst.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rahul on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_HOME_LAUNCH = "IsFirstHomeLaunch";
    private static final String IS_FIRST_NEWSDETAIL_LAUCH = "IsFirstNewsDetail";
    private static final String IS_FIRST_EVENTSDETAIL_LAUCH = "IsFirstEventsDetail";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstHomeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_HOME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstHomeLaunch() {
        return pref.getBoolean(IS_FIRST_HOME_LAUNCH, true);
    }

    public void setFirstNewsLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_NEWSDETAIL_LAUCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstNewsLaunch() {
        return pref.getBoolean(IS_FIRST_NEWSDETAIL_LAUCH, true);
    }

    public void setFirstEventsLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_EVENTSDETAIL_LAUCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstEventsLaunch() {
        return pref.getBoolean(IS_FIRST_EVENTSDETAIL_LAUCH, true);
    }

}
