package com.wosmart.sdkdemo.util.v7_gt7d.action;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.wosmart.sdkdemo.App;
import com.wosmart.sdkdemo.common.BaseActivity;
import com.wosmart.ukprotocollibary.WristbandManager;
import com.wosmart.ukprotocollibary.WristbandManagerCallback;
import com.wosmart.ukprotocollibary.applicationlayer.ApplicationLayerTemperatureControlPacket;

public class UnitSetAction extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WristbandManager.getInstance().registerCallback(new WristbandManagerCallback() {

            @Override
            public void onTemperatureMeasureSetting(ApplicationLayerTemperatureControlPacket packet) {
                super.onTemperatureMeasureSetting(packet);
                // 读取温度设置返回
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 设置温度单位，true 摄氏度，false 华氏度
                WristbandManager.getInstance().setTempUnit(true);

                // 读取温度设置
                WristbandManager.getInstance().requestTemperatureSetting();

                // 设置距离公英制，true 公制，false 英制
                WristbandManager.getInstance().settingUnitSystem(true);

                // 设置时间格式 true 24 小时，false 12 小时
                WristbandManager.getInstance().setHourSystem(true);

                // 设置日期格式，0 MM-DD(月份在前)  1 DD-MM(月份在后)
                WristbandManager.getInstance().setDeviceDateFormat(0);
            }
        }).start();

    }
}
