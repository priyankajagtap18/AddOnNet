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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.constants.AppConstants;
import com.addonnet.entities.UserDetail;
import com.addonnet.sync.SyncListener;
import com.addonnet.sync.SyncManager;
import com.addonnet.utils.PreferenceHandler;
import com.addonnet.utils.UIUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pita on 9/25/2016.
 */
public class LoginAct extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Context mContext;
    private TextView mTvForgetPwd, mTvSignUp;
    private ImageView mIvLogin, mIvBtnFBLogin, mIvBtnGLogin;
    private SimpleFacebook mSimpleFacebook;
    private SimpleFacebookConfiguration configuration;
    private Permission[] permissions;
    private static final int RC_SIGN_IN = 12501;//9001;
    private GoogleApiClient mGoogleApiClient;
    public static Activity mLoginActivity;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private Utilities mUtilities;
    private ArrayList<UserDetail> mArrLDetail;
    private String strEmail, strPwd;
    private EditText mEtEmail, mEtPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bindControls();
        setListeners();
        initSyncListener();
    }

    private void bindControls() {
        mContext = this;
        mLoginActivity = LoginAct.this;
        mUtilities = new Utilities(mContext);
        FacebookSdk.sdkInitialize(mContext);
        mIvLogin = (ImageView) findViewById(R.id.iv_login);
        mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mTvSignUp = (TextView) findViewById(R.id.tv_signup);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtPwd = (EditText) findViewById(R.id.et_password);
        mIvBtnFBLogin = (ImageView) findViewById(R.id.iv_btn_fb_login);
        mIvBtnGLogin = (ImageView) findViewById(R.id.iv_btn_g_login);
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        permissions = new Permission[]{Permission.EMAIL, Permission.PUBLISH_ACTION};
        configuration = new SimpleFacebookConfiguration.Builder().setAppId(getString(R.string.app_id)).setPermissions(permissions).build();
        SimpleFacebook.setConfiguration(configuration);
        //Utilities.getKeyHash(this);
        initGooglePlus();
    }

    private void setListeners() {
        mIvLogin.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
        mTvSignUp.setOnClickListener(this);
        mIvBtnFBLogin.setOnClickListener(this);
        mIvBtnGLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login:
                if (validateFields()) {
                    doLogin();
                }
                break;
            case R.id.iv_btn_fb_login:
                doFacebookLogin();
                break;
            case R.id.iv_btn_g_login:
                doGooglePlusLogin();
                break;
            case R.id.tv_signup:
                startActivity(new Intent(LoginAct.this, SignUpAct.class));
                break;
        }
    }

    private void initSyncListener() {
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                mUtilities.hideProgressDialog();
                switch (taskId) {
                    case SyncManager.LOGIN:
                        if (arrResult != null && arrResult.size() > 0) {
                            mArrLDetail = (ArrayList<UserDetail>) arrResult;
                            showStatus(mArrLDetail.get(0));
                        } else {
                            onSyncFailure(taskId, getString(R.string.server_error));
                        }
                        break;
                }
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                mUtilities.hideProgressDialog();
                UIUtils.showToast(LoginAct.this, getString(R.string.server_error));
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
    }

    private void showStatus(UserDetail userDetail) {
        if (userDetail.getStatus().equalsIgnoreCase("true")) {
            UIUtils.showToast(this, getString(R.string.login_success));
            AppConstants.userDetail=new UserDetail();
            AppConstants.userDetail=userDetail;
            PreferenceHandler.writeBoolean(mContext, AppConstants.sKeyIsLoggedIn, true);
            startActivity(new Intent(LoginAct.this, MainAct.class));
            finish();
        } else {
            UIUtils.showToast(this, getString(R.string.login_fail));
        }

    }

    private boolean validateFields() {
        boolean isValid;
        strPwd = mEtPwd.getText().toString().trim();
        strEmail = mEtEmail.getText().toString().trim();

        if (Utilities.isEditTextEmpty(mEtEmail)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_email));
        } else if (Utilities.isEditTextEmpty(mEtPwd)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_password));
        } else if (!Utilities.isEmailValid(strEmail)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_email_error));
        } else {
            isValid = true;
        }
        return isValid;
    }

    private void doLogin() {
        if (mUtilities.isOnline()) {
            mUtilities.hideKeyboard(mEtEmail);
            mArrLDetail = new ArrayList<>();
            mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
            syncManager = new SyncManager(this, SyncManager.LOGIN, syncListener);
            syncManager.Authenticate(strEmail, strPwd);
        } else {
            mUtilities.hideProgressDialog();
            UIUtils.showToast(this, getString(R.string.network_error_msg));
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
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void doFacebookLogin() {
        try {
            mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
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
                    mUtilities.hideProgressDialog();
                    PreferenceHandler.writeBoolean(mContext, AppConstants.sKeyIsLoggedIn, true);
                    startActivity(new Intent(LoginAct.this, MainAct.class));
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getGooglePlusProfile(result);
        }
       /* try {
            mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                getGooglePlusProfile(result);
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_enter);
    }

    private void getGooglePlusProfile(GoogleSignInResult result) {
        Log.d("googleplus", "getGooglePlusProfile:" + result.getStatus());
        mUtilities.hideProgressDialog();
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = result.getSignInAccount();
            System.out.println(account.getDisplayName() + "\t" + account.getEmail() + "\t" + account.getGivenName());
            UserDetail userDetail=new UserDetail();
            userDetail.setName(account.getGivenName());
            userDetail.setEmail(account.getEmail());
            showStatus(userDetail);
        } else {
            Utilities.showToast(mContext, getString(R.string.server_error));
        }
    }

    private void doGooglePlusLogin() {
        mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
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
