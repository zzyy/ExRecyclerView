package com.zy.exRecyclerView;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 通用的ViewHolder
 *
 * Created by Simon on 2016/8/18.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    public CommonViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>(8);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView( @IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
