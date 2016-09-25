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
import com.addonnet.utils.Utilities;

/**
 * Created by pita on 9/25/2016.
 */
public class EnquiryFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private Utilities mUtilities;
    private Context mContext;
    private TextView mTvEnquiry;
    private EditText mEtName, mEtEmail, mEtMobileNo, mEtAddress, mEtCompany;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_enquiry, container, false);
        bindControls();
        setListeners();
        return mRootView;
    }

    private void bindControls() {
        mContext = getActivity();
        mUtilities = new Utilities(mContext);
        mTvEnquiry = (TextView) mRootView.findViewById(R.id.tv_enquiry);
        mEtName = (EditText) mRootView.findViewById(R.id.et_full_name);
        mEtEmail = (EditText) mRootView.findViewById(R.id.et_email);
        mEtMobileNo = (EditText) mRootView.findViewById(R.id.et_mobile_no);
        mEtAddress = (EditText) mRootView.findViewById(R.id.et_address);
        mEtCompany = (EditText) mRootView.findViewById(R.id.et_company_name);

    }

    private void setListeners() {
        mTvEnquiry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_enquiry:
                break;

        }
    }
}

