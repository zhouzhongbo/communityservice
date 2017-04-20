package com.bobo.communityservice.tools;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zhouzhongbo on 2017/4/20.
 */

public class ImageBindTool {

    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorDrawable)
                .into(imageView);
    }
}
