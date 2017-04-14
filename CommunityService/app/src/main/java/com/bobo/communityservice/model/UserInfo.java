package com.bobo.communityservice.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiReference;
import com.droi.sdk.core.DroiUser;

import java.util.ArrayList;

/**
 * Created by zzb on 2017/4/5.
 */

public class UserInfo extends DroiUser {

    @DroiExpose
    String nickname;

    @DroiExpose
    Boolean Sex;

    @DroiExpose
    DroiFile Icon;

    @DroiReference
    ArrayList<String> address;

}
