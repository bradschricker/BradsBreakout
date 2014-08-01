package com.bignerdranch.bradsbreakout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.View;

import java.util.Random;

/**
 * Created by localadmin on 7/30/14.
 */
public class Ball extends View {

    private Context mContext;
    private long mSeed;
    private Random mRandom;
    private int mXMin;
    private int mXMax;
    private int mYMin;
    private int mYMax;
    private float mRadius;
    private float mXCoordinate;
    private float mYCoordinate;
    private float mXVelocity;
    private float mYVelocity;
    private RectF mBallBounds;
    private Bitmap mBradBall;
    private Paint mPaint;
    private MediaPlayer mMediaPlayer;

    public Ball(Context context) {
        super(context);
        mContext = context;
        long mSeed = System.currentTimeMillis();
        mRandom = new Random(mSeed);
        mXMax = 0;
        mYMin = 0;
        mRadius = 20;
        mXCoordinate = mRadius + 400;
        mYCoordinate = mRadius + 400;
        mXVelocity = mRandom.nextInt(3);
        mYVelocity = mRandom.nextInt(4);
        if (mYVelocity < 2) {
            mYVelocity = 2;
        }
        mBallBounds = new RectF();

        mBradBall = BitmapFactory.decodeResource(getResources(), R.drawable.brad_ball);
        mPaint = new Paint();

        mMediaPlayer = MediaPlayer.create(mContext, R.raw.oww);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBallBounds.set(mXCoordinate - mRadius, mYCoordinate - mRadius, mXCoordinate + mRadius, mYCoordinate + mRadius);
        canvas.drawBitmap(mBradBall, mXCoordinate, mYCoordinate, mPaint);

        if ((mXVelocity != 0) && (mYVelocity !=0)) {
            update();
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) { }

        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        mXMax = w-1;
        mYMax = h-1;
    }

    private void update() {
        mXCoordinate += mXVelocity;
        mYCoordinate += mYVelocity;

        if (BradsBreakoutActivity.detectBallPaddleCollision()) {
            mYVelocity = (float) 1.07 * (-mYVelocity);
            mXVelocity = (float) 1.07 * (mXVelocity);
            playOww();
        } else if (BradsBreakoutActivity.detectBallBlockCollisionTopAndBottom()) {
            mYVelocity = -mYVelocity;
            playOww();
        } else if (BradsBreakoutActivity.detectBallBlockCollisionRightAndLeft()) {
            mXVelocity = -mXVelocity;
            playOww();
        } else if (mXCoordinate + mRadius > mXMax) {
            mXVelocity = -mXVelocity;
            mXCoordinate = mXMax - mRadius;
            playOww();
        } else if (mXCoordinate - mRadius < mXMin) {
            mXVelocity = -mXVelocity;
            mXCoordinate = mXMin + mRadius;
            playOww();
        } else if (mYCoordinate + mRadius > mYMax) {
            mXVelocity = 0;
            mYVelocity = 0;
            BradsBreakoutActivity.gameLost();
        } else if (mYCoordinate - mRadius < mYMin) {
            mYVelocity = -mYVelocity;
            mYCoordinate = mYMin + mRadius;
            playOww();
        }
    }

    public void playOww() {
        mMediaPlayer.start();
    }

    public float getXCoordinate() {
        return mXCoordinate;
    }

    public float getYCoordinate() {
        return mYCoordinate;
    }

    public float getXVelocity() {
        return mXVelocity;
    }

    public void setXVelocity(float xVelocity) {
        mXVelocity = xVelocity;
    }

    public float getYVelocity() {
        return mYVelocity;
    }

    public void setYVelocity(float yVelocity) {
        mYVelocity = yVelocity;
    }
}
