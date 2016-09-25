package com.addonnet.sync;

import android.content.Context;

import com.addonnet.network.DownloadListener;
import com.addonnet.utils.Utilities;

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
    ;


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

  /*  public void postSignIn(String strUsername, String strPassword) {
        AsyncHttpClient client = new AsyncHttpClient();
        ArrayList<String> arrResult = new ArrayList<String>();
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.add(context.getString(R.string.param_login_name), strUsername);
            requestParams.add(context.getString(R.string.param_login_password), strPassword);
            Log.i("=== json body===", " " + requestParams);
            client.post(AppUrls.sSignInURL, requestParams, new DownloadHandler(SIGN_IN, SyncManager.this, arrResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

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