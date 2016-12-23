package com.example.administrator.mydemos.api;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2015/9/6.
 */
public class GsonGetRequest<T> extends Request<T> {

    private final static String TAG = "request";
    private int mMethod;
    private Response.Listener<T> mListener;
    private Gson mGson;
    private Type mType;

    public GsonGetRequest(String url, Gson gson, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mGson = gson;
        mType = type;
        mListener = listener;
        Log.e(TAG, url);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>) Response.success(mGson.fromJson(parsed, mType), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
