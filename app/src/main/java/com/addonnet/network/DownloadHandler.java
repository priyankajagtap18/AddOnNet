package com.addonnet.network;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class DownloadHandler extends AsyncHttpResponseHandler {

    DownloadListener listener;
    int taskId;

    public DownloadHandler(int taskId, DownloadListener listener, ArrayList<?> arrResult) {
        this.listener = listener;
        this.taskId = taskId;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        System.out.println("====DownloadHandler.onSuccess() statusCode = [" + statusCode + "], headers = [" + headers + "], Response = [" +
                new String(responseBody) + "], taskId = [" + taskId + "]");
        listener.onDownloadSuccess(taskId, new String(responseBody));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        if (responseBody != null) {
            listener.onDownloadFailure(taskId, new String(responseBody));
            System.out.println("====DownloadHandler.onFailure() statusCode = [" + statusCode + "], headers = [" + headers + "], Response = [" + new String(responseBody) +
                    "], error = [" + error + "], taskId = [" + taskId + "]");
        } else {
            listener.onDownloadFailure(taskId, new String());
            System.out.println("====DownloadHandler.onFailure() statusCode = [" + statusCode + "], headers = [" + headers + "], Response = [" +
                    responseBody + "], error = [" + error + "], taskId = [" + taskId + "]");
        }
    }


}
