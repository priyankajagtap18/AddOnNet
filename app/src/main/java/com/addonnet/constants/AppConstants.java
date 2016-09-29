package com.addonnet.constants;

import android.content.Context;

import com.addonnet.entities.UserDetail;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class AppConstants
{
    public static String sTagFragment;
    public static String sKeyUserName = "username";
    public static String sKeyPassword = "password";

    private Context context;
    private static AppConstants sDataControllerInstance;
    public static String sKeyIsLoggedIn = "isLoggedIn";
    public static final String KEY_CATEGORY_ID = "KEY_CATEGORY_ID";
    public static final String KEY_CATEGORY_NAME = "KEY_CATEGORY_NAME";
    public static final String KEY_PRODUCT_DETAILS = "KEY_PRODUCT_DETAILS";
    public static String CAT_ID;

    public static UserDetail userDetail;


    public static synchronized AppConstants getInstance() {
        if (sDataControllerInstance == null) {
            sDataControllerInstance = new AppConstants();
        }
        return sDataControllerInstance;
    }

    public Context getContext() {
        return context;
    }


    public void setContext(Context context) {
        this.context = context;
    }
}
