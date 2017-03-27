package com.bobo.communityservice;

import android.app.Application;

import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;

/**
 * Created by zhouzhongbo on 2017/3/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Core.initialize(this);
        DroiAnalytics.initialize(this);
        DroiPush.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);
    }
}
