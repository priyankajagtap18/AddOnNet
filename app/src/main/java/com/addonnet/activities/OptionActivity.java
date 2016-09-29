package com.addonnet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.addonnet.R;

/**
 * Created by PravinK on 29-09-2016.
 */
public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView mIvSignUp, mIvLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        bindControls();
        setListeners();

    }

    private void bindControls() {
        mContext = this;
        mIvSignUp = (ImageView) findViewById(R.id.iv_sign_up);
        mIvLogin = (ImageView) findViewById(R.id.iv_login);
    }

    private void setListeners() {
        mIvSignUp.setOnClickListener(this);
        mIvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sign_up:
                startActivity(new Intent(this, SignUpAct.class));
                break;
            case R.id.iv_login:
                startActivity(new Intent(this, LoginAct.class));
                break;
        }
    }
}
