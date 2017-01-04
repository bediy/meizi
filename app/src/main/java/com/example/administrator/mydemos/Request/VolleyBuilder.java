package com.example.administrator.mydemos.Request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.example.administrator.mydemos.model.AllData;

import java.lang.ref.WeakReference;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

/**
 * Created by Administrator on 2016/6/29.
 * Volley封装
 */
final class VolleyBuilder extends Builder {

    private int method = GET;
    private String url;
    private GsonRequest request;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;
    private RequestManager.ResponseCallBack mCallBack;
    private RequestManager.Controller mController;
    private Map<String, String> mParams;
    private String mTag;

    VolleyBuilder(RequestManager.Controller controller) {
        mController = controller;
    }

    public VolleyBuilder(RequestManager.Controller controller, RequestManager requestManager) {
        mController = controller;
        mController = requestManager.new Controller();
    }

    protected GsonRequest getRequest() {
        return request;
    }

    @Override
    public Builder GET() {
        method = GET;
        return this;
    }

    @Override
    public Builder POST() {
        method = POST;
        return this;
    }

    @Override
    public Builder URL(String s) {
        url = s;
        return this;
    }

    @Override
    public Builder setParams(Map<String, String> params) {
        mParams = params;
        return this;
    }

    @Override
    public Builder setTag(String tag) {
        mTag = tag;
        return this;
    }

    @Override
    public <T> Builder addCallBack(@NonNull final RequestManager.ResponseCallBack<T> callBack) {

        mCallBack = callBack;

        mListener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                mCallBack.onResponse(response);
            }
        };

        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mCallBack.onErrorResponse(error);
            }
        };

//        mListener = new Listener<>(callBack);
//        mErrorListener = new ErrorListener(callBack);
        return this;
    }

    private static class Listener<T> implements Response.Listener<T> {

        private WeakReference<RequestManager.ResponseCallBack<T>> weakReference;

        public Listener(RequestManager.ResponseCallBack<T> callBack) {
            this.weakReference = new WeakReference<>(callBack);
        }

        @Override
        public void onResponse(T response) {
            final RequestManager.ResponseCallBack<T> callBack = weakReference.get();
            if (callBack != null) {
                callBack.onResponse(response);
            }
        }
    }

    private static class ErrorListener implements Response.ErrorListener {

        private WeakReference<RequestManager.ResponseCallBack> weakReference;

        public ErrorListener(RequestManager.ResponseCallBack callBack) {
            this.weakReference = new WeakReference<>(callBack);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            final RequestManager.ResponseCallBack callBack = weakReference.get();
            if (callBack != null) {
                callBack.onErrorResponse(error);
            }
        }
    }


    @Override
    public RequestManager.Controller create() {
        return create(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> RequestManager.Controller create(Class<T> tClass) {
        assertNotNull();
        request = new GsonRequest<T>(method, url, mListener, mErrorListener, tClass) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mParams;
            }
        };
        if (mTag != null)
            request.setTag(mTag);
//        自动启动请求
        mController.start(request);
        return mController;
    }

    @Override
    void reset() {
        method = GET;
        url = null;
        mParams = null;
        mTag = null;
        mCallBack = null;
    }

    @Override
    void assertNotNull() {
        if (url == null)
            throw new IllegalArgumentException("Url must not null");

        if (mCallBack == null)
            throw new IllegalArgumentException("The Listener of the ResponseCallBack must not null");

        if (method == Request.Method.POST) {
            if (mParams == null) {
                throw new IllegalArgumentException("Params must not null");
            }
        }
    }


}
