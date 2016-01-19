package com.mouse.cookie.testview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cookie on 2016/1/18.
 */
public class MyView_2 extends View {

    private Bitmap mImage;
    private int mIamgeScale;
    private String mStringTitleText;
    private int mIntTitleTextColor;
    private int mIntTitleTextSize;

    private Rect mRectBound;
    private Rect mRectImage;
    private Paint mPaint;

    private int mWidth, mHeight;

    public MyView_2(Context context) {
        this(context, null);
    }

    public MyView_2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView_2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView_2);

        int count = mTypedArray.getIndexCount();

        for (int i = 0; i < count; i++) {
            int attr = mTypedArray.getIndex(i);

            switch (attr) {
                case R.styleable.MyView_2_image: {
                    mImage = BitmapFactory.decodeResource(getResources(), mTypedArray.getResourceId(attr, 0));
                    break;
                }
                case R.styleable.MyView_2_imageScaleType: {
                    mIamgeScale = mTypedArray.getInt(attr, 0);
                    break;
                }
                case R.styleable.MyView_2_titleText: {
                    mStringTitleText = mTypedArray.getString(attr);
                    break;
                }
                case R.styleable.MyView_2_titleTextSize: {
                    mIntTitleTextSize = mTypedArray.getDimensionPixelSize(attr, 10);
                    break;
                }
                case R.styleable.MyView_2_textColor: {
                    mIntTitleTextColor = mTypedArray.getColor(attr, Color.GRAY);
                    break;
                }
            }
        }

        mTypedArray.recycle();

        mRectBound = new Rect();
        mPaint = new Paint();
        mPaint.setTextSize(mIntTitleTextSize);
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

        mPaint.setColor(mIntTitleTextColor);
        mPaint.setTextSize(mIntTitleTextSize);
        canvas.drawText(mStringTitleText, getWidth() / 2 - mRectBound.width() / 2, getHeight(), mPaint);

        mRectImage = new Rect();
        mRectImage.left = getPaddingLeft();
        mRectImage.right = mWidth - getPaddingRight();
        mRectImage.top = getPaddingTop();
        mRectImage.bottom = mHeight - getPaddingBottom();

        if (mIamgeScale == 0) {
            canvas.drawBitmap(mImage, null, mRectImage, mPaint);
        }else {
            mRectImage.left = mWidth / 2 - mImage.getWidth() / 2;
            mRectImage.right = mWidth / 2 + mImage.getWidth() / 2;
            mRectImage.top = (mHeight - mRectBound.height()) / 2 - mImage.getHeight() / 2;
            mRectImage.bottom = (mHeight - mRectBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, mRectImage, mPaint);
        }
    }
}
