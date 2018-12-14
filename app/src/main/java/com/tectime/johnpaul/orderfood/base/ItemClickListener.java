package com.tectime.johnpaul.orderfood.base;

import android.view.View;

/**
 * Created by johnmachahuay on 3/31/18.
 */

// This is like a delegate in IOS, protocol ItemClickListenerDelegate: class {}
public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}
