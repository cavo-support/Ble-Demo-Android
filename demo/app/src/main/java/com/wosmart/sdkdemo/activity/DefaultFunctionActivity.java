package com.wosmart.sdkdemo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.wosmart.sdkdemo.R;
import com.wosmart.sdkdemo.common.BaseActivity;
import com.wosmart.ukprotocollibary.WristbandManager;
import com.wosmart.ukprotocollibary.WristbandManagerCallback;
import com.wosmart.ukprotocollibary.model.db.JWHealthDataManager;
import com.wosmart.ukprotocollibary.v2.JWHealthDataSearchParams;
import com.wosmart.ukprotocollibary.v2.JWValueCallback;
import com.wosmart.ukprotocollibary.v2.common.executor.JWArchTaskExecutor;
import com.wosmart.ukprotocollibary.v2.entity.JWHRVRmssdInfo;
import com.wosmart.ukprotocollibary.v2.entity.JWHealthData;
import com.wosmart.ukprotocollibary.v2.entity.function.JWDefaultFunctionInfo;

import java.util.Calendar;
import java.util.List;

public class DefaultFunctionActivity extends BaseActivity implements View.OnClickListener {

    public static final int FUNCTION_ID = 50;

    private static final String TAG = "DefaultFunctionActivity";

    private Toolbar toolbar;

    private Switch bleedPressureSwitch;
    private Switch temperatureSwitch;
    private Switch pressureSwitch;
    private Switch sceneSwitch;
    private Switch alexaSwitch;
    private Switch lightSenseSwitch;

    private JWDefaultFunctionInfo mDefaultFunctionInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_function);
        initView();
        initData();
        addListener();

        readDefaultFunction();
    }

    private void initData() {
        WristbandManager.getInstance().registerCallback(new WristbandManagerCallback() {

            @Override
            public void onReadDefaultFunctionRsp(JWDefaultFunctionInfo defaultFunctionInfo) {
                super.onReadDefaultFunctionRsp(defaultFunctionInfo);
                Log.e(TAG, "onReadDefaultFunctionRsp, dataList = " + defaultFunctionInfo);
                if (defaultFunctionInfo == null) {
                    return;
                }
                mDefaultFunctionInfo = defaultFunctionInfo;
                bleedPressureSwitch.setChecked(defaultFunctionInfo.bloodPressureEnable);
                temperatureSwitch.setChecked(defaultFunctionInfo.temperatureEnable);
                pressureSwitch.setChecked(defaultFunctionInfo.pressureEnable);
                sceneSwitch.setChecked(defaultFunctionInfo.sceneEnable);
                alexaSwitch.setChecked(defaultFunctionInfo.alexaEnable);
                lightSenseSwitch.setChecked(defaultFunctionInfo.lightSenseEnable);
            }

        });

    }


    private void readDefaultFunction() {
        JWArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                WristbandManager.getInstance().readDefaultFunction();
            }
        });
    }

    private void setDefaultFunction() {
        if (mDefaultFunctionInfo == null) {
            Toast.makeText(this, "please read default function before", Toast.LENGTH_SHORT).show();
            return;
        }
        JWArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                WristbandManager.getInstance().setDefaultFunction(mDefaultFunctionInfo);
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        bleedPressureSwitch = findViewById(R.id.sw_blood_pressure);
        temperatureSwitch = findViewById(R.id.sw_temperature);
        pressureSwitch = findViewById(R.id.sw_pressure);
        sceneSwitch = findViewById(R.id.sw_scene);
        alexaSwitch = findViewById(R.id.sw_alexa);
        lightSenseSwitch = findViewById(R.id.sw_light_sense);
    }



    private void addListener() {
        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDefaultFunction();
            }
        });

        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefaultFunction();
            }
        });

        bleedPressureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mDefaultFunctionInfo != null) {
                    mDefaultFunctionInfo.bloodPressureEnable = b;
                }
            }
        });
        temperatureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mDefaultFunctionInfo != null) {
                    mDefaultFunctionInfo.temperatureEnable = b;
                }
            }
        });
        pressureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mDefaultFunctionInfo != null) {
                    mDefaultFunctionInfo.pressureEnable = b;
                }
            }
        });
        sceneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mDefaultFunctionInfo != null) {
                    mDefaultFunctionInfo.sceneEnable = b;
                }
            }
        });
        alexaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mDefaultFunctionInfo != null) {
                    mDefaultFunctionInfo.alexaEnable = b;
                }
            }
        });
        lightSenseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mDefaultFunctionInfo != null) {
                    mDefaultFunctionInfo.lightSenseEnable = b;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
    }
}
