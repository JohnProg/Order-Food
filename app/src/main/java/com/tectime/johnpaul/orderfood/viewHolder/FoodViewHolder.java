package com.tectime.johnpaul.orderfood.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tectime.johnpaul.orderfood.R;
import com.tectime.johnpaul.orderfood.base.ItemClickListener;
import com.tectime.johnpaul.orderfood.model.Food;

/**
 * Created by johnmachahuay on 3/31/18.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder {

    private TextView txtFoodName;
    private ImageView imgViewFoodImage;
    private ItemClickListener delegate;

    public FoodViewHolder(View itemView) {
        super(itemView);
        txtFoodName = itemView.findViewById(R.id.food_name);
        imgViewFoodImage = itemView.findViewById(R.id.food_image);
    }

    public void bindItemData(final Food viewModel) {
        Log.d("a", viewModel.getName());
        txtFoodName.setText(viewModel.getName());
        Glide.with(itemView.getContext())
                .load(viewModel.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.background))
                .into(imgViewFoodImage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.onClick(view, getAdapterPosition(), false);
            }
        });
    }

    public void setDelegate(ItemClickListener delegate) {
        this.delegate = delegate;
    }
}