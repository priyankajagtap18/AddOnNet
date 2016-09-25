package com.addonnet.constants;

import android.content.Context;

/**
 * Created by PriyankaJ on 09-03-2016.
 */
public class AppConstants {

    public static String sTagFragment;
    public static String sKeyUserName = "username";
    public static String sKeyPassword = "password";
    public static String sKeyPosition = "Position";
    public static String sKeyDeleteProduct = "DeleteProduct";
    public static String sKeyType = "Type";
    public static String sKeyQuantity = "Quantity";
    public static String sKeyArray = "Array";
    public static String sKeyPic = "Pic";
    private Context context;
    private static AppConstants sDataControllerInstance;
    public static String sKeyIsLoggedIn = "isLoggedIn";
    public static String sKeyToken = "token";
    public static String sKeyFirstname = "first_name";
    public static String sKeyLastname = "last_name";
    public static String sKeyAddress = "address";
    public static String sKeyPhone = "phone";
    public static String sKeyBillingCountry = "billing_country";
    public static String sKeyBillingFirstname = "billing_first_name";
    public static String sKeyBillingLastname = "billing_last_name";
    public static String sKeyBillingCompany = "billing_company";
    public static String sKeyBillingAddress1 = "billing_address_1";
    public static String sKeyBillingAddress2 = "billing_address_2";
    public static String sKeyBillingCity = "billing_city";
    public static String sKeyBillingState = "billing_state";
    public static String sKeyBillingEmail = "billing_email";
    public static String sKeyBillingPhone = "billing_phone";
    public static String sKeyBillingPostcode = "billing_postcode";
    public static String sKeyShippingCountry = "shipping_country";
    public static String sKeyShippingFirstname = "shipping_first_name";
    public static String sKeyShippingLastname = "shipping_last_name";
    public static String sKeyShippingCompany = "shipping_company";
    public static String sKeyShippingAddress1 = "shipping_address_1";
    public static String sKeyShippingAddress2 = "shipping_address_2";
    public static String sKeyShippingCity = "shipping_city";
    public static String sKeyShippingState = "shipping_state";
    public static String sKeyShippingEmail = "shipping_email";
    public static String sKeyShippingPhone = "shipping_phone";
    public static String sKeyShippingPostcode = "shipping_postcode";
    public static String sKeyEmail = "email";
    public static String KEY_PRODUCT = "KEY_PRODUCT";
    public static String KEY_CALCULATE_CART = "KEY_CALCULATE_CART";
    public static String KEY_PLACE_ORDER="KEY_PLACE_ORDER";
    public static String KEY_ORDER_SUMMARY = "KEY_ORDER_SUMMARY";
    public static String KEY_CATEGORY = "KEY_CATEGORY";
    public static boolean isFromKeepFragment = false;

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
