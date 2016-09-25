package com.addonnet.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.addonnet.R;
import com.addonnet.activities.MainAct;
import com.addonnet.constants.AppConstants;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by PriyankaJ on 26-08-2015.
 */
public class Utilities {
    private Context context;
    private static FragmentTransaction fragmentTransaction;
    private ProgressDialog pgdialog;

    public Utilities(Context context) {
        this.context = context;
    }

    public void replaceFragment(final FragmentActivity mActivity, final Fragment mFragment, int resID) {
        try {
            if (mActivity != null) {
                String strTitle = mActivity.getString(resID);
                AppConstants.sTagFragment = strTitle;
                fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_rigth, R.anim.exit_to_left, R.anim.pop_enter, R.anim.pop_exit);
                if (MainAct.mTvTitle != null)
                    MainAct.mTvTitle.setText(strTitle);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentTransaction.replace(R.id.fl_container, mFragment, AppConstants.sTagFragment).commit();
                    }
                }, 100);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(String message) {
        if (pgdialog != null)
            if (pgdialog.isShowing()) {
                pgdialog.dismiss();
                pgdialog.cancel();
            }
        pgdialog = ProgressDialog.show(context, context.getString(R.string.app_name), message, true);

    }

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

    public boolean isOnline() {
        ConnectivityManager conMgr = null;
        conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getPercentDiscount(String promotion_price) {
        int distCountPrice = 0;
        try {
            float discountPercent = Float.parseFloat(promotion_price)
                    / Float.parseFloat(promotion_price) * 100;
            distCountPrice = (int) (100 - discountPercent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return distCountPrice;
    }

    public static void setImage(Context context, String imageURL, ImageView img) {
        try {
            Picasso.with(context)
                    .load(imageURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context mContext, String strMessage) {
        Toast toast;
        toast = Toast.makeText(mContext.getApplicationContext(), strMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    // hide soft keyboard
    public void hideKeyboard(View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(),
                0);
    }

    public static boolean isEditTextEmpty(EditText mEditText) {
        if (mEditText == null || mEditText.getText().toString().trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Use for validating inserted email_id whether it is right or not.
     *
     * @param email
     * @return
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void setLayoutParamForDialog(Activity activity, Dialog dialog) {
        Display display;
        display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public static String getKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            String packageName = context.getApplicationContext().getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
            Log.e("Package Name=", context.getApplicationContext().getPackageName());
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                Log.e("KeyHash=", key);
            }
        }catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

}
