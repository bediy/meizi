package com.example.administrator.mydemos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/9/28/0028.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setupWindowAnimations();
        setContentView();
        setupViews();
    }

    protected void setupWindowAnimations() {

    }

    protected void setupViews() {

    }

    protected void setContentView() {

    }
}
