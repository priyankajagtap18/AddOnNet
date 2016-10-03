package com.addonnet.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.constants.AppConstants;
import com.addonnet.entities.Products;
import com.addonnet.interfaces.AdapterResponseInterface;
import com.addonnet.utils.Utilities;

import java.util.ArrayList;

/**
 * Created by pita on 9/25/2016.
 */
public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Products> mArrLResult;
    private AdapterResponseInterface listener;


    public CategoryProductAdapter(Context context, ArrayList<Products> arrResult, AdapterResponseInterface listener) {
        super();
        this.mContext = context;
        this.mArrLResult = arrResult;
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_category_product, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int i) {
        try {
            holder.tv_product.setText(mArrLResult.get(i).getProductName());
            Utilities.setImage(mContext, mArrLResult.get(i).getImageUrl().replace(" ", "%20"), holder.mIvProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mArrLResult.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvProduct;
        private TextView tv_product;

        public CategoryViewHolder(View view) {
            super(view);
            mIvProduct = (ImageView) view.findViewById(R.id.iv_item);
            tv_product = (TextView) view.findViewById(R.id.tv_product);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(AppConstants.KEY_PRODUCT_DETAILS, mArrLResult.get(getAdapterPosition()));
                    listener.getAdapterResponse(bundle);
                }
            });
        }

    }
}


