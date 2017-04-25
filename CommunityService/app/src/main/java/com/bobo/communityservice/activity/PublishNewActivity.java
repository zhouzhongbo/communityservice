package com.bobo.communityservice.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bobo.communityservice.R;
import com.bobo.communityservice.adapter.CustomViewHolder;
import com.bobo.communityservice.adapter.PublishGalleryAdapter;
import com.bobo.communityservice.databinding.NewPublishBinding;
import com.bumptech.glide.Glide;
import com.droi.sdk.core.DroiFile;

import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class PublishNewActivity extends Activity implements PublishGalleryAdapter.OnItemClickListener{
    NewPublishBinding newPublishBinding;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPublishBinding = DataBindingUtil.setContentView(this, R.layout.activity_newpublish_layout);

    }

    private void initView(){
        newPublishBinding.publishTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initRecycleView(){
        PublishGalleryAdapter adapter = new PublishGalleryAdapter(this);
        newPublishBinding.imglist.setAdapter(adapter);
//        adapter.setItemClickListener();
//        newPublishBinding.imglist.addOnScrollListener(new MyPublishFragment.OnRecyclerScrollListener());

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newPublishBinding.imglist.setLayoutManager(linearLayoutManager);
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

//    public String goodsTitle;
//    public String Description;
//    public Number goodsPrice;
//    public DroiFile goodsIcon;
//    public ArrayList<DroiFile> goodsImg;
//    public String goodsSellerName;
//    public String goodsSellerPhoneNumber;
//    public int goodsType;
//    public CommunityUser writer;


    @Override
    public void onAddImageClick(List<DroiFile> desImg) {
        ShowDialog();
    }



    @Override
    public void onRemoveImageClick(List<DroiFile> desImg, int position) {

    }

    @Override
    public void onPreViewImageClick(List<DroiFile> desImg, int position) {

    }

    private void ShowDialog(){
        new AlertDialog.Builder(this).setItems(
                new String[] { "拍摄照片", "从相册选择"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
//                                    takePhoto();
                                Log.d("zzb","11111111111");
                                break;
                            case 1:
//                                    getPhotoFromAlbum();
                                Log.d("zzb","22222222");
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }
}
