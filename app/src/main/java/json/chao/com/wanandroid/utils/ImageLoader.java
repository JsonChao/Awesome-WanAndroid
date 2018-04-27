package json.chao.com.wanandroid.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import json.chao.com.wanandroid.app.WanAndroidApp;

/**
 * @author quchao
 * @date 2017/11/27
 */

public class ImageLoader {

    /**
     * 使用Glide加载圆形ImageView(如头像)时，不要使用占位图
     *
     * @param context context
     * @param url image url
     * @param iv imageView
     */
    public static void load(Context context, String url, ImageView iv) {
        if (!WanAndroidApp.getAppComponent().getDataManager().getNoImageState()) {
            GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.DATA).into(iv);
        }
    }
}
