package com.example.administrator.mydemos.Request;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/6/21.
 */
public class RequestManager {

    private RequestQueue mRequestQueue;
    private static RequestManager sInstance;
    private int method;
    private Builder mBuilder;
    private Controller mController;

    public RequestManager() {
//        mBuilder = new Builder();
        mController = new Controller();
        mBuilder = new VolleyBuilder(mController, this);
    }

    public void setBuilder(Builder builder) {
        mBuilder = builder;
    }

    public static VolleyBuilder getInstance() {
        if (sInstance == null) {
            synchronized (RequestManager.class) {
                if (sInstance == null) {
                    sInstance = new RequestManager();
                }
            }
        }
        return (VolleyBuilder)(sInstance.getBuilder());
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

    /*class Builder {
        private int method;
        private RequestCallBack tRequestCallBack;
        private String url;
        private GsonRequest request;

        public Builder() {
        }

        public Builder GET() {
            method = Request.Method.GET;
            return this;
        }

        public Builder POST() {
            method = Request.Method.POST;
            return this;
        }

        private Builder url(String s) {
            url = s;
            return this;
        }

        private void addCallBack(RequestCallBack callBack) {
            tRequestCallBack = callBack;
        }

        private <T> Controller create(T t, Type type) {



            Response.Listener listener = new Response.Listener<T>() {
                @Override
                public void onResponse(T response) {
                    tRequestCallBack.onResponse(response);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tRequestCallBack.onErrorResponse(error);
                }
            };

            request = new GsonRequest<T>(method, url, listener, errorListener, type);
            return mController;
        }

        protected GsonRequest getRequest() {
            return request;
        }

    }*/

    public class Controller {
        public void start() {
            getRequestQueue().add(((VolleyBuilder)mBuilder).getRequest());
        }
    }


    public interface RequestCallBack<T> {
        void onResponse(T response);
        void onErrorResponse(VolleyError error);
    }
}
