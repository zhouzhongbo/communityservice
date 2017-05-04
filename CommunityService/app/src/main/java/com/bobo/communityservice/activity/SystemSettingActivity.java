package com.bobo.communityservice.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.SystemSettingBinding;
import com.bobo.communityservice.viewmodel.SystemSettingModel;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class SystemSettingActivity extends Activity {

    SystemSettingBinding ssbinding;
    SystemSettingModel ssmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ssbinding = DataBindingUtil.setContentView(this,R.layout.activity_system_setting_layout);
        ssmodel = new SystemSettingModel(this);
        ssbinding.setSettingmodel(ssmodel);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
