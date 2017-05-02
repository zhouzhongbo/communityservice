package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by zzb on 2017/4/5.
 *  used to descrip comments in sell goods
 */

public class PersionGoodsComment extends DroiObject {

    @DroiReference
    PersionGoods  goods;

//    at first version,do not support make comments to other people
//    @DroiReference
//    CommunityUser commentsTo;

    @DroiReference
    CommunityUser commentsFrom;

    @DroiExpose
    String commnetsBody;

//    at first version ,do not support img;
//    @DroiExpose
//    DroiFile commentsImg;


    public PersionGoods getGoods() {
        return goods;
    }

    public void setGoods(PersionGoods goods) {
        this.goods = goods;
    }

    public String getCommnetsBody() {
        return commnetsBody;
    }

    public void setCommnetsBody(String commnetsBody) {
        this.commnetsBody = commnetsBody;
    }

    public CommunityUser getCommentsFrom() {
        return commentsFrom;
    }

    public void setCommentsFrom(CommunityUser commentsFrom) {
        this.commentsFrom = commentsFrom;
    }
}
