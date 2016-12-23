package com.example.administrator.mydemos.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/1.
 */
public class PagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private final Context mContext;
    private final ViewPager mViewPager;
    private ArrayList mList;
    private final ArrayList<FMInfo> mTabs = new ArrayList<FMInfo>();

    static final class FMInfo {
        private final Class<?> clss;
        private final Bundle args;

        FMInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }

    public PagerAdapter(AppCompatActivity activity, ViewPager pager, ArrayList list) {
        super(activity.getSupportFragmentManager());
        mContext = activity;
        mViewPager = pager;
        mList = list;
        mViewPager.setAdapter(this);
        mViewPager.addOnPageChangeListener(this);
    }

    /*public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
        FMInfo info = new FMInfo(clss, args);
        tab.setTag(info);
        tab.setTabListener(this);
        mTabs.add(info);
        mActionBar.addTab(tab);
        notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        FMInfo info = mTabs.get(position);
        return Fragment.instantiate(mContext, info.clss.getName(), info.args);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
