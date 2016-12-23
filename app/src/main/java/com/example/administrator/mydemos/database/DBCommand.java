package com.example.administrator.mydemos.database;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/5/17.
 */
public abstract class DBCommand<T> {
    private static final Handler sHANDLER = new Handler(Looper.getMainLooper());
    private static final ExecutorService sDBEngine = Executors.newSingleThreadExecutor();

    public void executor() {
        sDBEngine.execute(new Runnable() {
            @Override
            public void run() {
                postResult(doInBackGround());
            }
        });
    }

    protected void postResult(final T result) {
        sHANDLER.post(new Runnable() {
            @Override
            public void run() {
                onPostExecutor(result);
            }
        });
    }

    protected abstract void onPostExecutor(T result);

    protected abstract T doInBackGround();
}
