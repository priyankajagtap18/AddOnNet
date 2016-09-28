package com.addonnet.sync;

import android.content.Context;
import android.util.Log;

import com.addonnet.constants.AppUrls;
import com.addonnet.network.DownloadHandler;
import com.addonnet.network.DownloadListener;
import com.addonnet.utils.Utilities;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 01-09-2015.
 */
public class SyncManager implements DownloadListener, ParseListener {

    private Context context;
    private SyncListener listener;
    private int type;
    private Utilities utilities;
    public static final int LOGIN = 1, SIGN_UP = 2, FORGOT_PASSWORD = 3, GET_CATEGORY = 4, GET_PRODUCT = 5,GET_ITEM_DETAIL = 6, ENQUIRY = 7;


    public SyncManager(Context context, int type, SyncListener listener) {

        System.out.println("===type = [" + type + "]");
        this.context = context;
        this.listener = listener;
        this.type = type;
        utilities = new Utilities(context);
    }

    @Override
    public void onDownloadSuccess(int taskId, String strResponse) {

        AsyncParseHelper parseHelper = new AsyncParseHelper(context, taskId, SyncManager.this);
        parseHelper.execute(strResponse);
    }

    @Override
    public void onDownloadFailure(int taskId, String strResponse) {
        listener.onSyncFailure(taskId, strResponse);
    }

    @Override
    public void onParseSuccess(int taskId, String strResponse, ArrayList<?> arrResult) {
        listener.onSyncSuccess(taskId, strResponse, arrResult);
    }

    @Override
    public void onParseFailure(String str, Throwable error) {
    }

    public void Authenticate(String strEmail, String strPassword) {
        AsyncHttpClient client = new AsyncHttpClient();
        String sUrl = AppUrls.spAuthentication+"Email="+strEmail+"&Password="+strPassword;
        Log.e("URL: ", "" + sUrl);
        client.post(sUrl, null, new DownloadHandler(LOGIN, SyncManager.this, null));

    }
    public void postRegistration(String strName, String strEmail, String strPassword, String strMobile) {
        AsyncHttpClient client = new AsyncHttpClient();
        String sUrl = AppUrls.spRegistration+"&Name="+strName+"&Email="+strEmail+"&Password="+strPassword+"&Mobile="+strMobile;
        Log.e("URL: ", "" + sUrl);
        client.post(sUrl, null, new DownloadHandler(SIGN_UP, SyncManager.this, null));
    }

    public void getCategories() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(120000);
        String sUrl = AppUrls.spCategories;
        Log.e("URL: ", "" + sUrl);
        client.get(sUrl, null, new DownloadHandler(GET_CATEGORY, SyncManager.this, null));
    }
    public void getProducts(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(120000);
        String sUrl = AppUrls.spProducts+"&CategoryId="+id;
        Log.e("URL: ", "" + sUrl);
        client.get(sUrl, null, new DownloadHandler(GET_PRODUCT, SyncManager.this, null));
    }
    public void sendEnquiry(String fullName,String email,String mobile,String address,String company,String description) {
        AsyncHttpClient client = new AsyncHttpClient();
        String sUrl = AppUrls.spEnquiry+"&FullName="+fullName+"&Email="+email+"&Mobile="+mobile+"&CompanyName="+company+"&Address="+address+"&Description="+description;
        Log.e("URL: ", "" + sUrl);
        client.post(sUrl, null, new DownloadHandler(ENQUIRY, SyncManager.this, null));
    }

}