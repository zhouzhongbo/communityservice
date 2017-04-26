package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

import java.util.ArrayList;

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
    public ArrayList<DroiFile> goodsImg;

    @DroiExpose
    public String goodsSellerName;

    @DroiExpose
    public String goodsSellerPhoneNumber;

    @DroiExpose
    public int goodsType;

    public Number likeCount;
    public Number viewerCount;


    @DroiReference
    public CommunityUser writer;

    @DroiReference
    ArrayList<CommunityUser> goodsViewer;


    @DroiReference
    PersionGoodsLike goodsLike;


    @DroiReference
    PersionGoodsComment goodsComments;


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
}
