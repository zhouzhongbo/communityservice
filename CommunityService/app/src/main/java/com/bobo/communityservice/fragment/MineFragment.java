package com.bobo.communityservice.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.viewmodel.MineViewModel;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhouzhongbo on 2017/3/28.
 */

public class MineFragment extends Fragment implements TakePhoto.TakeResultListener,InvokeListener {
    MineViewModel minemodel;
    MineBinding mineBinding;
    boolean isLogin;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;


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
        getTakePhoto().onCreate(savedInstanceState);
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
        minemodel.setTakePhoto(getTakePhoto());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(getActivity(),type,invokeParam,this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(getActivity()),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        minemodel.refreshUi();
    }


    private void initView(){
        if(isLogin){
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

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(getActivity(),this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        ArrayList<TImage> im =  result.getImages();
        minemodel.setUserIcon(im.get(0));
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }
}
