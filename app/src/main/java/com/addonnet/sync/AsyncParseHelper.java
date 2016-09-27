package com.addonnet.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.addonnet.constants.AppUrls;
import com.addonnet.entities.RegistrationWrapper;
import com.addonnet.entities.UserDetailsWrapper;
import com.addonnet.utils.Utilities;
import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * Created by PriyankaJ on 01-09-2015.
 */
public class AsyncParseHelper extends AsyncTask<String, String, ArrayList<?>> {

    private Context context;
    private ParseListener listener;
    private int taskId;
    private ArrayList<?> arrResult;
    private Utilities utilities;

    public AsyncParseHelper(Context context, int taskId, ParseListener listener) {
        this.context = context;
        this.listener = listener;
        this.taskId = taskId;
        utilities = new Utilities(context);

    }

    @Override
    protected ArrayList<?> doInBackground(String... params) {
        String response = params[0];

        switch (taskId) {
            case SyncManager.LOGIN:
                try {
                    ArrayList<RegistrationWrapper> arrayList = new ArrayList<>();
                    response = response.replace(AppUrls.sStartXMLTag, "").replace(AppUrls.sEndXMLTag, "");
                    RegistrationWrapper wrapper = new Gson().fromJson(response.toString(), RegistrationWrapper.class);
                    arrayList.add(wrapper);
                    arrResult = arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SyncManager.SIGN_UP:
                try {
                    ArrayList<UserDetailsWrapper> arrayList = new ArrayList<>();
                    response = response.replace(AppUrls.sStartXMLTag, "").replace(AppUrls.sEndXMLTag, "");
                    UserDetailsWrapper wrapper = new Gson().fromJson(response.toString(), UserDetailsWrapper.class);
                    arrayList.add(wrapper);
                    arrResult = arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }


        return arrResult;
    }

    @Override
    protected void onPostExecute(ArrayList<?> arrResult) {
        super.onPostExecute(arrResult);
        listener.onParseSuccess(taskId, "", arrResult);
    }

}
