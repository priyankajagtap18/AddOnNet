package com.addonnet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.addonnet.R;

/**
 * Created by pita on 9/25/2016.
 */
public class SignUpAct extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView mTvSignUp, mTvLogin;
    private EditText mEtName, mEtEmail, mEtMobileNo, mEtPwd, mEtConfirmPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        bindControls();
        setListeners();
    }

    private void bindControls() {
        mContext = this;
        mTvSignUp = (TextView) findViewById(R.id.tv_signup);
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
            case R.id.tv_signup:
                startActivity(new Intent(SignUpAct.this, MainAct.class));
                break;
            case R.id.tv_login:
                finish();
                break;
        }
    }
}
