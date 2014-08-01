package com.bignerdranch.bradsbreakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by localadmin on 8/1/14.
 */
public class ScoreBoard extends TextView {

    private Paint mScorePaint;
    private int mGameScore;

    public ScoreBoard (Context context) {
        super(context);
        mScorePaint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        mScorePaint.setColor(Color.WHITE);
        mScorePaint.setTextSize(32.0f);
        mScorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("Score: " + mGameScore, (float) 50.0, (float) 50.0, mScorePaint);
    }

    public void updateScore(int score) {
        invalidate();
        mGameScore = score;
    }

}
