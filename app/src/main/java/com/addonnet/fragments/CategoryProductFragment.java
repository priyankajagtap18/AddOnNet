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
import com.addonnet.constants.AppConstants;
import com.addonnet.entities.Products;
import com.addonnet.interfaces.AdapterResponseInterface;
import com.addonnet.sync.SyncListener;
import com.addonnet.sync.SyncManager;
import com.addonnet.utils.UIUtils;
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
    private RecyclerView mRvProduct;
    private ArrayList<Products> mAListProducts;
    private SyncManager syncManager;
    private SyncListener syncListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_products, container, false);
        bindControls();
        setListeners();
        initSyncListener();
        getProducts();
        return mRootView;
    }

    private void bindControls() {
        mContext = getActivity();
        mUtilities = new Utilities(mContext);
        mRvProduct = (RecyclerView) mRootView.findViewById(R.id.rv_category);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mRvProduct.setLayoutManager(layoutManager);

    }

    private void setListeners() {
    }
    private void getProducts() {
        if (mUtilities.isOnline()) {
            mAListProducts = new ArrayList<>();
            mUtilities.showProgressDialog(getString(R.string.msg_please_wait));
            syncManager = new SyncManager(getActivity(), SyncManager.GET_PRODUCT, syncListener);
            syncManager.getProducts(AppConstants.CAT_ID);
        } else {
            mUtilities.hideProgressDialog();
            UIUtils.showToast(getActivity(), getString(R.string.network_error_msg));
        }
    }

    private void initSyncListener() {
        syncListener = new SyncListener() {
            @Override
            public void onSyncSuccess(int taskId, String result, ArrayList<?> arrResult) {
                mUtilities.hideProgressDialog();
                switch (taskId) {
                    case SyncManager.GET_PRODUCT:
                        if (arrResult != null && arrResult.size() > 0) {
                            if (((ArrayList<Products>) arrResult).get(0).getStatus().equalsIgnoreCase("false")) {
                                Utilities.showToast(getActivity(), "Records not available");
                            } else {
                                mAListProducts = ((ArrayList<Products>) arrResult);
                                setAdapter();
                            }
                        } else {
                            onSyncFailure(taskId, getString(R.string.server_error));
                        }
                        break;
                }
            }

            @Override
            public void onSyncFailure(int taskId, String message) {
                mUtilities.hideProgressDialog();
                UIUtils.showToast(getActivity(), getString(R.string.server_error));
            }

            @Override
            public void onSyncProgressUpdate(String message) {

            }

        };
    }
    private void setAdapter() {
        adapter = new CategoryProductAdapter(mContext, mAListProducts, this);
        mRvProduct.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getAdapterResponse(Bundle bundle) {
        if(bundle!=null) {
            Products products = (Products) bundle.getSerializable(AppConstants.KEY_PRODUCT_DETAILS);
            mUtilities.replaceFragment(getActivity(), new ItemDetailFragment().newInstance(products), R.string.item_detail);
        }
    }
}
