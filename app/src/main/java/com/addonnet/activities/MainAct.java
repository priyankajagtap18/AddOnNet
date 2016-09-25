package com.addonnet.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.adapters.CategoryNameAdapter;
import com.addonnet.constants.AppConstants;
import com.addonnet.entities.CategoryData;
import com.addonnet.fragments.CategoryProductFragment;
import com.addonnet.fragments.EnquiryFragment;
import com.addonnet.fragments.ItemDetailFragment;
import com.addonnet.interfaces.AdapterResponseInterface;
import com.addonnet.sync.SyncListener;
import com.addonnet.sync.SyncManager;
import com.addonnet.utils.PreferenceHandler;
import com.addonnet.utils.UIUtils;
import com.addonnet.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterResponseInterface {

    private Utilities mUtilities;
    private SyncManager syncManager;
    private SyncListener syncListener;
    private DrawerLayout mDrawerLayout;
    public static Toolbar mToolbar;
    private NavigationView mNavigationView;
    private Context mContext;
    public static TextView mTvTitle;
    private List<CategoryData> mAListCategory;
    private CategoryNameAdapter adapter;
    private RecyclerView mRvCategory;
    private TextView mTvLogout, mTvMap;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindControls();
        initSyncListener();
        //  getCategories();
        setUpNavigationView();
        setListeners();
        setAdapter();
    }

    private void bindControls() {
        mContext = this;
        mUtilities = new Utilities(mContext);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mRvCategory = (RecyclerView) mNavigationView.findViewById(R.id.rv_category);
        mTvLogout = (TextView) mNavigationView.findViewById(R.id.tv_logout);
        mTvMap = (TextView) mNavigationView.findViewById(R.id.tv_map);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) mToolbar.findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mUtilities.replaceFragment(this, new CategoryProductFragment(), R.string.category);

    }

    private void getCategories() {
        if (mUtilities.isOnline()) {
            mAListCategory = new ArrayList<>();
            mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
            syncManager = new SyncManager(this, SyncManager.GET_CATEGORY, syncListener);
        } else {
            mUtilities.hideProgressDialog();
            UIUtils.showToast(this, getString(R.string.network_error_msg));
        }
    }

    private void initSyncListener() {
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                mUtilities.hideProgressDialog();
                switch (taskId) {
                    case SyncManager.GET_CATEGORY:
                        if (arrResult != null && arrResult.size() > 0) {
                            setAdapter();
                        } else {
                            onSyncFailure(taskId, getString(R.string.server_error));
                        }
                        break;
                }
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                mUtilities.hideProgressDialog();
                UIUtils.showToast(MainAct.this, getString(R.string.server_error));
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }

        };
    }

    private void setAdapter() {
        if (mAListCategory != null && mAListCategory.size() > 0) {
            adapter = new CategoryNameAdapter(mContext, mAListCategory, MainAct.this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRvCategory.setLayoutManager(layoutManager);
            mRvCategory.setAdapter(adapter);
        }
    }

    private void setListeners() {
        mTvLogout.setOnClickListener(this);
        mTvMap.setOnClickListener(this);
    }

    /**
     * Initilize navigation view
     */
    private void setUpNavigationView() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogoutDialog() {

        try {
            final Dialog mLogoutDialog = new Dialog(this);
            mLogoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mLogoutDialog.setContentView(R.layout.dialog_logout);
            mLogoutDialog.setCanceledOnTouchOutside(false);
            TextView mTvOk, mTvCancel;
            mTvOk = (TextView) mLogoutDialog.findViewById(R.id.tv_ok);
            mTvCancel = (TextView) mLogoutDialog.findViewById(R.id.tv_cancel);
            mTvCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mLogoutDialog.dismiss();
                }
            });
            mTvOk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mLogoutDialog.dismiss();
                    PreferenceHandler.writeBoolean(mContext, AppConstants.sKeyIsLoggedIn, false);
                    finish();
                }
            });

            mLogoutDialog.show();
            mUtilities.setLayoutParamForDialog(this, mLogoutDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment myFragment = getSupportFragmentManager().findFragmentByTag(AppConstants.sTagFragment);
            if (myFragment instanceof CategoryProductFragment) {
                handleBackPress();
            }
            if (myFragment instanceof EnquiryFragment) {
                mUtilities.replaceFragment(this, new CategoryProductFragment(), R.string.category);
            }
            if (myFragment instanceof ItemDetailFragment) {

                mUtilities.replaceFragment(this, new CategoryProductFragment(), R.string.category);
            }

        }
    }

    private void handleBackPress() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        UIUtils.showToast(mContext, getString(R.string.msg_exit_app));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        closeDrawer();
        switch (v.getId()) {
            case R.id.tv_logout:
                showLogoutDialog();
                break;
            case R.id.tv_map:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    private void closeDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void getAdapterResponse(Bundle bundle) {
        closeDrawer();
    }
}
