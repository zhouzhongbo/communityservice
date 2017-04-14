package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;

import java.util.ArrayList;

/**
 * Created by zzb on 2017/4/5.
 *
 *  descrip goods  selled in market.
 */

public class MarketGoods extends DroiObject {

    @DroiExpose
    Number productSortType;

    @DroiExpose
    String productTitle;

    @DroiExpose
    String productDescription;

    @DroiExpose
    DroiFile productIcon;

    @DroiExpose
    Number productPrice;

    @DroiExpose
    ArrayList<DroiFile> productImgs;

    Number likeCount;
    Number viewerCount;

    ArrayList<PersionGoodsComment> comments;

    ArrayList<PersionGoodsLike> likes;
//    @DroiReference
//    PersionGoodsComment productPersionGoodsComment;
//
//    @DroiReference
//    PersionGoodsLike productPersionGoodsLike;

}
