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
    public Number productSortType;

    @DroiExpose
    public String productTitle;

    @DroiExpose
    public String productDescription;

    @DroiExpose
    public DroiFile productIcon;

    @DroiExpose
    public Number productPrice;

    @DroiExpose
    public ArrayList<DroiFile> productImgs;

    public Number likeCount;
    public Number viewerCount;

    public ArrayList<PersionGoodsComment> comments;

    public ArrayList<PersionGoodsLike> likes;
//    @DroiReference
//    PersionGoodsComment productPersionGoodsComment;
//
//    @DroiReference
//    PersionGoodsLike productPersionGoodsLike;

    public MarketGoods(){

    }

}
