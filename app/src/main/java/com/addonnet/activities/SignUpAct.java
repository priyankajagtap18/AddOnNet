package com.addonnet.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.entities.Registration;
import com.addonnet.sync.SyncListener;
import com.addonnet.sync.SyncManager;
import com.addonnet.utils.UIUtils;
import com.addonnet.utils.Utilities;

import java.util.ArrayList;

/**
 * Created by pita on 9/25/2016.
 */
public class SignUpAct extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView mTvLogin, mTvSignUp;
    private EditText mEtName, mEtEmail, mEtMobileNo, mEtPwd, mEtConfirmPwd;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private Utilities mUtilities;
    private ArrayList<Registration> mArrLRegister;
    private String strName, strEmail, strMobileNo, strPwd, strConfirmPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        bindControls();
        setListeners();
        initSyncListener();
    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        mTvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mEtName = (EditText) findViewById(R.id.et_full_name);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtMobileNo = (EditText) findViewById(R.id.et_mobile_no);
        mEtPwd = (EditText) findViewById(R.id.et_password);
        mEtConfirmPwd = (EditText) findViewById(R.id.et_confirm_password);
    }

    private void setListeners() {
        mTvSignUp.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                if (validateFields()) {
                    doSignUp();
                }
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }

    private void initSyncListener() {
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                mUtilities.hideProgressDialog();
                switch (taskId) {
                    case SyncManager.SIGN_UP:
                        if (arrResult != null && arrResult.size() > 0) {
                            mArrLRegister = (ArrayList<Registration>) arrResult;
                            showStatus(mArrLRegister.get(0));
                        } else {
                            onSyncFailure(taskId, getString(R.string.server_error));
                        }
                        break;
                }
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                mUtilities.hideProgressDialog();
                UIUtils.showToast(SignUpAct.this, getString(R.string.server_error));
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }
        };
    }

    private void showStatus(Registration registration) {
        if (registration.getStatus().equalsIgnoreCase("true")) {
            UIUtils.showToast(this, getString(R.string.registration_success));
            finish();
        } else {
            UIUtils.showToast(this, getString(R.string.msg_already_register));
        }
    }

    private boolean validateFields() {
        boolean isValid;
        strName = mEtName.getText().toString().trim();
        strPwd = mEtPwd.getText().toString().trim();
        strConfirmPwd = mEtConfirmPwd.getText().toString().trim();
        strEmail = mEtEmail.getText().toString().trim();
        strMobileNo = mEtMobileNo.getText().toString().trim();

        if (Utilities.isEditTextEmpty(mEtName)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_firstname));
        } else if (Utilities.isEditTextEmpty(mEtEmail)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_email));
        } else if (Utilities.isEditTextEmpty(mEtPwd)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_password));
        } else if (Utilities.isEditTextEmpty(mEtConfirmPwd)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_confirm_password));
        } else if (Utilities.isEditTextEmpty(mEtMobileNo)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_phone_no));
        } else if (!Utilities.isEmailValid(strEmail)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_email_error));
        } else if (!(strPwd.equals(strConfirmPwd))) {
            UIUtils.showToast(mContext, getString(R.string.msg_password_mistmach_error));
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    private void doSignUp() {
        if (mUtilities.isOnline()) {
            mUtilities.hideKeyboard(mEtEmail);
            mArrLRegister = new ArrayList<>();
            mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
            syncManager = new SyncManager(this, SyncManager.SIGN_UP, syncListener);
            syncManager.postRegistration(strName, strEmail, strPwd, strMobileNo);
        } else {
            mUtilities.hideProgressDialog();
            UIUtils.showToast(this, getString(R.string.network_error_msg));
        }
    }
}
