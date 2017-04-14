package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;

/**
 * Created by zzb on 2017/4/6.
 *
 * used to description thing in order;
 */

public class ThingInOrder extends DroiObject {

    @DroiExpose
    String sellID;

    @DroiExpose
    String orderId;

    @DroiExpose
    Number prices;

    @DroiExpose
    Number  count;

    @DroiExpose
    DroiFile icon;

}
