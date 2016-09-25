package com.addonnet.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.utils.Utilities;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;

import java.util.List;

/**
 * Created by pita on 9/25/2016.
 */
public class LoginAct extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;
    private TextView mTvLogin, mTvForgetPwd;
    private LinearLayout mLlFbLogin, mLlGoogleLogin;
    private SimpleFacebook mSimpleFacebook;
    private SimpleFacebookConfiguration configuration;
    private Permission[] permissions;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    public static Activity mLoginActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bindControls();
        setListeners();
    }

    private void bindControls() {
        mContext = this;
        mLoginActivity = LoginAct.this;
        FacebookSdk.sdkInitialize(mContext);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mLlFbLogin = (LinearLayout) findViewById(R.id.ll_fb_login);
        mLlGoogleLogin = (LinearLayout) findViewById(R.id.ll_google_login);
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        permissions = new Permission[]{Permission.EMAIL, Permission.PUBLISH_ACTION};
        configuration = new SimpleFacebookConfiguration.Builder().setAppId(getString(R.string.app_id)).setPermissions(permissions).build();
        SimpleFacebook.setConfiguration(configuration);
        //Utilities.getKeyHash(this);
        initGooglePlus();
    }

    private void setListeners() {
        mTvLogin.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
        mLlFbLogin.setOnClickListener(this);
        mLlGoogleLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(LoginAct.this, SignUpAct.class));
                break;
            case R.id.ll_fb_login:
                doFacebookLogin();
                break;
            case R.id.ll_google_login:
                doGooglePlusLogin();
                break;
        }
    }

    private void initGooglePlus() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginAct.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void doFacebookLogin() {
        try {
            if (!mSimpleFacebook.isLogin()) {
                mSimpleFacebook.login(new OnLoginListener() {
                    @Override
                    public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                        getFacebookProfile();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }

                    @Override
                    public void onFail(String reason) {
                    }
                });
            } else {
                getFacebookProfile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch user details from fb account
     */
    private void getFacebookProfile() {
        try {
            Profile.Properties properties = new Profile.Properties.Builder().add(Profile.Properties.FIRST_NAME)
                    .add(Profile.Properties.EMAIL).add(Profile.Properties.LOCATION).build();
            mSimpleFacebook.getProfile(properties, new OnProfileListener() {
                @Override
                public void onComplete(Profile profile) {
                    startActivity(new Intent(LoginAct.this, SignUpAct.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                getGooglePlusProfile(result);
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_enter);
    }

    private void getGooglePlusProfile(GoogleSignInResult result) {
        Log.d("googleplus", "getGooglePlusProfile:" + result.getStatus());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = result.getSignInAccount();
            startActivity(new Intent(LoginAct.this, SignUpAct.class));
            System.out.println(account.getDisplayName() + "\t" + account.getEmail() + "\t" + account.getGivenName());
        } else {
            Utilities.showToast(mContext, getString(R.string.server_error));
        }
    }

    private void doGooglePlusLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("googleplus", "onConnectionFailed:" + connectionResult);
    }

}
