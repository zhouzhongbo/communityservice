package com.bobo.communityservice.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.UserEditBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.viewmodel.EditUserModel;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhouzhongbo on 2017/4/21.
 */

public class EditUserInfoActivity extends Activity implements TakePhoto.TakeResultListener,InvokeListener {

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    UserEditBinding userEditBinding;
    String TAG = "zzb";
    Uri imageUri;
    CommunityUser user;
    EditUserModel  userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        userEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_edituser_layout);
        user = DroiUser.getCurrentUser(CommunityUser.class);
        userModel = new EditUserModel(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(user != null){
            if(user.Icon.hasUri()){
                Log.d("zzb",user.Icon.getUri().toString());
                Glide.with(this)
                    .load(user.Icon.getUri())
                    .into(userEditBinding.userIcon);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());


        String imagePath = result.getImage().getOriginalPath();
        final DroiFile droifile = new DroiFile(new File(imagePath));
        droifile.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(droiError.isOk()){
                    Log.d("zzb","droifile save is finished");
                    user.setIcon(droifile);
                    user.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","update icon success");
                                userModel.setUser(user);
                                userEditBinding.executePendingBindings();
                                userEditBinding.notifyChange();
                            }
                        }
                    });
                }
            }
        });

        userEditBinding.userIcon.setImageBitmap(BitmapFactory.decodeFile(result.getImage().getOriginalPath()));
//        Glide.with(this).load(new File(result.getImage().getCompressPath()).into(userEditBinding.userIcon);
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
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }



    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.icon_container:
                ShowDialog();
                break;

            case R.id.name_container:
                break;
            case R.id.sex_container:
                break;
            case R.id.address_container:
                break;
        }
    }

    CropOptions op =  new CropOptions.Builder().
            setAspectX(200).setAspectY(200).
            setWithOwnCrop(true).
            create();

    private void ShowDialog(){
        new AlertDialog.Builder(this).setItems(
                new String[] { "拍摄照片", "从相册选择"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto.onPickFromCaptureWithCrop(createOutUri(),op);
                                break;
                            case 1:
                                takePhoto.onPickFromGalleryWithCrop(createOutUri(),op);
                                //onPickFromGallery();
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

}
