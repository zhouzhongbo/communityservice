package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

import java.util.ArrayList;

/**
 * Created by zzb on 2017/4/5.
 *
 * shoppigcart is used to store thing what customer interested in,
 * but have not pay for it !
 */

public class ShoppingCart extends DroiObject {

//    @DroiExpose
//    ArrayList<ThingInCart> marketgoodslist;
    @DroiReference
    ArrayList<MarketGoods> marketGoodsList;

    @DroiReference
    UserInfo userID;

    Number totalPrice;

}
