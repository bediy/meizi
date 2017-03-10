package com.example.administrator.mydemos.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.mydemos.R;
import com.example.administrator.mydemos.model.Results;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/7/0007.
 */

public class AllDataRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "AllDataRVAdapter";

    private final static int TYPE_NORMAL = 0;
    private final static int TYPE_FOOT = 1;
    public final static int LOAD_LOADING = 0;
    public final static int LOAD_NO_MORE = 1;

    private AllDataRVAdapter.FootViewHolder footViewHolder;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private List<Results> mList;
    private Context mContext;
    private int mLayoutId = R.layout.item_view_all_data;
    private boolean isFirstOnly = true;
    private int mLastPosition = -1;
    private long mDuration = 500;
    private long mStartDelay = 500;
    private float bgViewE = -1;
    private float imageViewE = -1;
    private AllDataRVAdapter.onItemClickListener onItemClickListener;

    public AllDataRVAdapter(List<Results> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
            footViewHolder = new AllDataRVAdapter.FootViewHolder(view);
            return footViewHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new AllDataRVAdapter.NormalViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AllDataRVAdapter.NormalViewHolder) {
            AllDataRVAdapter.NormalViewHolder viewHolder = (AllDataRVAdapter.NormalViewHolder) holder;
            Glide.with(mContext)
                    .load(mList.get(position).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.imageView);
            viewHolder.imageView.setTransitionName(mList.get(position).getUrl());
            viewHolder.imageView.setTag(R.id.imageView, position);
            viewHolder.descTextView.setText(mList.get(position).getDesc());
            viewHolder.timeTextView.setText(mList.get(position).getPublishedAt());

//            setAnimator(viewHolder);
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAnimator(final AllDataRVAdapter.NormalViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();

        if (bgViewE == -1) {
            bgViewE = holder.backgroundView.getElevation();
            imageViewE = holder.imageView.getElevation();
        }

        if (!isFirstOnly || adapterPosition > mLastPosition) {
            holder.imageView.setElevation(0);
            holder.backgroundView.setElevation(0);
            for (Animator anim : createAnimators(holder)) {
                anim.setDuration(mDuration);
                anim.setInterpolator(mInterpolator);
                anim.setStartDelay(mStartDelay);
                anim.start();
            }
            mLastPosition = adapterPosition;
        }
    }


    protected Animator[] createAnimators(AllDataRVAdapter.NormalViewHolder holder) {
        ObjectAnimator imageAnim, cardAnim;
        Animator[] animators = new Animator[]{
                imageAnim = ObjectAnimator.ofFloat(holder.imageView, "elevation", 0, imageViewE),
                cardAnim = ObjectAnimator.ofFloat(holder.backgroundView, "elevation", 0, bgViewE)
        };
        holder.imageView.setTag(imageAnim);
        holder.backgroundView.setTag(cardAnim);
        return animators;
    }

    /*@Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof AllDataRVAdapter.NormalViewHolder) {
            AllDataRVAdapter.NormalViewHolder viewHolder = (AllDataRVAdapter.NormalViewHolder) holder;
            ((ObjectAnimator) viewHolder.backgroundView.getTag()).end();
            ((ObjectAnimator) viewHolder.imageView.getTag()).end();
        }
    }*/

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public void notifyDataSetInserted() {
        this.notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterViewStatus(int status) {
        switch (status) {
            case LOAD_LOADING:
                footViewHolder.progressBar.setVisibility(View.VISIBLE);
                footViewHolder.textView.setVisibility(View.GONE);
                break;
            case LOAD_NO_MORE:
                footViewHolder.progressBar.setVisibility(View.GONE);
                footViewHolder.textView.setVisibility(View.VISIBLE);
        }
    }


    public class NormalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.descTextView)
        TextView descTextView;
        @BindView(R.id.timeTextView)
        TextView timeTextView;
        @BindView(R.id.backgroundView)
        FrameLayout backgroundView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public View getImageView() {
            return imageView;
        }

        @OnClick({R.id.backgroundView, R.id.imageView})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.backgroundView:
                    if (onItemClickListener != null) {
                        onItemClickListener.onDescClickListener(getAdapterPosition(), view);
                    }
                    break;
                case R.id.imageView:
                    if (onItemClickListener != null) {
                        onItemClickListener.onImageClickListener(getAdapterPosition(), view);
                    }
                    break;
            }
        }

    }


    static class FootViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.textView)
        TextView textView;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setOnItemClickListener(AllDataRVAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onDescClickListener(int position, View view);
        void onImageClickListener(int position, View view);
    }


}
