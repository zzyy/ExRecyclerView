package com.zy.exRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 封装通用ViewHolder的adapter
 * <p>
 * Created by Simon on 2017/5/19.
 */

public abstract class HolderAdapter extends RecyclerView.Adapter<CommonRecyclerHolder> {
    @Override
    public CommonRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonRecyclerHolder(this.onCreateView(parent, viewType));
    }

    public abstract View onCreateView(ViewGroup parent, int viewType);

}
