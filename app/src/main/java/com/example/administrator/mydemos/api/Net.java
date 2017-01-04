package com.example.administrator.mydemos.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.administrator.mydemos.Request.GsonRequest;
import com.example.administrator.mydemos.base.App;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class Net<T> {

    private GsonRequest<T> tGsonRequest;


    public Net(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {

    }

    public Net(int method, String url, Type type, final NetCallBack<T> callBack, final Map<String, String> params) {

        Response.Listener listener = new Response.Listener<T>() {

            @Override
            public void onResponse(T response) {
                callBack.onResponse(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onErrorResponse(error);
            }
        };

        this.tGsonRequest = new GsonRequest<T>(method, url, listener, errorListener, null) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };


    }


    public void addToQueue(String tag) {
        tGsonRequest.setTag(tag);
        addToQueue(tGsonRequest);
    }

    public void addToQueue(Request<?> request) {
        App.getContext().getRequestQueue().add(request);
    }

    public void addToQueue(Request<?> request, String tag) {
        request.setTag(tag);
        addToQueue(request);
    }

    public static void cancelAllRequests(String tag) {
        App.getContext().getRequestQueue().cancelAll(tag);
    }

    public interface NetCallBack<T> {
        void onResponse(T response);
        void onErrorResponse(VolleyError error);
    }


}
