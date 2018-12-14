package com.tectime.johnpaul.orderfood.application;

import android.app.Application;

import com.tectime.johnpaul.orderfood.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class OrderFoodApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
