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

public class MineFragment extends Fragment implements TakePhoto.TakeResultListener,InvokeListener{
    MineViewModel minemodel;
    MineBinding mineBinding;
    boolean isLogin;
    Uri imageUri;
    String TAG = "zzb";
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
    public void onStart() {
        super.onStart();
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
                Intent mintent = new Intent(getActivity(), EditUserInfoActivity.class);
                mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mintent);
//                ShowDialog();
//                    minemodel.handlerUserIconClick(view);
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

    private void ShowDialog(){
        new AlertDialog.Builder(getActivity()).setItems(
                new String[] { "拍摄照片", "从相册选择"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                CropOptions op =  new CropOptions.Builder().
                                        setAspectX(200).setAspectY(200).
                                        setWithOwnCrop(true).
                                        create();
                                takePhoto.onPickFromCaptureWithCrop(createOutUri(),op);
                                break;
                            case 1:
                                takePhoto.onPickFromGallery();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    private Uri createOutUri(){
        // new一个File用来存放拍摄到的照片
        // 通过getExternalStorageDirectory方法获得手机系统的外部存储地址
        File imageFile = new File(Environment
                .getExternalStorageDirectory(), "tempImage.jpg");
        // 如果存在就删了重新创建
        try {
            if (imageFile.exists()) {
                imageFile.delete();
            }
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将存储地址转化成uri对象
        imageUri = Uri.fromFile(imageFile);
        return imageUri;
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
    CommunityUser user;
    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
        user = DroiUser.getCurrentUser(CommunityUser.class);

        String imagePath = result.getImage().getOriginalPath();
        final DroiFile droifile = new DroiFile(new File(imagePath));
        droifile.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(droiError.isOk()){
                    user.setIcon(droifile);
                    user.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","update icon success");
                                mineBinding.executePendingBindings();
                            }
                        }
                    });
                }
            }
        });
        mineBinding.executePendingBindings();

    }

    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(getActivity()),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

}
