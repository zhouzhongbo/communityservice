package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.activity.PublishNewActivity;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.NoticeObject;
import com.bobo.communityservice.model.PersionGoods;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouzhongbo on 2017/4/24.
 */

public class MyPublishViewModel {

    ArrayList<PersionGoods> mypublish = new ArrayList<PersionGoods>();

    Context context;

    public MyPublishViewModel(Context context){
        this.context = context;
    }

    public void handlerNewPublish(View view){
        Intent newPublish = new Intent(view.getContext(),PublishNewActivity.class);
        newPublish.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newPublish);
    }

    public ArrayList<PersionGoods> queryList(){
        CommunityUser user = DroiUser.getCurrentUser(CommunityUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            DroiCondition cond = DroiCondition.cond("CommunityUser._Id", DroiCondition.Type.EQ, user.getObjectId());
            DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).where(cond).query(PersionGoods.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
                @Override
                public void result(List<PersionGoods> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","query success! listsize ="+list.size());
                        if (list.size()>0){
                            mypublish.addAll(list);
                        }
                    }
                }
            });
        }
        return mypublish;
    }


}
