package com.zy.exRecyclerView.click;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zy.exRecyclerView.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 为RecyclerView设置每个item的监听事件
 * <p>
 * RecyclerClickSupport.addTo(mRecyclerView).setOnItemClickListener(new RecyclerClickSupport.OnItemClickListener())
 * <p>
 * Created by Simon on 2016/11/19.
 */

public class RecyclerClickSupport {
    //要求设置tag的id 必须是资源文件的id  用来保证唯一..
    private final static int DEFAULT_TAG_NAME = R.string.item_support_id;
    private final static int DEFAULT_TAG_NAME_CHILD = R.string.item_support_id_child;

    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private View.OnClickListener mOnChildClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };


    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(final View view) {
            Set<Integer> keySet = mOnClickListenerMap.keySet();
            for (final Integer itemKey : keySet){
                final View itemView = view.findViewById( itemKey );
                if (itemView != null){
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OnItemChildViewClickListener onItemChildViewClickListener = mOnClickListenerMap.get(itemKey);
                            onItemChildViewClickListener.onItemChildViewClicked(mRecyclerView, mRecyclerView.getChildAdapterPosition(view) ,  itemView);
                        }
                    });
                }
            }

            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private RecyclerClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(DEFAULT_TAG_NAME, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static RecyclerClickSupport addTo(RecyclerView view) {
        RecyclerClickSupport support = (RecyclerClickSupport) view.getTag(DEFAULT_TAG_NAME);
        if (support == null) {
            support = new RecyclerClickSupport(view);
        }
        return support;
    }

    public static RecyclerClickSupport removeFrom(RecyclerView view) {
        RecyclerClickSupport support = (RecyclerClickSupport) view.getTag(DEFAULT_TAG_NAME);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    private Map<Integer, OnItemChildViewClickListener> mOnClickListenerMap = new HashMap<>();

    /**
     * 为每个item中的子view设置点击事件
     * @param id
     * @param listener
     * @return
     */
    public RecyclerClickSupport setChildOnClickListener(@IdRes int id, OnItemChildViewClickListener listener){
        mOnClickListenerMap.put(id, listener);
        return this;
    }

    public RecyclerClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public RecyclerClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(DEFAULT_TAG_NAME, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

    /** 此方法少用  会创建大量的对象 */
    public interface OnItemChildViewClickListener{
        void onItemChildViewClicked(RecyclerView recyclerView, int position, View v);
    }
}
