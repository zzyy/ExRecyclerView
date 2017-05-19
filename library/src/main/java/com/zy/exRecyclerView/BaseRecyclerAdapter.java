package com.zy.exRecyclerView;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 和List整合, 简化构建Adapter的流程
 * <p>
 * Created by Simon on 2016/8/18.
 */
public abstract class BaseRecyclerAdapter<T> extends HolderAdapter {
    protected List<T> mData;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;


    public BaseRecyclerAdapter(List<T> data, @LayoutRes int layoutResId) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public void setData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mData.get(position);
    }


    @Override
    public View onCreateView(ViewGroup parent, int viewType) {
        if (this.mLayoutInflater == null) {
            this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        return mLayoutInflater.inflate(mLayoutResId, parent, false);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}
