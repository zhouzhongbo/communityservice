package com.bobo.communityservice.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.bobo.communityservice.R;
import com.bobo.communityservice.databinding.UserEditBinding;
import com.bobo.communityservice.tools.UnitUtil;
import com.bobo.communityservice.viewmodel.EditUserModel;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
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

/**
 * Created by zhouzhongbo on 2017/4/21.
 */

public class EditUserInfoActivity extends Activity implements TakePhoto.TakeResultListener,InvokeListener {

    String TAG = "zzb";
    TakePhoto takePhoto;
    InvokeParam invokeParam;
    UserEditBinding userEditBinding;
    Uri imageUri;
    EditUserModel  userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        userEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_edituser_layout);
        userModel = new EditUserModel(this,userEditBinding);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(userModel.user != null){
            if(userModel.user.Icon.hasUri()){
                Log.d("zzb",userModel.user.Icon.getUri().toString());
                Glide.with(this)
                    .load(userModel.user.Icon.getUri())
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
        String imagePath = result.getImage().getOriginalPath();
        final DroiFile droifile = new DroiFile(new File(imagePath));
        droifile.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(droiError.isOk()){
                    userModel.user.setIcon(droifile);
                    userModel.user.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","update icon success");
                            }
                        }
                    });
                }
            }
        });
        userEditBinding.userIcon.setImageBitmap(BitmapFactory.decodeFile(result.getImage().getOriginalPath()));
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
                userModel.handlerUserNameEdit();
                break;

            case R.id.sex_container:
                PopupMenu popup = new PopupMenu(EditUserInfoActivity.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.sex_select_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        userEditBinding.userSex.setText(item.getTitle().toString());
                        switch(item.getItemId()){
                            case R.id.male:
                                userModel.user.setSex(true);
                                break;

                            case R.id.female:
                                userModel.user.setSex(false);
                                break;
                        }
                        userModel.user.saveInBackground(new DroiCallback<Boolean>() {
                            @Override
                            public void result(Boolean aBoolean, DroiError droiError) {
                                if(droiError.isOk()){
                                    Log.d("zzb","user save sex successed !");
                                }else{
                                    Log.d("zzb","user save sex failed!");
                                }
                            }
                        });
                        return true;
                    }
                });
                popup.show(); //showing popup menu

                break;
            case R.id.address_container:
                userModel.handlerUserAddressEdit();
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
                                takePhoto.onPickFromCaptureWithCrop(UnitUtil.createOutUri(),op);
                                break;
                            case 1:
                                takePhoto.onPickFromGalleryWithCrop(UnitUtil.createOutUri(),op);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }
}
