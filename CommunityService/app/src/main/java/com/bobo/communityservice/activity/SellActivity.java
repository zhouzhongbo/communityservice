package com.bobo.communityservice.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.SellInfoBinding;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.viewmodel.SellInfoViewModel;

/**
 * Created by zhouzhongbo on 2017/4/17.
 */

public class SellActivity extends AppCompatActivity {

    SellInfoBinding binding;
    SellInfoViewModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PersionGoods goods = getIntent().getParcelableExtra("Goods");
        if(goods.goodsImg != null){
            Log.d("zzb","goodsimg = "+goods.toString());
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sell_layout);
        model = new SellInfoViewModel(this,binding);
        model.setGoods(goods);
        binding.setSellModel(model);
        model.viewInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
