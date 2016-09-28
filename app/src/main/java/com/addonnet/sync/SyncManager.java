package com.addonnet.sync;

import android.content.Context;
import android.util.Log;

import com.addonnet.constants.AppUrls;
import com.addonnet.entities.RegistrationWrapper;
import com.addonnet.entities.UserDetailsWrapper;
import com.addonnet.network.DownloadHandler;
import com.addonnet.network.DownloadListener;
import com.addonnet.utils.Utilities;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 01-09-2015.
 */
public class SyncManager implements DownloadListener, ParseListener {

    private Context context;
    private SyncListener listener;
    private int type;
    private Utilities utilities;
    public static final int LOGIN = 1, SIGN_UP = 2, FORGOT_PASSWORD = 3, GET_CATEGORY = 4, GET_ITEM_DETAIL = 5, ENQUIRY = 6;


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

    public void postAuthentication(String strEmail, String strPassword) {
        AsyncHttpClient client = new AsyncHttpClient();
        ArrayList<UserDetailsWrapper> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.add("Email", strEmail);
            requestParams.add("Password", strPassword);
            Log.i("=== json body===", " " + requestParams);
            client.post(AppUrls.spAuthentication, requestParams, new DownloadHandler(LOGIN, SyncManager.this, arrResult));
//            client.get("http://www.webdreamworksindia.in/addonsystem/GetCategoryLst.aspx?CategoryId=0&StatusId=0", null, new DownloadHandler(LOGIN, SyncManager.this, arrResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void postRegistration(String strName, String strEmail, String strPassword, String strMobile) {
        AsyncHttpClient client = new AsyncHttpClient();
        ArrayList<RegistrationWrapper> arrResult = new ArrayList<>();
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.add("UserId", "0");
            requestParams.add("Name", strPassword);
            requestParams.add("Email", strPassword);
            requestParams.add("Password", strPassword);
            requestParams.add("Mobile", strPassword);
            requestParams.add("StatusId", "1");
            requestParams.add("UpdateType", "");
            Log.i("=== json body===", " " + requestParams);
            client.post(AppUrls.spRegistration, requestParams, new DownloadHandler(SIGN_UP, SyncManager.this, arrResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public void getTrend() {
        ArrayList<Trend> arrResult = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(120000);
        RequestParams requestParams = new RequestParams();
        String sUrl = AppUrls.sGetTrend;
        Log.e("URL: ", "" + sUrl);
        client.get(sUrl, null, new DownloadHandler(GET_TREND, SyncManager.this, arrResult));
    }*/

}