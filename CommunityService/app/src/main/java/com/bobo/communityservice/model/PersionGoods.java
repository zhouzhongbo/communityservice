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
    String goodsTitle;

    @DroiExpose
    String Description;

    @DroiExpose
    Number goodsPrice;

    @DroiExpose
    DroiFile goodsIcon;

    @DroiExpose
    ArrayList<DroiFile> goodsImg;

    @DroiExpose
    String goodsSellerName;

    @DroiExpose
    String goodsSellerPhoneNumber;

    @DroiExpose
    int goodsType;

    Number likeCount;
    Number viewerCount;


    @DroiReference
    UserInfo writer;

    @DroiReference
    ArrayList<UserInfo> goodsViewer;

    //reference object

    @DroiReference
    PersionGoodsLike goodsLike;

    @DroiReference
    PersionGoodsComment goodsComments;

}
