package com.bobo.communityservice.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bobo.communityservice.R;
import com.bobo.communityservice.fragment.MyOrderFragment;
import com.bobo.communityservice.fragment.MyPublishFragment;
import com.bobo.communityservice.fragment.MySelledFragment;
import com.bobo.communityservice.fragment.MyStartFragment;
import com.bobo.communityservice.tools.ConstantObject;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class MineRelativeActivity extends FragmentActivity {
    static FragmentManager fm;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.mine_relative_layout);
        fm = getSupportFragmentManager();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void initView(){
        FragmentTransaction transaction = fm.beginTransaction();
        String flag = getIntent().getStringExtra("TAG");
        if(flag == null){
            this.finish();
        }else if(flag.equals(ConstantObject.START)){
            Fragment startFragment = new MyStartFragment();
            transaction.replace(R.id.fragment_layout, startFragment);
        }else if(flag.equals(ConstantObject.PUBLISH)){
            Fragment publishFragment = new MyPublishFragment();
            transaction.replace(R.id.fragment_layout, publishFragment);
        }else if(flag.equals(ConstantObject.ORDER)){
            Fragment orderFragment = new MyOrderFragment();
            transaction.replace(R.id.fragment_layout, orderFragment);
        }else if(flag.equals(ConstantObject.SELLED)){
            Fragment orderFragment = new MySelledFragment();
            transaction.replace(R.id.fragment_layout, orderFragment);
        }
        transaction.commit();
    }

}
