package com.bobo.communityservice.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.R;
import com.bobo.communityservice.activity.LoginActivity;
import com.bobo.communityservice.activity.MineRelativeActivity;
import com.bobo.communityservice.activity.SystemSettingActivity;
import com.bobo.communityservice.databinding.MineBinding;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.tools.ConstantObject;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class MineViewModel {
    public CommunityUser user;
    private boolean isLogin;
    Context context;
    MineBinding mineBinding;
    private TakePhoto takePhoto;


    public MineViewModel(Context context,MineBinding minebinding){
//        user.Icon.getUri();
        this.context = context;
        isLogin = isLogin();
        mineBinding = minebinding;
    }

    public void setTakePhoto(TakePhoto takePhoto){
        this.takePhoto = takePhoto;
    }


    private boolean isLogin(){
        boolean islogin;
        user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            islogin = true;
        } else {
            islogin = false;
        }
        return islogin;
    }

    public void handlerUserIconClick(View v){
        Log.d("zzb","is login ="+isLogin);
        if(isLogin){
            ShowDialog();
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerUserNameClick(View v){
        Log.d("zzb","handlerUserNameClick");
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }

    }

    public void handlerEditUsrInfo(View v){
        Log.d("zzb","handlerEditUsrInfo");
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerRegisterOrLogin(View v){
        Intent mintent = new Intent(context, LoginActivity.class);
        mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mintent);
    }

    public void handlerCheckin(View v){
        Log.d("zzb","handlerCheckin");
        if(isLogin){

        }else{
            handlerRegisterOrLogin(v);
        }
    }


    public void handlerMyStartClick(View v){
        Log.d("zzb","handlerMyStartClick");
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.START);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerMyPublishClick(View v){
        Log.d("zzb","handlerMyPublishClick");
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.PUBLISH);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerMyOrderClick(View v){
        Log.d("zzb","handlerMyOrderClick");
        if(isLogin){
            Intent mintent = new Intent(v.getContext(), MineRelativeActivity.class);
            mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mintent.putExtra("TAG", ConstantObject.ORDER);
            context.startActivity(mintent);
        }else{
            handlerRegisterOrLogin(v);
        }
    }

    public void handlerFeedBackClick(View v){
        Log.d("zzb","handlerFeedBackClick");
        if(isLogin){
        }else{
            //打开反馈页面
            DroiFeedback.callFeedback((Activity)context);
        }
    }
    public void handlerSystemSetttingClick(View v){
        Log.d("zzb","handlerSystemSetttingClick");
        if(isLogin){
        }else{
            Intent setting = new Intent(context, SystemSettingActivity.class);
            setting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(setting);
        }
    }


    public void refreshUi(){
        isLogin = isLogin();
        if(isLogin){
            mineBinding.loginStatusView.setVisibility(View.VISIBLE);
            mineBinding.registerOrLoging.setVisibility(View.GONE);
        }else{
            mineBinding.loginStatusView.setVisibility(View.GONE);
            mineBinding.registerOrLoging.setVisibility(View.VISIBLE);
        }
        mineBinding.notifyChange();
//        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
//            nameTextView.setText(user.getUserId());
//            if (user.avatar != null) {
//                user.avatar.getInBackground(new DroiCallback<byte[]>() {
//                    @Override
//                    public void result(byte[] bytes, DroiError error) {
//                        if (error.isOk()) {
//                            if (bytes == null) {
//                                Log.i(TAG, "bytes == null");
//                            } else {
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                                titleImg.setImageBitmap(bitmap);
//                            }
//                        }
//                    }
//                }, null);
//            }
//        } else {
//            titleImg.setImageResource(R.drawable.profile_default_icon);
//            nameTextView.setText(R.string.fragment_mine_login);
//        }
    }


    private void ShowDialog(){
        new AlertDialog.Builder(context).setItems(
                new String[] { "拍摄照片", "从相册选择"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto.onPickFromCapture(createOutUri());
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
        Uri imageUri;
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

    public void setUserIcon(final TImage imageFile){
        String imagePath = imageFile.getOriginalPath();
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
                            }
                        }
                    });
                }
            }
        });
        if(droifile.hasUri()){
            Glide.with(context)
                    .load(droifile.getUri())
                    .into(mineBinding.usrIcon);
        }else{
            Glide.with(context)
                    .load(new File(imageFile.getCompressPath()))
                    .into(mineBinding.usrIcon);
        }
    }
}
