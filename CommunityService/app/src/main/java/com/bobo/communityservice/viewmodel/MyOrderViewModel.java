package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.util.Log;

import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.OrderInfo;
import com.bobo.communityservice.model.PersionGoods;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/26.
 */

public class MyOrderViewModel {

    Context context;
    ArrayList<OrderInfo> myorder = new ArrayList<OrderInfo>();

    public MyOrderViewModel(Context context){
        this.context = context;
    }


    public ArrayList<OrderInfo> queryList(){
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            DroiCondition cond = DroiCondition.cond("UserID._Id", DroiCondition.Type.EQ, user.getObjectId());
            DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).where(cond).query(OrderInfo.class).build();
            query.runQueryInBackground(new DroiQueryCallback<OrderInfo>() {
                @Override
                public void result(List<OrderInfo> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","query success! listsize ="+list.size());
                        if (list.size()>0){
                            myorder.addAll(list);
                        }
                    }
                }
            });
        }
        return myorder;
    }

}
