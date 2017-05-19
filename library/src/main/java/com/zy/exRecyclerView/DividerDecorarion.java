package com.zy.exRecyclerView;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Simon on 2016/11/16.
 */

public class DividerDecorarion extends RecyclerView.ItemDecoration {
    private int mLeftOffset;
    private int mTopOffset;
    private int mRightOffset;
    private int mBottomOffset;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mLeftOffset, mTopOffset, mRightOffset, mBottomOffset);
    }

    public DividerDecorarion(int mLeftOffset, int mTopOffset, int mRightOffset,  int mBottomOffset) {
        this.mBottomOffset = mBottomOffset;
        this.mLeftOffset = mLeftOffset;
        this.mRightOffset = mRightOffset;
        this.mTopOffset = mTopOffset;
    }

    // 在recyclerView的子view绘制之前调用
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
