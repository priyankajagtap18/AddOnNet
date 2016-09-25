package com.addonnet.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.addonnet.R;
import com.addonnet.constants.AppConstants;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class UIUtils {
    private ProgressDialog pgdialog;

    /**
     * Show progress dialog
     *
     * @param message
     */
    public void showProgressDialog(String message) {
        if (pgdialog != null)
            if (pgdialog.isShowing()) {
                pgdialog.dismiss();
                pgdialog.cancel();
            }
        pgdialog = ProgressDialog.show(AppConstants.getInstance().getContext(),
                AppConstants.getInstance().getContext().getString(R.string.app_name), message, true);

    }

    /**
     * Show progress dialog
     *
     * @param mContext
     * @param message
     */
    public void showProgressDialog(Context mContext, String message) {
        if (pgdialog != null)
            if (pgdialog.isShowing()) {
                pgdialog.dismiss();
                pgdialog.cancel();
            }
        pgdialog = ProgressDialog.show(mContext,
                mContext.getString(R.string.app_name), message, true);

    }

    /**
     * Hide progress dialog
     */
    public void hideProgressDialog() {
        try {
            if (pgdialog != null) {
                if (pgdialog.isShowing()) {
                    pgdialog.dismiss();
                    pgdialog.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This function is used to show toast throughout the application
     *
     * @param context
     */
    public static void showToast(Context context, String message) {

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
