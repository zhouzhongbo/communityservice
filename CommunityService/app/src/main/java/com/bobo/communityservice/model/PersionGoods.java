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

    //reference object

    @DroiReference
    PersionGoodsLike goodsLike;

    @DroiReference
    PersionGoodsComment goodsComments;


}
