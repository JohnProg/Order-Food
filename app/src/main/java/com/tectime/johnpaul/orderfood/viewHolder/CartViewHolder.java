package com.tectime.johnpaul.orderfood.viewHolder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.tectime.johnpaul.orderfood.R;
import com.tectime.johnpaul.orderfood.base.ItemClickListener;
import com.tectime.johnpaul.orderfood.model.Order;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by johnmachahuay on 4/1/18.
 */

public class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView txt_cart_name, txt_price;
    private ImageView img_cart_count;

    private ItemClickListener delegate;

    public CartViewHolder(View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_item_price);
        img_cart_count = (ImageView) itemView.findViewById(R.id.card_item_count);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.onClick(view, getAdapterPosition(), false);
            }
        });
    }

    public void bindItemData(final Order order) {
        // Set Image
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + order.getQuantity(), Color.RED);
        img_cart_count.setImageDrawable(drawable);

        // Set Price
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int totalPrice = (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        txt_price.setText(fmt.format(totalPrice));

        // Set Name
        txt_cart_name.setText(order.getProductName());
    }

    public void setDelegate(ItemClickListener delegate) {
        this.delegate = delegate;
    }
}