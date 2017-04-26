package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by zzb on 2017/4/5.
 */

public class PersionGoodsLike extends DroiObject {

    @DroiReference
    PersionGoods  goods;

    @DroiReference
    CommunityUser likeCommunityUser;


    public PersionGoods getGoods() {
        return goods;
    }

    public void setGoods(PersionGoods goods) {
        this.goods = goods;
    }

    public CommunityUser getLikeCommunityUser() {
        return likeCommunityUser;
    }

    public void setLikeCommunityUser(CommunityUser likeCommunityUser) {
        this.likeCommunityUser = likeCommunityUser;
    }
}
