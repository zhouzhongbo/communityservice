package com.bobo.communityservice.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bobo.communityservice.R;
import com.bobo.communityservice.fragment.LoginFragment;
import com.droi.sdk.analytics.DroiAnalytics;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class LoginActivity extends FragmentActivity {

    static FragmentManager fm;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        fm = getSupportFragmentManager();
        displayLoginFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DroiAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DroiAnalytics.onPause(this);
    }

    private static void displayLoginFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_layout, loginFragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
