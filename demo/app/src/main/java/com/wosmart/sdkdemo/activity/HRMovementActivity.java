package com.wosmart.sdkdemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.wosmart.sdkdemo.R;
import com.wosmart.sdkdemo.common.BaseActivity;
import com.wosmart.ukprotocollibary.WristbandManager;
import com.wosmart.ukprotocollibary.WristbandManagerCallback;
import com.wosmart.ukprotocollibary.applicationlayer.ApplicationLayerTodaySumSportPacket;
import com.wosmart.ukprotocollibary.model.db.GlobalGreenDAO;
import com.wosmart.ukprotocollibary.model.db.JWHealthDataManager;
import com.wosmart.ukprotocollibary.v2.JWValueCallback;
import com.wosmart.ukprotocollibary.v2.common.executor.JWArchTaskExecutor;
import com.wosmart.ukprotocollibary.v2.entity.JWHRMovementInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HRMovementActivity extends BaseActivity implements View.OnClickListener {

    public static final int FUNCTION_ID = 51;

    private static final String TAG = "HRMovementActivity";

    private Toolbar toolbar;

    private TextView logTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_movement);
        initView();
        initData();
        addListener();
    }

    private void initData() {
        // make sure you have logged in successfully before performing synchronization
        // 执行同步前确保已经登录成功 WristbandManager.getInstance().startLoginProcess("1234567890");
        // when you end the hr movement, you need to execute the synchronization function
        // 在桑拿结束后执行一次同步才能获取到心率体动数据
        WristbandManager.getInstance().registerCallback(new WristbandManagerCallback() {

            @Override
            public void onSyncDataEnd(ApplicationLayerTodaySumSportPacket packet) {
                super.onSyncDataEnd(packet);
                Log.e(TAG, "onSyncDataEnd");
                logTv.setText("onSyncDataEnd");
            }

            @Override
            public void onSyncHRMovementDataReceived(List<JWHRMovementInfo> hrMovementInfoList) {
                super.onSyncHRMovementDataReceived(hrMovementInfoList);
                // sync hr movement data received, you can save it by yourself
                Log.e(TAG, "onSyncHRMovementDataReceived, dataList = " + (hrMovementInfoList != null ? hrMovementInfoList.toString() : ""));
            }
        });
    }

    private void getHRMovementHistory() {
        logTv.setText("");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Log.e(TAG, "getHRMovementHistory");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // after sync hr movement data, you can get special hr movement data by date from SDK database
        List<JWHRMovementInfo> hrMovementInfoList = GlobalGreenDAO.getInstance().loadHRMovementDataByDate(year, month, day);
        if (hrMovementInfoList != null) {
            StringBuilder sb = new StringBuilder();
            for (JWHRMovementInfo info : hrMovementInfoList) {
                sb.append("hr = " + info.heartRateValue + ", tem = " + info.temperature + ", mov = " + info.movement + ", la = " + info.label + ", " + sdf.format(new Date(info.time)) + "\n");
            }
            logTv.setText(sb.toString());
        }

        // or user this method to get hr movement data, it's 2.0 api
        JWHealthDataManager.getInstance().getHistoryHRMovementListByDate(year, month, day, new JWValueCallback<List<JWHRMovementInfo>>() {
            @Override
            public void onSuccess(List<JWHRMovementInfo> data) {
                if (data != null) {
//                    StringBuilder sb = new StringBuilder();
//                    for (JWHRMovementInfo info : data) {
//                        sb.append("hr = " + info.heartRateValue + ", tem = " + info.temperature + ", mov = " + info.movement + ", la = " + info.label + ", " + sdf.format(new Date(info.time)) + "\n");
//                    }
//                    logTv.setText(sb.toString());
                }
            }

            @Override
            public void onError(int code, String desc) {

            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        logTv = findViewById(R.id.tv_log);
    }



    private void addListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_sync_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JWArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        WristbandManager.getInstance().sendDataRequest();
                    }
                });
            }
        });
        findViewById(R.id.btn_enable_measure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JWArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        // support interval 5min 15min
                        WristbandManager.getInstance().setContinueHrp(true, 5);
                    }
                });
            }
        });
        findViewById(R.id.btn_read_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHRMovementHistory();
            }
        });
    }


    @Override
    public void onClick(View v) {
    }
}
