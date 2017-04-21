package com.example.administrator.mydemos.Request;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * Created by Administrator on 2016/6/21.
 */
public class RequestManager {

    private RequestQueue mRequestQueue;
    private static RequestManager sInstance;
    private Builder mBuilder;
    private Controller mController;

    public RequestManager() {
//        mBuilder = new Builder();
        mController = new Controller();
        mBuilder = new VolleyBuilder(mController);
    }



    public static RequestManager getInstance() {
        if (sInstance == null) {
            synchronized (RequestManager.class) {
                if (sInstance == null) {
                    sInstance = new RequestManager();
                }
            }
        }
        return sInstance;
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, final ResourceCallBack resourceCallBack) {
        Glide
                .with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        resourceCallBack.onImageReady();
                    }
                });
    }

    public void setBuilder(Builder builder) {
        mBuilder = builder;
    }

    public static Builder volley() {
        Builder builder = sInstance.getBuilder();
        builder.reset();
        return builder;
    }

    public RequestQueue initRequestQueue(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    private RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    private Builder getBuilder() {
        return mBuilder;
    }

    public void cancelAllRequests(String tag) {
        mController.cancelAll(tag);
    }

    class Controller {
        void start(Request request) {
            getRequestQueue().add(request);
        }
        void cancelAll(String tag) {
            getRequestQueue().cancelAll(tag);
        }
    }

    public interface ResponseCallBack<T> {
        void onResponse(T response);
        void onErrorResponse(Error error);
    }

    public static class Error {
        public Error(VolleyError error) {
        }
    }

    public interface ResourceCallBack {
        void onImageReady();
    }

}
