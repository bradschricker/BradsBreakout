package com.bignerdranch.bradsbreakout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by localadmin on 7/31/14.
 */
public class Block extends View {

    private static final int BLOCK_ROWS = 4;
    private static final int BLOCKS_PER_ROW = 12;

    private RectF mBlockBounds;
    private boolean mIsAlive;
    private int mScreenWidth;
    private float mCellWidth;
    private float mCellHeight;
    private Paint mPaint;

    public Block(Context context){
        super(context);
        mBlockBounds = new RectF();
        mIsAlive = true;
        mPaint = new Paint();
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mCellWidth = mScreenWidth / BLOCKS_PER_ROW;
        mCellHeight = (float) 40.0;
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawRect(mBlockBounds, mPaint);
    }

    public boolean getIsAlive() {
        return mIsAlive;
    }

    public void setIsAlive(boolean isAlive) {
        mIsAlive = isAlive;
    }

    public void setBlockBounds(int index) {
        int row = (index % BLOCKS_PER_ROW);
        int column = (index / BLOCKS_PER_ROW);
        float leftBoundary = (row * mCellWidth) + 5;
        float topBoundary = (column * mCellHeight + 100) + 5;
        float rightBoundary = ((row + 1) * mCellWidth) - 5;
        float bottomBoundary = ((column + 1) * mCellHeight + 100) - 5;
        mBlockBounds.set(leftBoundary, topBoundary, rightBoundary, bottomBoundary);
    }

    public float getBlockTopBound() {
        return mBlockBounds.top;
    }

    public float getBlockRightBound() {
        return mBlockBounds.right;
    }

    public float getBlockBottomBound() {
        return mBlockBounds.bottom;
    }

    public float getBlockLeftBound() {
        return mBlockBounds.left;
    }

    public void setInvisible() {
        this.setVisibility(View.GONE);
    }

}
