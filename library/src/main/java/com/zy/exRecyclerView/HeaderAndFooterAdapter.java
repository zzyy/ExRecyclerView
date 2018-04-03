package com.zy.exRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.zy.exRecyclerView.check.Check.checkNotNull;


/**
 * RecyclerView.Adapter with Header and Footer
 */
public class HeaderAndFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_FOOTER_VIEW_TYPE_START = Integer.MIN_VALUE;

    private final RecyclerView.Adapter mOriginalAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    /*private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };*/

    public HeaderAndFooterAdapter(@NonNull RecyclerView.Adapter originalAdapter) {
        checkNotNull(originalAdapter);
        mOriginalAdapter = originalAdapter;

//        mOriginalAdapter.registerAdapterDataObserver(mDataObserver);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerViewsCount = this.getHeaderViewsCount();
        int footerViewsCount = this.getFooterViewsCount();
        if (viewType < HEADER_FOOTER_VIEW_TYPE_START + headerViewsCount) {
            return new CommonViewHolder(mHeaderViews.get(viewType - HEADER_FOOTER_VIEW_TYPE_START));
        } else if (viewType < HEADER_FOOTER_VIEW_TYPE_START + headerViewsCount + footerViewsCount) {
            return new CommonViewHolder(mHeaderViews.get(viewType - HEADER_FOOTER_VIEW_TYPE_START - headerViewsCount));
        } else {
            int originalViewType = viewType - headerViewsCount - footerViewsCount;
            return mOriginalAdapter.onCreateViewHolder(parent, originalViewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCount = this.getHeaderViewsCount();
        int originalCount = mOriginalAdapter.getItemCount();
        if (position < headerViewsCount) {
            this.setHeadAndFooterViewFullHorizon(holder);
            this.onBindHeaderViewHolder((CommonViewHolder) holder, position);
        } else if (position < headerViewsCount + originalCount) {
            mOriginalAdapter.bindViewHolder(holder, position - headerViewsCount);
        } else {
            this.setHeadAndFooterViewFullHorizon(holder);
            this.onBindFooterViewHolder((CommonViewHolder) holder, position);
        }
    }

    protected void onBindHeaderViewHolder(CommonViewHolder holder, int position) {
    }

    protected void onBindFooterViewHolder(CommonViewHolder holder, int position) {
    }

    private void setHeadAndFooterViewFullHorizon(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }


    @Override
    public int getItemViewType(int position) {
        // 为每个header footer view都分配一个 viewType;
        int headerViewsCount = this.getHeaderViewsCount();
        int footerViewsCount = this.getFooterViewsCount();
        int originalCount = mOriginalAdapter.getItemCount();
        // [HEADER_FOOTER_VIEW_TYPE_START, HEADER_FOOTER_VIEW_TYPE_START + headerViewsCount) 为 header
        if (position < headerViewsCount) {
            return HEADER_FOOTER_VIEW_TYPE_START + position;
        } else if (position < headerViewsCount + originalCount) {
            // 使默认的 itemViewType 不和header footer的重复
            int originalItemViewType = mOriginalAdapter.getItemViewType(position - headerViewsCount);
            return originalItemViewType + headerViewsCount + footerViewsCount;
        } else {
            // [HEADER_FOOTER_VIEW_TYPE_START + headerViewsCount, HEADER_FOOTER_VIEW_TYPE_START + headerViewsCount + footerViewsCount) 为 footer
            return HEADER_FOOTER_VIEW_TYPE_START + position - originalCount;
        }
    }

    //    ==============================
    @Override
    public int getItemCount() {
        return mOriginalAdapter.getItemCount() + getHeaderViewsCount() + getFooterViewsCount();
    }

    public RecyclerView.Adapter getOriginalAdapter() {
        return mOriginalAdapter;
    }

    public void addHeaderView(View header) {
        checkNotNull(header, "header must not null");
        mHeaderViews.add(header);
        notifyDataSetChanged();
    }

    public void removeHeaderView(View header) {
        checkNotNull(header, "header must not null");
        mHeaderViews.remove(header);
        notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        checkNotNull(footer, "footer must not null");
        mFooterViews.add(footer);
        notifyDataSetChanged();
    }

    public void removeFooterView(View footer) {
        checkNotNull(footer, "footer must not null");
        mFooterViews.remove(footer);
        notifyDataSetChanged();
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

}
