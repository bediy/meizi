package com.example.administrator.mydemos.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
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
 * Created by Administrator on 2016/3/7.
 */
public class AllDataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "AllDataRecyclerViewAdapter";

    private final static int TYPE_NORMAL = 0;
    private final static int TYPE_FOOT = 1;
    public final static int LOAD_LOADING = 0;
    public final static int LOAD_NO_MORE = 1;

    private FootViewHolder footViewHolder;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private List<Results> mList;
    private Context mContext;
    private int mLayoutId = R.layout.item_view_all_data_rl;
    private boolean isFirstOnly = true;
    private int mLastPosition = -1;
    private long mDuration = 500;
    private long mStartDelay = 500;
    private float cardViewE = -1;
    private float imageCardViewE = -1;
    private onItemClickListener onItemClickListener;

    public AllDataRecyclerViewAdapter(List<Results> list, Context context) {
        mList = list;
        mContext = context;
    }

    public AllDataRecyclerViewAdapter(List<Results> list, Context context, int layoutId) {
        mList = list;
        mContext = context;
        mLayoutId = layoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_view, parent, false);
            footViewHolder = new FootViewHolder(view);
            return footViewHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new NormalViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder viewHolder = (NormalViewHolder) holder;
            Glide.with(mContext)
                    .load(mList.get(position).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.imageView);
            viewHolder.imageView.setTransitionName(mList.get(position).getUrl());
            viewHolder.imageView.setTag(R.id.imageView, position);
            viewHolder.descTextView.setText(mList.get(position).getDesc());
            viewHolder.timeTextView.setText(mList.get(position).getPublishedAt());

            setAnimator(viewHolder);
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAnimator(final NormalViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();

        if (cardViewE == -1) {
            cardViewE = holder.cardView.getCardElevation();
            imageCardViewE = holder.imageCardView.getCardElevation();
        }

        if (!isFirstOnly || adapterPosition > mLastPosition) {
            holder.imageCardView.setCardElevation(0);
            holder.cardView.setCardElevation(0);
            for (Animator anim : createAnimators(holder)) {
                anim.setDuration(mDuration);
                anim.setInterpolator(mInterpolator);
                anim.setStartDelay(mStartDelay);
                anim.start();
            }
            mLastPosition = adapterPosition;
        }
    }


    protected Animator[] createAnimators(NormalViewHolder holder) {
        ObjectAnimator imageAnim, cardAnim;
        Animator[] animators = new Animator[]{
                imageAnim = ObjectAnimator.ofFloat(holder.imageCardView, "cardElevation", 0, imageCardViewE),
                cardAnim = ObjectAnimator.ofFloat(holder.cardView, "cardElevation", 0, cardViewE)
        };
        holder.imageCardView.setTag(imageAnim);
        holder.cardView.setTag(cardAnim);
        return animators;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder viewHolder = (NormalViewHolder) holder;
            ((ObjectAnimator) viewHolder.cardView.getTag()).end();
            ((ObjectAnimator) viewHolder.imageCardView.getTag()).end();
        }
    }

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
        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.imageCardView)
        CardView imageCardView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public View getImageView() {
            return imageView;
        }

        @OnClick({R.id.cardView, R.id.imageView})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cardView:
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


    public void setOnItemClickListener(AllDataRecyclerViewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onDescClickListener(int position, View view);
        void onImageClickListener(int position, View view);
    }


}
