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

public class PersionOrder extends DroiObject {

    @DroiReference
    CommunityUser buyer;

    @DroiReference
    PersionGoods goods;

    @DroiExpose
    Boolean isDelivery;

    @DroiExpose
    Boolean isReceipt;

    public CommunityUser getBuyer() {
        return buyer;
    }

    public void setBuyer(CommunityUser buyer) {
        this.buyer = buyer;
    }

    public PersionGoods getGoods() {
        return goods;
    }

    public void setGoods(PersionGoods goods) {
        this.goods = goods;
    }

    public Boolean getDelivery() {
        return isDelivery;
    }

    public void setDelivery(Boolean delivery) {
        isDelivery = delivery;
    }

    public Boolean getReceipt() {
        return isReceipt;
    }

    public void setReceipt(Boolean receipt) {
        isReceipt = receipt;
    }
}
