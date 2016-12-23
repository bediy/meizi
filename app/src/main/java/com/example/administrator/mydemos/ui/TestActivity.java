package com.example.administrator.mydemos.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.mydemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/17.
 */
public class TestActivity extends AppCompatActivity {
    /*@BindView(R.id.view_top)
    CardView viewTop;
    @BindView(R.id.view_bottom)
    CardView viewBottom;*/

    float viewTopE;
    float viewBottomE;
    Interpolator interpolator = new AccelerateDecelerateInterpolator();
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageCardView)
    CardView imageCardView;
    @BindView(R.id.descTextView)
    TextView descTextView;
    @BindView(R.id.timeTextView)
    TextView timeTextView;
    @BindView(R.id.cardView)
    CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        viewTopE = imageCardView.getCardElevation();
        viewBottomE = cardView.getCardElevation();
    }



    @OnClick(R.id.cardView)
    public void onClick() {
//        Glide.with(this).load("http://img1.gamersky.com/image2016/07/20160715_djy_248_5/gamersky_03origin_05_201671518126D7.jpg").into(imageView);
        ObjectAnimator topAnim = ObjectAnimator.ofFloat(imageCardView, "cardElevation", 0, viewTopE).setDuration(1000);
        topAnim.setInterpolator(interpolator);
        topAnim.setStartDelay(1000);
        topAnim.start();
        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(cardView, "cardElevation", 0, viewBottomE).setDuration(1000);
        bottomAnim.setInterpolator(interpolator);
        bottomAnim.setStartDelay(1000);
        bottomAnim.start();
    }
}
