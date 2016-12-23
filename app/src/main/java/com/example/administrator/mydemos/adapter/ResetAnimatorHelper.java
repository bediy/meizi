package com.example.administrator.mydemos.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.View;

/**
 * Created by Administrator on 2016/7/6.
 */
public final class ResetAnimatorHelper {
    public static void clear(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null).setStartDelay(0);
    }

    public static void resetZ(View v, float value) {
        ViewCompat.setTranslationZ(v, value);
//        ViewCompat.setElevation(v, value);
    }

    public static void resetCardElevation(CardView v, float value) {
        v.setCardElevation(value);
    }
}
