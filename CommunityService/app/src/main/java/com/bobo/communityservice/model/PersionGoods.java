package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzb on 2017/4/5.
 *  descrip goods  selled by single persion.
 */

public class PersionGoods extends DroiObject {

    @DroiExpose
    public String goodsTitle;

    @DroiExpose
    public String Description;

    @DroiExpose
    public Number goodsPrice;

    @DroiExpose
    public DroiFile goodsIcon;

    @DroiExpose
    public List<DroiFile> goodsImg;

    @DroiExpose
    public String goodsSellerName;

    @DroiExpose
    public String goodsSellerPhoneNumber;

    @DroiExpose
    public int goodsType;

    @DroiReference
    public CommunityUser writer;

    @DroiExpose
    public boolean isSelled = false;

    @DroiReference
    ArrayList<CommunityUser> goodsViewer;

    @DroiExpose
    public Number viewerCount = 0;


    @DroiReference
    public PersionGoodsLike goodsLike;

    @DroiExpose
    public Number likeCount = 0;


    @DroiReference
    public PersionGoodsComment goodsComments;


    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setGoodsPrice(Number goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public void setGoodsIcon(DroiFile goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public void setGoodsImg(ArrayList<DroiFile> goodsImg) {
        this.goodsImg = goodsImg;
    }

    public void setGoodsSellerName(String goodsSellerName) {
        this.goodsSellerName = goodsSellerName;
    }

    public void setGoodsSellerPhoneNumber(String goodsSellerPhoneNumber) {
        this.goodsSellerPhoneNumber = goodsSellerPhoneNumber;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }


    public void setWriter(CommunityUser writer) {
        this.writer = writer;
    }


//    public Number getGoodsPrice() {
//        return goodsPrice;
//    }
}
