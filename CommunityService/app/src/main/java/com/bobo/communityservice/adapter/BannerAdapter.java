package com.bobo.communityservice.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;

import java.util.ArrayList;

/**
 * Created by chenpei on 2016/5/11.
 */
public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Drawable> mBanners;

    public BannerAdapter(Context mContext, ArrayList<Drawable> mBanners) {
        this.mContext = mContext;
        this.mBanners = mBanners;
    }

    @Override
    public int getCount() {
        return mBanners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageDrawable(mBanners.get(position));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
