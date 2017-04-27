package com.bobo.communityservice;

import android.app.Application;

import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.NoticeObject;
import com.bobo.communityservice.model.OrderInfo;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.model.PersionGoodsComment;
import com.bobo.communityservice.model.PersionGoodsLike;
import com.bobo.communityservice.model.ShoppingCart;
import com.bobo.communityservice.model.ThingInCart;
import com.bobo.communityservice.model.ThingInOrder;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;

/**
 * Created by zhouzhongbo on 2017/3/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Core.initialize(this);
        DroiAnalytics.initialize(this);
        DroiPush.initialize(this);
        DroiUpdate.initialize(this);
        DroiFeedback.initialize(this);

        DroiObject.registerCustomClass( CommunityUser.class );
        DroiObject.registerCustomClass( NoticeObject.class );
        DroiObject.registerCustomClass( OrderInfo.class );
        DroiObject.registerCustomClass( PersionGoods.class );
        DroiObject.registerCustomClass( PersionGoodsComment.class );
        DroiObject.registerCustomClass( PersionGoodsLike.class );
        DroiObject.registerCustomClass( ShoppingCart.class );
        DroiObject.registerCustomClass( ThingInCart.class );
        DroiObject.registerCustomClass( ThingInOrder.class );

    }
}
