package com.addonnet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.entities.EnquiryAddUpd;
import com.addonnet.sync.SyncListener;
import com.addonnet.sync.SyncManager;
import com.addonnet.utils.UIUtils;
import com.addonnet.utils.Utilities;

import java.util.ArrayList;

/**
 * Created by pita on 9/25/2016.
 */
public class EnquiryFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private Utilities mUtilities;
    private Context mContext;
    private EditText mEtName, mEtEmail, mEtMobileNo, mEtAddress, mEtCompany, mEtDescription;
    private TextView mTvSubmit;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private String strName, strEmail, strMobileNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_enquiry, container, false);
        bindControls();
        setListeners();
        initSyncListener();
        return mRootView;
    }

    private void initSyncListener() {
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                mUtilities.hideProgressDialog();
                switch (taskId) {
                    case SyncManager.ENQUIRY:
                        if (arrResult != null && arrResult.size() > 0) {
                            if (((ArrayList<EnquiryAddUpd>) arrResult).get(0).getStatus().equalsIgnoreCase("true")) {
                                UIUtils.showToast(getActivity(), getString(R.string.enquiry_sent));
                                mUtilities.replaceFragment(getActivity(), new CategoryProductFragment(), R.string.category);
                            } else {
                                UIUtils.showToast(getActivity(), getString(R.string.enquiry_failed));
                                mUtilities.replaceFragment(getActivity(), new CategoryProductFragment(), R.string.category);
                            }
                        } else {
                            onSyncFailure(taskId, getString(R.string.server_error));
                        }
                        break;
                }
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                mUtilities.hideProgressDialog();
                UIUtils.showToast(getActivity(), getString(R.string.server_error));
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }

        };
    }

    private void bindControls() {
        mContext = getActivity();
        mUtilities = new Utilities(mContext);
        mTvSubmit = (TextView) mRootView.findViewById(R.id.tv_submit);
        mEtName = (EditText) mRootView.findViewById(R.id.et_full_name);
        mEtEmail = (EditText) mRootView.findViewById(R.id.et_email);
        mEtMobileNo = (EditText) mRootView.findViewById(R.id.et_mobile_no);
        mEtAddress = (EditText) mRootView.findViewById(R.id.et_address);
        mEtCompany = (EditText) mRootView.findViewById(R.id.et_company_name);
        mEtDescription = (EditText) mRootView.findViewById(R.id.et_description);

    }

    private void setListeners() {
        mTvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (validateFields()) {
                    sendEnquiry();
                }
                break;

        }
    }
    private boolean validateFields() {
        boolean isValid;
        strName = mEtName.getText().toString().trim();
        strEmail = mEtEmail.getText().toString().trim();
        strMobileNo = mEtMobileNo.getText().toString().trim();

        if (Utilities.isEditTextEmpty(mEtName)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_firstname));
        } else if (Utilities.isEditTextEmpty(mEtEmail)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_email));
        } else if (Utilities.isEditTextEmpty(mEtMobileNo)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_empty_phone_no));
        } else if (!Utilities.isEmailValid(strEmail)) {
            isValid = false;
            UIUtils.showToast(mContext, getString(R.string.msg_email_error));
        } else {
            isValid = true;
        }
        return isValid;
    }
    private void sendEnquiry() {
        if (mUtilities.isOnline()) {
            mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
            syncManager = new SyncManager(getActivity(), SyncManager.ENQUIRY, syncListener);
            syncManager.sendEnquiry(mEtName.getText().toString().trim(), mEtEmail.getText().toString().trim(), mEtMobileNo.getText().toString().trim(), mEtAddress.getText().toString().trim(), mEtCompany.getText().toString().trim(), mEtDescription.getText().toString().trim());
        } else {
            mUtilities.hideProgressDialog();
            UIUtils.showToast(getActivity(), getString(R.string.network_error_msg));
        }
    }
}

