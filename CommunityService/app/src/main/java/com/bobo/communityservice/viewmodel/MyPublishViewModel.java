package com.bobo.communityservice.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bobo.communityservice.activity.PublishNewActivity;
import com.bobo.communityservice.adapter.GoodsListAdapter;
import com.bobo.communityservice.model.CommunityUser;
import com.bobo.communityservice.model.PersionGoods;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;

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

    public void refreshItem(GoodsListAdapter adapter){
        loadquery(adapter,false);
    }


    public void LoadMoreItem(GoodsListAdapter adapter){
        loadquery(adapter,true);
    }

    private void loadquery(final GoodsListAdapter adapter, final boolean isLoad){
        Log.d("zzb","publishViewModel star query:isload ="+isLoad);
        CommunityUser user= DroiUser.getCurrentUser(CommunityUser.class);
        DroiCondition cond;
        if(user != null && !user.isAnonymous()){
            cond = DroiCondition.cond("writer._Id", DroiCondition.Type.EQ, user.getObjectId());
            if(isLoad){
                if(mypublish.size()>0){
                    PersionGoods pg = mypublish.get(mypublish.size()-1);
                    DroiCondition cond2 = DroiCondition.cond("_CreationTime", DroiCondition.Type.LT, pg.getCreationTime());
                    cond = cond.and(cond2);
                }
            }
            DroiQuery query = DroiQuery.Builder.newBuilder().orderBy("_CreationTime", true).limit(10).where(cond).query(PersionGoods.class).build();
            query.runQueryInBackground(new DroiQueryCallback<PersionGoods>() {
                @Override
                public void result(List<PersionGoods> list, DroiError droiError) {
                    if(droiError.isOk()){
                        Log.d("zzb","publishViewModel query success! listsize ="+list.size());

                        if (list.size()>0){
                            if(!isLoad){
                                mypublish.clear();
                                adapter.clearData();
                            }
                            mypublish.addAll(list);
                            adapter.addData(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }
}
