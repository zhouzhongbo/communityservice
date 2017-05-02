package com.bobo.communityservice.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.activity.EditUserInfoActivity;
import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.viewmodel.MineViewModel;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zhouzhongbo on 2017/3/28.
 */

public class MineFragment extends Fragment{
    MineViewModel minemodel;
    MineBinding mineBinding;
    boolean isLogin;
    Uri imageUri;
    String TAG = "zzb";
    CommunityUser user;

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
