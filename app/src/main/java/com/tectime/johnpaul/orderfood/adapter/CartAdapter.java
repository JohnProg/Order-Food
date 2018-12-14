package com.tectime.johnpaul.orderfood.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tectime.johnpaul.orderfood.R;
import com.tectime.johnpaul.orderfood.model.Order;
import com.tectime.johnpaul.orderfood.viewHolder.CartViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnmachahuay on 4/1/18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private Context context;
    private List<Order> listData = new ArrayList<>();

    public CartAdapter(Context context, List<Order> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Order order = listData.get(position);
        holder.bindItemData(order);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
