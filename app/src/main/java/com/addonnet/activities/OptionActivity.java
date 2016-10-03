package com.addonnet.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.addonnet.R;

/**
 * Created by PravinK on 29-09-2016.
 */
public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private TextView mTvSignUp, mTvLogin;
    public static Activity mOptionActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        bindControls();
        setListeners();

    }

    private void bindControls() {
        mContext = this;
        mOptionActivity = OptionActivity.this;
        mTvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
    }

    private void setListeners() {
        mTvSignUp.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                startActivity(new Intent(this, SignUpAct.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(this, LoginAct.class));
                break;
        }
    }
}
