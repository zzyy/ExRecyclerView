package com.zy.exRecyclerView.click;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 监听item的点击事件
 *
 * Created by Simon on 2016/11/19.
 */

public abstract class ClickItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    GestureDetectorCompat mGestureDetector;

    @Override
    public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {

        if (mGestureDetector == null){
            mGestureDetector = new GestureDetectorCompat(rv.getContext(), new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View itemView = rv.findChildViewUnder( e.getX(), e.getY() );

                    onItemClick( rv, itemView, rv.getChildAdapterPosition( itemView )  );

                    return true;
                }

            });
        }

        mGestureDetector.onTouchEvent( e );


        return false;
    }

    public abstract void onItemClick(RecyclerView rv, View itemView, int position);

}
