package com.addonnet.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addonnet.R;
import com.addonnet.constants.AppConstants;
import com.addonnet.entities.Categories;
import com.addonnet.interfaces.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 08-09-2016.
 */
public class CategoryNameAdapter extends RecyclerView.Adapter<CategoryNameAdapter.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Categories> mArrLResult;
    private AdapterResponseInterface listener;


    public CategoryNameAdapter(Context context, ArrayList<Categories> arrResult, AdapterResponseInterface listener) {
        super();
        this.mContext = context;
        this.mArrLResult = arrResult;
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_category, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int i)
    {
        holder.mTvCategoryName.setText(mArrLResult.get(i).getCategoryName());
    }

    @Override
    public int getItemCount() {
//        return mArrLResult.size();
        return mArrLResult.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvCategoryName;
        private ImageView mIvCategory;
        private LinearLayout mLlCategory;

        public CategoryViewHolder(View view) {
            super(view);
            mTvCategoryName = (TextView) view.findViewById(R.id.tv_category_name);
            mIvCategory = (ImageView) view.findViewById(R.id.iv_category);
            mLlCategory = (LinearLayout) view.findViewById(R.id.ll_category);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.KEY_CATEGORY_ID, mArrLResult.get(getAdapterPosition()).getCategoryId());
                    bundle.putString(AppConstants.KEY_CATEGORY_NAME, mArrLResult.get(getAdapterPosition()).getCategoryName());
                    listener.getAdapterResponse(bundle);
                }
            });
        }

    }
}

