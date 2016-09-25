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
import com.addonnet.entities.CategoryData;
import com.addonnet.interfaces.AdapterResponseInterface;

import java.util.List;

/**
 * Created by pita on 9/25/2016.
 */
public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CategoryData> mArrLResult;
    private AdapterResponseInterface listener;


    public CategoryProductAdapter(Context context, List<CategoryData> arrResult, AdapterResponseInterface listener) {
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
    }

    @Override
    public int getItemCount() {
//        return mArrLResult.size();
        return 5;
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
                    // bundle.putString(AppConstants.KEY_CATEGORY, "cat="+mArrLResult.get(getAdapterPosition()).getId());
                    //  bundle.putString("Year", mArrLResult.get(getAdapterPosition()));
                    listener.getAdapterResponse(bundle);
                }
            });
        }

    }
}


