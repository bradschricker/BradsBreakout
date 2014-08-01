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
 * Created by localadmin on 7/30/14.
 */
public class Platform extends View {

    private RectF mPlatformBounds;
    private int mScreenHeight;
    private int mScreenWidth;
    private Paint mPaint;

    public Platform(Context context) {
        super(context);
        mPlatformBounds = new RectF();
        mPaint = new Paint();
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        mPlatformBounds.set((mScreenWidth / 2) - 75, mScreenHeight - 120, (mScreenWidth / 2) + 75, mScreenHeight - 100);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mPlatformBounds, mPaint);
    }


    public float getPlatformTopBound() {
        return mPlatformBounds.top;
    }

    public float getPlatformRightBound() {
        return mPlatformBounds.right;
    }

    public float getPlatformBottomBound() {
        return mPlatformBounds.bottom;
    }

    public float getPlatformLeftBound() {
        return mPlatformBounds.left;
    }

    public void setPlatformBounds(float left, float right) {
        mPlatformBounds.set(left, mScreenHeight - 120, right, mScreenHeight - 100);
    }

}
