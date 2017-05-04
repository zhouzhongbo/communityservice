package com.bobo.communityservice.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.viewmodel.MineViewModel;

/**
 * Created by zhouzhongbo on 2017/3/28.
 */

public class MineFragment extends Fragment{
    MineViewModel minemodel;
    MineBinding mineBinding;

    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mineBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_mine_layout,container,false);
        mineBinding.setMineViewModel(minemodel);
        minemodel = new MineViewModel(getActivity(),mineBinding);
        return mineBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("zzb","onresume");
        minemodel.refreshUi();
    }


    private void initView(){

        if(minemodel.isLogin()){
            mineBinding.registerOrLoging.setVisibility(View.GONE);
            mineBinding.loginStatusView.setVisibility(View.VISIBLE);
        }else{
            mineBinding.registerOrLoging.setVisibility(View.VISIBLE);
            mineBinding.loginStatusView.setVisibility(View.GONE);
        }
        mineBinding.usrIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerUserIconClick(view);
            }
        });

        mineBinding.registerOrLoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.d("zzb","register or loging clicked!");
                minemodel.handlerRegisterOrLogin(view);
            }
        });

        mineBinding.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerEditUsrInfo(view);
            }
        });

        mineBinding.editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minemodel.handlerEditUsrInfo(v);
            }
        });
        mineBinding.checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerCheckin(view);
            }
        });
        mineBinding.starContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerMyStartClick(view);

            }
        });
        mineBinding.publishContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerMyPublishClick(view);
            }
        });
        mineBinding.orderContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerMyOrderClick(view);

            }
        });
        mineBinding.selledContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerMySelledClick(view);

            }
        });
        mineBinding.feedbackContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerFeedBackClick(view);
            }
        });
        mineBinding.settingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minemodel.handlerSystemSetttingClick(view);

            }
        });
    }
}
