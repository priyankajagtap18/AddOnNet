package com.addonnet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.addonnet.R;


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
                    startActivity(new Intent(SplashAct.this, LoginAct.class));

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
