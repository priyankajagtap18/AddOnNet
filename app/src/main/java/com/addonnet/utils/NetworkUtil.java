package com.addonnet.utils;

import android.content.Context;
import android.net.ConnectivityManager;


/**
 * Check network connectivity for making online request to server
 */
public class NetworkUtil {
    public static boolean getConnectivityStatus(Context context) {

        ConnectivityManager conMgr;
        conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
