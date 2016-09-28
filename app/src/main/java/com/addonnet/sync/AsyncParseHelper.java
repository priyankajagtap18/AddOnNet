package com.addonnet.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.addonnet.entities.Categories;
import com.addonnet.entities.CategoryData;
import com.addonnet.entities.EnquiryAddUpd;
import com.addonnet.entities.EnquiryWrapper;
import com.addonnet.entities.ProductWrapper;
import com.addonnet.entities.Products;
import com.addonnet.entities.Registration;
import com.addonnet.entities.RegistrationWrapper;
import com.addonnet.entities.UserDetail;
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
                    ArrayList<UserDetail> arrayList = new ArrayList<>();
                    UserDetailsWrapper wrapper = new Gson().fromJson(response.toString(), UserDetailsWrapper.class);
                    arrayList.addAll(wrapper.getUserDetails());
                    arrResult = arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SyncManager.SIGN_UP:
                try {
                    ArrayList<Registration> arrayList = new ArrayList<>();
                    RegistrationWrapper wrapper = new Gson().fromJson(response.toString(), RegistrationWrapper.class);
                    arrayList.addAll(wrapper.getRegistration());
                    arrResult = arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SyncManager.GET_CATEGORY:
                try {
                    ArrayList<Categories> arrayList = new ArrayList<>();
                    CategoryData categories = new Gson().fromJson(response.toString(), CategoryData.class);
                    arrayList.addAll(categories.getCategories());
                    arrResult = arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SyncManager.GET_PRODUCT:
                try {
                    ArrayList<Products> arrayList = new ArrayList<>();
                    ProductWrapper productWrapper = new Gson().fromJson(response.toString(), ProductWrapper.class);
                    arrayList.addAll(productWrapper.getProducts());
                    arrResult = arrayList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SyncManager.ENQUIRY:
                try {
                    ArrayList<EnquiryAddUpd> arrayList = new ArrayList<>();
                    EnquiryWrapper enquiryWrapper = new Gson().fromJson(response.toString(), EnquiryWrapper.class);
                    arrayList.addAll(enquiryWrapper.getEnquiryAddUpd());
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
