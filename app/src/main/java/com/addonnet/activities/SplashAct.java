package com.addonnet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.addonnet.R;
import com.addonnet.constants.AppConstants;
import com.addonnet.utils.PreferenceHandler;


public class SplashAct extends Activity {
    int mDelay = 0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(mDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    boolean isLoggedIn = PreferenceHandler.readBoolean(SplashAct.this, AppConstants.sKeyIsLoggedIn, false);
                    if (isLoggedIn) {
                        startActivity(new Intent(SplashAct.this, MainAct.class));
                    } else {
                        startActivity(new Intent(SplashAct.this, OptionActivity.class));
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
        finish();
    }

}
