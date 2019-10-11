package com.example.sber_app5;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FinanceProgressView extends View {
    private int mProgress;
    private int mColor;
    private int mTextSize;

    private final RectF mProgressRect = new RectF(0, 0, 700, 700);
    private final RectF mNeedleRect = new RectF(0, 0, 300, 100);
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mTextBounds = new Rect();
    private static final int DEFAULT_COLOR = Color.RED;
    private static final int MAX_PROGRESS = 360;
    private static final int MAX_ANGLE = 360;
    private static final float STROKE_WIDTH = 40;
    private static final float TEXT_STROKE_WIDTH = 5;
    private static final float LINE_STROKE_WIDTH = 15;

    private static final String TAG = "FinanceProgressView";

    public FinanceProgressView(Context context) {
        super(context);
    }

    public FinanceProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(STROKE_WIDTH / 2, STROKE_WIDTH / 2); /*translate срабатывает на все последующие методы*/
        // canvas.scale(2.0f, 1.0f); растягивает
        // canvas.save() сохраняет состояние канваса до вызова canvas.restore(), который возвращает вью к изначальному состоянию,
        // применяя те изменения, которые были применены межжду save и restore

        canvas.drawArc(mProgressRect, 0f, 360 * 1.0f, false, mCirclePaint);
        final String progressString = mProgress + "км/ч";
        final float textWidth = mTextPaint.measureText(progressString);
        mTextPaint.getTextBounds(progressString, 0, progressString.length(), mTextBounds);
        float x = mProgressRect.width() / 2 - textWidth / 2; /*mProgressRect.width()/2f - mTextBounds.width()/2f - mTextBounds.left;*/
        float y = mProgressRect.height() / 2 - (mTextPaint.ascent() + mTextPaint.descent()) / 2; /*mProgressRect.height()/2f - mTextBounds.height()/2f - mTextBounds.bottom;*/
        canvas.drawText(progressString, x, y, mTextPaint);

        double radius = mProgressRect.height() / 2;
        double angle = (((2 * Math.PI) / MAX_PROGRESS) * mProgress + Math.PI / 2);
        float x_line = mProgressRect.width() / 2 + STROKE_WIDTH / 2;
        float y_line = mProgressRect.height() / 2 + STROKE_WIDTH / 2;
        float x1 = (float)(radius * Math.cos(angle));
        float y1 = (float)(radius * Math.sin(angle));

        canvas.save();
        canvas.rotate(mProgress * 1.0f, x_line, y_line);
        canvas.drawLine(x_line, y_line, x_line, STROKE_WIDTH / 2, mLinePaint);
        canvas.restore();
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs){
        //Достаём атрибуты
        extractAttributes(context, attrs);

        configureCirclePaint();
        configureTextPaint();
        configureLinePaint();
    }

    private void configureCirclePaint() {
        mCirclePaint.setStrokeWidth(STROKE_WIDTH);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(mColor);

        if (mProgress <= 50) {
            mCirclePaint.setColor(getResources().getColor(R.color.green));
        }
        else if (mProgress <= 100) {
            mCirclePaint.setColor(getResources().getColor(R.color.yellow));
        }
        else { mCirclePaint.setColor(getResources().getColor(R.color.red)); }

        invalidate();
    }

    private void configureTextPaint() {
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(TEXT_STROKE_WIDTH);
        mTextPaint.setColor(mColor);
        mTextPaint.setStyle(Paint.Style.FILL);

        if (mProgress <= 50) {
            mTextPaint.setColor(getResources().getColor(R.color.green));
        }
        else if (mProgress <= 100) {
            mTextPaint.setColor(getResources().getColor(R.color.yellow));
        }
        else { mTextPaint.setColor(getResources().getColor(R.color.red)); }

        invalidate();
    }

    private void configureLinePaint() {
        mLinePaint.setStrokeWidth(LINE_STROKE_WIDTH);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    private void extractAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final Resources.Theme theme = context.getTheme();
        final TypedArray typedArray = theme.obtainStyledAttributes(attrs, R.styleable.FinanceProgressView, R.attr.financeProgressStyle, 0);

        try{
            mProgress = typedArray.getInteger(R.styleable.FinanceProgressView_progress,0);
            mColor = typedArray.getColor(R.styleable.FinanceProgressView_color, DEFAULT_COLOR);
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.FinanceProgressView_text_size, getResources().getDimensionPixelSize(R.dimen.defaultTextSize));
            Log.d(TAG, "Progress = " + mProgress + ", Color = " + mColor + ", TextSize = " + mTextSize);
        } finally {
            typedArray.recycle();
        }
    }

    public int getmProgress() {
        return mProgress;
    }

    public int getmColor() {
        return mColor;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }
}
