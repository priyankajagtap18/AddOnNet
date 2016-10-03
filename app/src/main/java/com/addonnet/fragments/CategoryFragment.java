package com.addonnet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.activities.MainAct;
import com.addonnet.entities.Products;
import com.addonnet.utils.Utilities;

import java.util.ArrayList;

/**
 * Created by PravinK on 03-10-2016.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private Utilities mUtilities;
    private Context mContext;
    private ArrayList<Products> mAListProducts;
    private TextView tv_first,tv_second,tv_third,tv_fourth;
    private ImageView iv_first,iv_second,iv_third,iv_fourth;
    private RelativeLayout rel_first,rel_second,rel_third,rel_fourth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_category, container, false);
        bindControls();
        setListeners();
        return mRootView;
    }

    private void bindControls() {
        mContext = getActivity();
        mUtilities = new Utilities(mContext);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);

        tv_first=(TextView)mRootView.findViewById(R.id.tv_first);
        tv_second=(TextView)mRootView.findViewById(R.id.tv_second);
        tv_third=(TextView)mRootView.findViewById(R.id.tv_third);
        tv_fourth=(TextView)mRootView.findViewById(R.id.tv_fourth);

        iv_first=(ImageView) mRootView.findViewById(R.id.iv_first);
        iv_second=(ImageView) mRootView.findViewById(R.id.iv_second);
        iv_third=(ImageView) mRootView.findViewById(R.id.iv_third);
        iv_fourth=(ImageView) mRootView.findViewById(R.id.iv_fourth);

        rel_first=(RelativeLayout)mRootView.findViewById(R.id.rel_first);
        rel_second=(RelativeLayout)mRootView.findViewById(R.id.rel_second);
        rel_third=(RelativeLayout)mRootView.findViewById(R.id.rel_third);
        rel_fourth=(RelativeLayout)mRootView.findViewById(R.id.rel_fourth);

        tv_first.setText(MainAct.mAListCategory.get(0).getCategoryName());
        tv_second.setText(MainAct.mAListCategory.get(1).getCategoryName());
        tv_third.setText(MainAct.mAListCategory.get(2).getCategoryName());
        tv_fourth.setText(MainAct.mAListCategory.get(3).getCategoryName());

        Utilities.setImage(mContext, MainAct.mAListCategory.get(0).getImageUrl().replace(" ", "%20"), iv_first);
        Utilities.setImage(mContext, MainAct.mAListCategory.get(1).getImageUrl().replace(" ", "%20"), iv_second);
        Utilities.setImage(mContext, MainAct.mAListCategory.get(2).getImageUrl().replace(" ", "%20"), iv_third);
        Utilities.setImage(mContext, MainAct.mAListCategory.get(3).getImageUrl().replace(" ", "%20"), iv_fourth);

    }

    private void setListeners()
    {
        rel_first.setOnClickListener(this);
        rel_second.setOnClickListener(this);
        rel_third.setOnClickListener(this);
        rel_fourth.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.rel_first:
                mUtilities.replaceFragment(MainAct.mAListCategory.get(0).getCategoryId(),MainAct.mAListCategory.get(0).getCategoryName(),getActivity());
                break;
            case R.id.rel_second:
                mUtilities.replaceFragment(MainAct.mAListCategory.get(1).getCategoryId(),MainAct.mAListCategory.get(1).getCategoryName(),getActivity());
                break;
            case R.id.rel_third:
                mUtilities.replaceFragment(MainAct.mAListCategory.get(2).getCategoryId(),MainAct.mAListCategory.get(2).getCategoryName(),getActivity());
                break;
            case R.id.rel_fourth:
                mUtilities.replaceFragment(MainAct.mAListCategory.get(3).getCategoryId(),MainAct.mAListCategory.get(3).getCategoryName(),getActivity());
                break;
        }
    }
}