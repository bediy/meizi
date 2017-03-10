package com.example.administrator.mydemos.Request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

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

    public void setBuilder(Builder builder) {
        mBuilder = builder;
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
        void onErrorResponse(VolleyError error);
    }
}
