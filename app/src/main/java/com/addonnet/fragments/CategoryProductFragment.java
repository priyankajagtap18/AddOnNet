package com.addonnet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.addonnet.R;
import com.addonnet.adapters.CategoryProductAdapter;
import com.addonnet.entities.CategoryData;
import com.addonnet.interfaces.AdapterResponseInterface;
import com.addonnet.utils.Utilities;

import java.util.ArrayList;

/**
 * Created by pita on 9/25/2016.
 */
public class CategoryProductFragment extends Fragment implements View.OnClickListener, AdapterResponseInterface {
    private View mRootView;
    private Utilities mUtilities;
    private Context mContext;
    private CategoryProductAdapter adapter;
    private ArrayList<CategoryData> mArrLCategory;
    private RecyclerView mRvCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_category, container, false);
        bindControls();
        setListeners();
        setAdapter();
        return mRootView;
    }

    private void bindControls() {
        mContext = getActivity();
        mUtilities = new Utilities(mContext);
        mRvCategory = (RecyclerView) mRootView.findViewById(R.id.rv_category);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRvCategory.setLayoutManager(layoutManager);

    }

    private void setListeners() {
    }

    private void setAdapter() {
        mArrLCategory = new ArrayList<>();
        adapter = new CategoryProductAdapter(mContext, mArrLCategory, this);
        mRvCategory.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getAdapterResponse(Bundle bundle) {
        mUtilities.replaceFragment(getActivity(), new ItemDetailFragment(), R.string.item_detail);
    }
}
