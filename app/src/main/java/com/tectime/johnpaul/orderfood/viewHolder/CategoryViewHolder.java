package com.tectime.johnpaul.orderfood.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tectime.johnpaul.orderfood.HomeActivity;
import com.tectime.johnpaul.orderfood.R;
import com.tectime.johnpaul.orderfood.base.ItemClickListener;
import com.tectime.johnpaul.orderfood.model.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView txtCategoryName;
    private ImageView imgViewCategoryBanner;
    private ItemClickListener delegate;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        txtCategoryName = (TextView) itemView.findViewById(R.id.category_name);
        imgViewCategoryBanner = (ImageView) itemView.findViewById(R.id.category_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.onClick(view, getAdapterPosition(), false);
            }
        });
    }

    public void bindItemData(final Category viewModel) {
        txtCategoryName.setText(viewModel.getName());
        Glide.with(itemView.getContext())
                .load(viewModel.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.background))
                .into(imgViewCategoryBanner);
    }

    public void setDelegate(ItemClickListener delegate) {
        this.delegate = delegate;
    }
}
