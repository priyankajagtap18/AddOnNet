package com.addonnet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.constants.AppConstants;
import com.addonnet.entities.Products;
import com.addonnet.utils.Utilities;

/**
 * Created by pita on 9/25/2016.
 */
public class ItemDetailFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private Utilities mUtilities;
    private Context mContext;
    private ImageView mIvItem;
    private Products products;
    private TextView  mTvBrand, mTvColor, mTvItemWidth, mTvScreenRes, mTvHardDrive, mTvBatteryLife,
            mTvConnectivity, mTvDescription, mTvProductName;
    private ImageView mIvEnquiry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        if (getArguments() != null)
            products = (Products) getArguments().getSerializable(AppConstants.KEY_PRODUCT_DETAILS);
        bindControls();
        setListeners();
        return mRootView;
    }

    public static ItemDetailFragment newInstance(Products products) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        if (products != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.KEY_PRODUCT_DETAILS, products);
            itemDetailFragment.setArguments(bundle);
        }
        return itemDetailFragment;
    }

    private void bindControls() {
        mContext = getActivity();
        mUtilities = new Utilities(mContext);
        mIvEnquiry = (ImageView) mRootView.findViewById(R.id.iv_enquiry);
        mTvBrand = (TextView) mRootView.findViewById(R.id.tv_brand);
        mTvColor = (TextView) mRootView.findViewById(R.id.tv_color);
        mTvItemWidth = (TextView) mRootView.findViewById(R.id.tv_item_width);
        mTvScreenRes = (TextView) mRootView.findViewById(R.id.tv_screen_resolution);
        mTvHardDrive = (TextView) mRootView.findViewById(R.id.tv_hard_drive);
        mTvBatteryLife = (TextView) mRootView.findViewById(R.id.tv_battery_life);
        mTvConnectivity = (TextView) mRootView.findViewById(R.id.tv_connectivity);
        mTvDescription = (TextView) mRootView.findViewById(R.id.tv_description);
        mTvProductName =(TextView)mRootView.findViewById(R.id.tv_product_name);
        mIvItem = (ImageView) mRootView.findViewById(R.id.iv_item);

        Utilities.setImage(mContext, products.getImageUrl(),  mIvItem);
        mTvBrand.setText(products.getBrandName());
        mTvColor.setText(products.getColorName());
        mTvDescription.setText(products.getDescription());
        mTvProductName.setText(products.getProductName());
    }

    private void setListeners() {
        mIvEnquiry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_enquiry:
                mUtilities.replaceFragment(getActivity(), new EnquiryFragment(), R.string.enquiry);
                break;
        }
    }
}
