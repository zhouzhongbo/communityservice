package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

/**
 * Created by zzb on 2017/4/5.
 * this is used to descrip product in shoppingcart
 */

public class ThingInCart extends DroiObject {

    @DroiExpose
    String marketProductID;

    @DroiExpose
    Number count;

    @DroiExpose
    Boolean isSelected;
}
