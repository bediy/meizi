package com.example.administrator.mydemos.base;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.mydemos.Request.RequestManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2015/8/26.
 */
public class App extends Application {

    private static App mContext;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        mContext = this;
        RequestManager.getInstance().initRequestQueue(this);
    }

    public static App getContext() {
        return mContext;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;

    }


}
