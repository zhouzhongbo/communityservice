package com.bobo.communityservice.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.CustomViewHolder;
import com.bobo.communityservice.adapter.PublishGalleryAdapter;
import com.bobo.communityservice.databinding.NewPublishBinding;
import com.bobo.communityservice.model.PersionGoods;
import com.bumptech.glide.Glide;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class PublishNewActivity extends Activity implements PublishGalleryAdapter.OnItemClickListener,TakePhoto.TakeResultListener,InvokeListener {
    NewPublishBinding newPublishBinding;
    LinearLayoutManager linearLayoutManager;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private PopupWindow mPopupWindow;
    Uri imageUri;
    PublishGalleryAdapter adapter;
    PersionGoods  persionGoods;

    public String goodsTitle;
    public String Description;
    public Number goodsPrice;
    public DroiFile goodsIcon;
    public ArrayList<DroiFile> goodsImg;
    public String goodsSellerName;
    public String goodsSellerPhoneNumber;
    public int goodsType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        newPublishBinding = DataBindingUtil.setContentView(this, R.layout.activity_newpublish_layout);
        initView();
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

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }



    @Override
    public void takeSuccess(TResult result) {
        ArrayList<TImage> im =  result.getImages();
        for(int i=0;i<im.size();i++){
            String imagePath = im.get(0).getOriginalPath();
            DroiFile img = new DroiFile(new File(imagePath));
            adapter.addImageItem(img);
        }
        adapter.notifyDataSetChanged();
        Log.d("zzb","takeSuccess:"+im.size());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.d("zzb","takeFail"+msg);
        Toast.makeText(this,"take photo failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Log.d("zzb","takeCancel");
        Toast.makeText(this,"take photo cancel",Toast.LENGTH_SHORT).show();
    }


    private void initView(){
        newPublishBinding.publishTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                goodsTitle = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        newPublishBinding.publishItemDescripton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Description = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newPublishBinding.priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                goodsPrice = Long.valueOf(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        initRecycleView();
        newPublishBinding.publishSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(publishCheck()){
                    // 1. 注册UserData至SDK
                    DroiObject.registerCustomClass( PersionGoods.class );
                    persionGoods = new PersionGoods();
                    persionGoods.setGoodsTitle(goodsTitle);
                    persionGoods.setDescription(Description);
                    persionGoods.setGoodsImg(adapter.getImgList());
                    persionGoods.setGoodsPrice(goodsPrice);
                    persionGoods.setGoodsType(goodsType);

                    persionGoods.saveInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            if(droiError.isOk()){
                                Log.d("zzb","persionGoods save failed!");
                            }else{
                                Log.d("zzb","persionGoods save failed!");
                            }
                        }
                    });
                }
            }
        });


        newPublishBinding.typeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(PublishNewActivity.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.publish_type_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        newPublishBinding.typeChoose.setText(item.getTitle().toString());
                        switch(item.getItemId()){
                            case R.id.house:
                                goodsType = 1;
                                break;
                            case R.id.persion_goods:
                                goodsType = 2;
                                break;
                            case R.id.help_repair:
                                goodsType = 3;
                                break;
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }

    private void initRecycleView(){
        adapter = new PublishGalleryAdapter(this);
        newPublishBinding.imglist.setAdapter(adapter);
        adapter.setItemClickListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newPublishBinding.imglist.setLayoutManager(linearLayoutManager);
    }


    private boolean publishCheck(){
        boolean result = false;
        if(goodsTitle == null ||goodsTitle.equals("")){
            Toast.makeText(this,"商品标题不能为空",Toast.LENGTH_SHORT).show();
            return result;
        }

        if(Description == null ||Description.equals("")){
            Toast.makeText(this,"商品描述不能为空",Toast.LENGTH_SHORT).show();
            return result;
        }

        if(goodsPrice.floatValue() <= 0 ){
            Toast.makeText(this,"商品价格必须大于0",Toast.LENGTH_SHORT).show();
            return result;
        }
        if(goodsImg != null&&goodsImg.size()>0){
            for(int i=0;i<goodsImg.size();i++){
                if(!goodsImg.get(i).hasUri()){
                    Toast.makeText(this,"图片未上传完成，请稍后再试",Toast.LENGTH_SHORT).show();
                    return result;
                }
            }
        }else {
            Toast.makeText(this,"必须要有图片描述",Toast.LENGTH_SHORT).show();
            return result;
        }

        if(goodsType <=0){
            Toast.makeText(this,"请选择类型",Toast.LENGTH_SHORT).show();
            return result;
        }
        return result;
    }



    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    public void onAddImageClick(List<DroiFile> desImg) {
        ShowDialog();
    }



    @Override
    public void onRemoveImageClick(List<DroiFile> desImg, int position) {
        desImg.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPreViewImageClick(List<DroiFile> desImg, int position) {
        DroiFile  file = desImg.get(position);
        if(!file.hasUri()) {
            Toast.makeText(this,"文件暂未上传，请稍等。。。",Toast.LENGTH_SHORT).show();
            return;
        }
        View popupView = getLayoutInflater().inflate(R.layout.popopwin_preview_img_layout, null);
        popupView.findViewById(R.id.close_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        ImageView preview = (ImageView) popupView.findViewById(R.id.img_preview);
        mPopupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        Glide.with(this)
                .load(file.getUri())
                .into(preview);
    }


    private void ShowDialog(){
        new AlertDialog.Builder(this).setItems(
                new String[] { "拍摄照片", "从相册选择"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto.onPickFromCapture(createOutUri());
                                break;
                            case 1:
                                takePhoto.onPickMultiple(10);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
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