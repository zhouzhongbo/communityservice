package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by zzb on 2017/4/5.
 *  used to descrip comments in sell goods
 */

public class PersionGoodsComment extends DroiObject {

    @DroiExpose
    String persionGoodID;

    @DroiReference
    CommunityUser commentsTo;

    @DroiReference
    CommunityUser commentsFrom;

    @DroiExpose
    String commnetsBody;

    @DroiExpose
    DroiFile commentsImg;

}
