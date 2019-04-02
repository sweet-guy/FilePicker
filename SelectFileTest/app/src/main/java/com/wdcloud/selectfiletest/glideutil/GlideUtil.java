package com.wdcloud.selectfiletest.glideutil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wdcloud.selectfiletest.R;

import java.io.File;

/**
 * Created by Umbrella on 2018/7/11.
 */

public class GlideUtil {

    public static RequestOptions getCropRequestOptions() {
        return new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

    }

    /*圆形带边框*/
    public static RequestOptions getCircleRequestOptions(Context context) {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(2, ContextCompat.getColor(context, R.color.font_blue)));
    }

    public static RequestOptions getCircleRequestOptions(int borderWidth,int borderColor) {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(borderWidth,borderColor));
    }

    /*圆角*/
    public static RequestOptions getCircRoundleRequestOptions(int round) {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(round));
    }


    /*普通的*/
    public static RequestOptions getRequestOptions() {
        return new RequestOptions()
                .placeholder(R.mipmap.default_image)// 正在加载中的图片
                .error(R.mipmap.default_image) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
    }


    /*下载图片*/
    public static void downloadImage(final Context context, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String url = "http://www.guolin.tech/book.png";
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    Log.d("logcat", "下载好的图片文件路径=" + imageFile.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context
     * @param url       例如：https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif
     * @param imageView
     */
    public static void loadGif(Context context, String url, ImageView imageView, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
                })
                .into(imageView);

    }


    public static void loadImageView(Context context, ImageView imageView, String path, RequestOptions options) {
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    public static void loadImageView(Context context, ImageView imageView, File file, RequestOptions options) {
        Glide.with(context).load(file).apply(options).into(imageView);
    }

}
