package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by zzb on 2017/4/6.
 */

public class MarketGoodsLike extends DroiObject {

    @DroiExpose
    String marketGoodID;

    @DroiReference
    UserInfo likeUserInfo;

}
