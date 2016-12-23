package com.example.administrator.mydemos.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/6/29.
 */
public class VolleyBuilder implements Builder {

    private int method;
    private String url;
    private GsonRequest request;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;
    private RequestManager.Controller mController;

    public VolleyBuilder(RequestManager.Controller controller) {
        mController = controller;
    }
    public VolleyBuilder(RequestManager.Controller controller, RequestManager requestManager) {
        mController = controller;
        mController = requestManager.new Controller();
    }

    @Override
    public Builder GET() {
        method = Request.Method.GET;
        return this;
    }

    @Override
    public Builder POST() {
        method = Request.Method.POST;
        return this;
    }


    @Override
    public <T> Builder addCallBack(final RequestManager.RequestCallBack<T> callBack) {
        mListener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                callBack.onResponse(response);
            }
        };
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onErrorResponse(error);
            }
        };
        return this;
    }


    @Override
    public Builder create() {
        return this;
    }

    @Override
    public <T> RequestManager.Controller create(Class<? super T> aClass) {
        Type type = new TypeToken<Class<? super T>>() {}.getType();
        request = new GsonRequest<T>(method, url, mListener, mErrorListener, type);
        return mController;
    }

    @Override
    public Builder URL(String s) {
        url = s;
        return this;
    }

    protected GsonRequest getRequest() {
        return request;
    }


}
