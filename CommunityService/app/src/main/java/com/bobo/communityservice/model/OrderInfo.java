package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

import java.util.ArrayList;

/**
 * Created by zzb on 2017/4/6.
 * descrip what people have buied,
 * and store the price what custom pay for it
 */

public class OrderInfo extends DroiObject {

    @DroiReference
    UserInfo UserID;

    @DroiExpose
    String Address;

    @DroiExpose
    ArrayList<ThingInOrder> things;

    @DroiExpose
    Boolean isDelivery;

    @DroiExpose
    Boolean isReceipt;


}
