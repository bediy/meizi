package com.example.administrator.mydemos.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/19.
 */
public class GsonRequest<T> extends Request<T> {

    private final static String TAG = "request";
    private Response.Listener<T> mListener;
    private Gson mGson;
    private Type mType;

    public GsonRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Gson mGson, Type type) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mGson = mGson;
        this.mType = type;
    }

    public GsonRequest(int method, String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Type type) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mGson = new Gson();
        this.mType = type;
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
