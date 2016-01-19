package com.mouse.cookie.testview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by cookie on 2016/1/18.
 */
public class MyView extends View {

    //自定义属性
    private String mStringTitleText;
    private int mIntTitleTextColor;
    private int mIntTitleTextSize;

    //画笔及范围
    private Paint mPaint;
    private Rect mRectBound;

    //控件的宽与高
    private int mWidth, mHeight;

    public MyView(Context context) {
        this(context, null);
        Log.e("Tag_MyView", "构造函数_1");
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.e("Tag_MyView", "构造函数_2");
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Log.e("Tag_MyView", "构造函数_3");
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView, defStyleAttr, 0);
        int count = mTypedArray.getIndexCount();
        Log.e("Tag_MyView", "构造函数_count:" + count);
        for (int i = 0; i < count; i++) {
            int attr = mTypedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyView_titleText: {
                    mStringTitleText = mTypedArray.getString(attr);
                    Log.e("Tag_MyView", "构造函数_attr_text:" + mStringTitleText);
                    break;
                }
                case R.styleable.MyView_textColor: {
                    mIntTitleTextColor = mTypedArray.getColor(attr, Color.BLACK);
                    Log.e("Tag_MyView", "构造函数_attr_color:" + mIntTitleTextColor);
                    break;
                }
                case R.styleable.MyView_titleTextSize: {
                    mIntTitleTextSize = mTypedArray.getDimensionPixelSize(attr, 10);
                    Log.e("Tag_MyView", "构造函数_attr_size:" + mIntTitleTextSize);
                    break;
                }
                default: {
                    Log.e("Tag_MyView", "default");
                }
            }
        }
        mTypedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mIntTitleTextSize);
        mRectBound = new Rect();
        mPaint.getTextBounds(mStringTitleText, 0, mStringTitleText.length(), mRectBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mPaint.setTextSize(mIntTitleTextSize);
        mPaint.getTextBounds(mStringTitleText, 0, mStringTitleText.length(), mRectBound);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            float textWidth = mRectBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            mWidth = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            float textHeight = mRectBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            mHeight = desired;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPaint.setColor(Color.WHITE);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mIntTitleTextColor);
        canvas.drawText(mStringTitleText, getWidth() / 2 - mRectBound.width() / 2, getHeight() / 2 + mRectBound
                .height() / 2, mPaint);
        Log.e("Tag_onDraw", "attr_mWidth:" + mWidth);
        Log.e("Tag_onDraw", "attr_mHeight:" + mHeight);
        Log.e("Tag_onDraw", "attr_color:" + mIntTitleTextColor);
        Log.e("Tag_onDraw", "attr_size:" + mIntTitleTextSize);
        Log.e("Tag_onDraw", "attr_text:" + mStringTitleText);
    }
}
