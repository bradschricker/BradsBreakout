package com.bignerdranch.bradsbreakout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class BradsBreakoutActivity extends Activity implements View.OnTouchListener {

    private static final int BLOCK_ROWS = 4;
    private static final int BLOCKS_PER_ROW = 12;

    private static Context mContext;
    private static Ball mBall;
    private static Platform mPlatform;
    private static Block mBlocks[] = new Block[BLOCKS_PER_ROW * BLOCK_ROWS];
    private static ScoreBoard mScoreBoard;
    private static int mScore;
    private static int mNumBlocksVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNumBlocksVisible = 48;
        mContext = this;
        setContentView(R.layout.activity_brads_breakout);
        mBall = new Ball(this);
        mBall.setBackgroundColor(Color.BLACK);
        setContentView(mBall);
        mPlatform = new Platform(this);
        addContentView(mPlatform, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPlatform.setOnTouchListener(this);
        allocateAndDrawBlocks();
        mScore = 0;
        mScoreBoard = new ScoreBoard(this);
        mScoreBoard.updateScore(mScore);
        addContentView(mScoreBoard, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.brads_breakout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_MOVE:
                mPlatform.setPlatformBounds(motionEvent.getX() - 75, motionEvent.getX() + 75);
                break;
        }
        mPlatform.invalidate();
        return true;
    }

    public static boolean detectBallPaddleCollision() {
        if ((mBall.getXCoordinate() > mPlatform.getPlatformLeftBound()) && (mBall.getXCoordinate() < mPlatform.getPlatformRightBound()) &&
                (mBall.getYCoordinate() > mPlatform.getPlatformTopBound() - 40) && (mBall.getYCoordinate() < mPlatform.getPlatformTopBound() + 10)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean detectBallBlockCollisionTopAndBottom() {
        for (int i = (BLOCKS_PER_ROW * BLOCK_ROWS - 1); i >= 0; i--) {
            if (mBall.getYVelocity() < 0) {
                if ((mBall.getXCoordinate() > mBlocks[i].getBlockLeftBound()) && (mBall.getXCoordinate() < mBlocks[i].getBlockRightBound()) &&
                        (mBall.getYCoordinate() > mBlocks[i].getBlockBottomBound() - 10) && (mBall.getYCoordinate() < mBlocks[i].getBlockBottomBound()) &&
                        (mBlocks[i].getIsAlive())) {
                    mBlocks[i].setIsAlive(false);
                    mBlocks[i].setInvisible();
                    updateVisibleBlocks();
                    updateScore(i);
                    return true;
                }
            } else {
                if ((mBall.getXCoordinate() > mBlocks[i].getBlockLeftBound()) && (mBall.getXCoordinate() < mBlocks[i].getBlockRightBound()) &&
                        (mBall.getYCoordinate() > mBlocks[i].getBlockTopBound() - 40) && (mBall.getYCoordinate() < mBlocks[i].getBlockTopBound() + 10) &&
                        (mBlocks[i].getIsAlive())) {
                    mBlocks[i].setIsAlive(false);
                    mBlocks[i].setInvisible();
                    updateVisibleBlocks();
                    updateScore(i);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean detectBallBlockCollisionRightAndLeft() {
        for (int i = (BLOCKS_PER_ROW * BLOCK_ROWS - 1); i >= 0; i--) {
            if (mBall.getXVelocity() < 0) {
                if ((mBall.getYCoordinate() > mBlocks[i].getBlockTopBound()) && (mBall.getYCoordinate() < mBlocks[i].getBlockBottomBound()) &&
                        (mBall.getXCoordinate() < mBlocks[i].getBlockRightBound() + 10) && (mBall.getXCoordinate() > mBlocks[i].getBlockRightBound()) &&
                        (mBlocks[i].getIsAlive())) {
                    mBlocks[i].setIsAlive(false);
                    mBlocks[i].setInvisible();
                    updateVisibleBlocks();
                    updateScore(i);
                    return true;
                }
            } else {
                if ((mBall.getYCoordinate() > mBlocks[i].getBlockTopBound()) && (mBall.getYCoordinate() < mBlocks[i].getBlockBottomBound()) &&
                        (mBall.getXCoordinate() > mBlocks[i].getBlockLeftBound() - 10) && (mBall.getXCoordinate() < mBlocks[i].getBlockLeftBound()) &&
                        (mBlocks[i].getIsAlive())) {
                    mBlocks[i].setIsAlive(false);
                    mBlocks[i].setInvisible();
                    updateVisibleBlocks();
                    updateScore(i);
                    return true;
                }
            }
        }
        return false;
    }

    public static void updateScore(int i) {
        if ((i <= 47) && (i > 35)) {
            mScore = mScore + 50;
        } else if ((i <= 35) && (i > 23)) {
            mScore = mScore + 100;
        } else if ((i <= 23) && (i > 11)) {
            mScore = mScore + 200;
        } else {
            mScore = mScore + 500;
        }

        mScoreBoard.updateScore(mScore);
    }

    public void allocateAndDrawBlocks() {
        for (int i = 0; i < (BLOCKS_PER_ROW * BLOCK_ROWS); i++) {
            mBlocks[i] = new Block(this);
            mBlocks[i].setBlockBounds(i);
            addContentView(mBlocks[i], new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public static void updateVisibleBlocks() {
        mNumBlocksVisible--;
        if (mNumBlocksVisible == 0) {
            mBall.setXVelocity(0.0f);
            mBall.setYVelocity(0.0f);
            gameWon();
        }
    }

    public static void gameWon() {
        Toast.makeText(mContext, R.string.game_won, Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(mContext, mContext.getClass());
                mContext.startActivity(intent);
            }
        }, 10000);
    }

    public static void gameLost() {
        Toast.makeText(mContext, R.string.game_lost, Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(mContext, mContext.getClass());
                mContext.startActivity(intent);
            }
        }, 10000);
    }
}
