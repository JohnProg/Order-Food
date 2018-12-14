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
import com.tectime.johnpaul.orderfood.model.Request;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by johnmachahuay on 4/1/18.
 */

public class RequestViewHolder extends RecyclerView.ViewHolder {

    private TextView txtId, txtStatus, txtPhone, txtAddress;
    private ItemClickListener delegate;

    public RequestViewHolder(View itemView) {
        super(itemView);
        txtId = (TextView) itemView.findViewById(R.id.txtId);
        txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
        txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
        txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.onClick(view, getAdapterPosition(), false);
            }
        });
    }

    public void bindItemData(final Request request) {
        txtId.setText(request.getId());
        txtStatus.setText(convertCodeToText(request.getStatus()));
        txtPhone.setText(request.getPhone());
        txtAddress.setText(request.getAddress());
    }

    private String convertCodeToText(String status) {
        if (status == null) {
            return "Placed";
        }
        if (status.equals(Request.STATUS_PLACED)) {
            return "Placed";
        } else if (status.equals(Request.STATUS_SHIPPING)) {
            return "On my way";
        } else {
            return "Shipped";
        }
    }

    public void setDelegate(ItemClickListener delegate) {
        this.delegate = delegate;
    }
}
