package com.wosmart.sdkdemo.util.v7_gt7d.action;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.wosmart.sdkdemo.R;
import com.wosmart.sdkdemo.activity.BuiltInWatchFaceActivity;
import com.wosmart.sdkdemo.activity.CustomWatchFaceActivity;
import com.wosmart.sdkdemo.activity.MarketWatchFaceActivity;
import com.wosmart.sdkdemo.common.BaseActivity;

/**
 *
 * {@link com.wosmart.sdkdemo.activity.WatchFaceActivity}
 */
public class WatchFaceAction extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_face);

        findViewById(R.id.btn_built_in_watch_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchFaceAction.this, BuiltInWatchFaceActivity.class));
            }
        });

        findViewById(R.id.btn_market_watch_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchFaceAction.this, MarketWatchFaceActivity.class));
            }
        });

        findViewById(R.id.btn_custom_watch_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WatchFaceAction.this, CustomWatchFaceActivity.class));
            }
        });
    }



}
