package com.ycj.videostream.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.ycj.videostream.R;

import java.io.File;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-17 14:35
 */

public class GlideUtil {
    private GlideUtil() {
    }

    /**
     * 加载项目内的图片
     *
     * @param activity
     * @param resId     资源id(R.drawable.xxx)
     * @param imageView
     */
    public static void load(Activity activity, int resId, ImageView imageView) {
        Glide.with(activity).load(resId).crossFade().into(imageView);
    }

    /**
     * 加载项目内的图片
     *
     * @param fragment
     * @param resId     资源id(R.drawable.xxx)
     * @param imageView
     */
    public static void load(Fragment fragment, int resId, ImageView imageView) {
        Glide.with(fragment).load(resId).crossFade().into(imageView);
    }

    /**
     * 加载SD卡图片文件
     *
     * @param activity
     * @param file
     * @param imageView
     */
    public static void load(Activity activity, File file, ImageView imageView) {
        if (null != file && file.isFile() && file.exists()) {
            Glide.with(activity).load(file).crossFade().into(imageView);
        }
    }

    /**
     * 加载SD卡图片文件
     *
     * @param fragment
     * @param file
     * @param imageView
     */
    public static void load(Fragment fragment, File file, ImageView imageView) {
        if (null != file && file.isFile() && file.exists()) {
            Glide.with(fragment).load(file).crossFade().into(imageView);
        }
    }

    /**
     * 加载SD卡图片文件
     *
     * @param fragment
     * @param file
     * @param imageView
     */
    public static void load(Fragment fragment, File file, ImageView imageView, int errorRes) {
        if (null != file && file.isFile() && file.exists()) {
            Glide.with(fragment).load(file).error(errorRes).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片(防止错位)
     *
     * @param activity
     * @param url
     * @param imageView
     * @param imageViewId
     */
    public static void load(Activity activity, final String url, ImageView imageView, final int imageViewId) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url).crossFade().into(
                    new ImageViewTarget<GlideDrawable>(imageView) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            final Object tag = view.getTag(imageViewId);
                            if (null != tag && tag.toString().equals(url)) {
                                view.setImageDrawable(resource);
                            }
                        }
                    }
            );
        }
    }

    /**
     * 加载网络图片(防止错位)
     *
     * @param fragment
     * @param url
     * @param imageView
     * @param imageViewId
     */
    public static void load(Fragment fragment, final String url, ImageView imageView, final int imageViewId) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(fragment).load(url).crossFade().into(
                    new ImageViewTarget<GlideDrawable>(imageView) {
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            final Object tag = view.getTag(imageViewId);
                            if (null != tag && tag.toString().equals(url)) {
                                view.setImageDrawable(resource);
                            }
                        }
                    }
            );
        }
    }

    /**
     * 加载网络图片
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void load(Activity activity, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param fragment
     * @param url
     * @param imageView
     */
    public static void load(Fragment fragment, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(fragment).load(url).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片(有默认加载中图片)
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void loadOrDefault(Activity activity, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            //TODO 替换默认图片
            Glide.with(activity).load(url).placeholder(R.mipmap.ic_launcher_round).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片(有默认加载中图片)
     *
     * @param fragment
     * @param url
     * @param imageView
     */
    public static void loadOrDefault(Fragment fragment, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            //TODO 替换默认图片
            Glide.with(fragment).load(url).placeholder(R.mipmap.ic_launcher_round).crossFade().into(imageView);
        }
    }


    /**
     * 加载网络图片
     *
     * @param activity
     * @param url
     * @param defaultResId 默认的图片
     * @param imageView
     */
    public static void load(Activity activity, final String url, int defaultResId, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url).placeholder(defaultResId).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param fragment
     * @param url
     * @param defaultResId 默认的图片
     * @param imageView
     */
    public static void load(Fragment fragment, String url, int defaultResId, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(fragment).load(url).placeholder(defaultResId).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param activity
     * @param url
     * @param errorId   加载失败图片
     * @param imageView
     */
    public static void load(Activity activity, String url, int errorId, ImageView imageView, boolean isError) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url).error(errorId).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param activity  Fragment
     * @param url
     * @param errorId   加载失败图片
     * @param imageView
     */
    public static void load(Fragment activity, String url, int errorId, ImageView imageView, boolean isError) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url).error(errorId).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param activity
     * @param url
     * @param defaultResId 默认图片
     * @param failResId    加载失败的图片
     * @param imageView
     */
    public static void load(Activity activity, String url, int defaultResId, int failResId, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url).placeholder(defaultResId).error(failResId).crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param fragment
     * @param url
     * @param defaultResId 默认图片
     * @param failResId    加载失败的图片
     * @param imageView
     */
    public static void load(Fragment fragment, String url, int defaultResId, int failResId, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(fragment).load(url).placeholder(defaultResId).error(failResId).crossFade().into(imageView);
        }
    }
}
