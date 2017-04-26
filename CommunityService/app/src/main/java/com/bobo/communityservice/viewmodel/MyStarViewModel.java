package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.util.Log;

import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.OrderInfo;
import com.bobo.communityservice.model.PersionGoods;
import com.bobo.communityservice.model.PersionGoodsLike;
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

public class MyStarViewModel {

    Context context;
    ArrayList<PersionGoods> mystar;
    public MyStarViewModel(Context context){
        this.context = context;
    }


    public ArrayList<PersionGoods> queryList(){
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            DroiCondition cond = DroiCondition.cond("likeCommunityUser._Id", DroiCondition.Type.EQ, user.getObjectId());
            DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).where(cond).query(PersionGoodsLike.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoodsLike>() {
                @Override
                public void result(List<PersionGoodsLike> list, DroiError droiError) {
                    if(droiError.isOk()){
                        if (list.size()>0){
                            for(int i=0;i<list.size();i++){
                                mystar.add(list.get(i).getGoods());
                            }
                        }
                    }
                }
            });
        }
        return mystar;
    }

}
