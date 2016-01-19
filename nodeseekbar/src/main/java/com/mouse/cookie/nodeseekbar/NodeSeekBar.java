package com.mouse.cookie.nodeseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cookie on 2016/1/18.
 */
public class NodeSeekBar extends View {

    private final static int VERTICAL = 0, HORIENZONTAL = 1;

    private OnProgressChangedListener listener;

    private int nodeNumber;
    private int progress;
    private int cycleRadius;
    private int cycleBackgroundColorBefore;
    private int cycleBackgroundColorAfter;
    private Drawable cycleBackgroundBefore;
    private Drawable cycleBackgroundAfter;
    private int orientation;    //方向

    private Paint mPaint;

    int mWidth, mHeight;

    public NodeSeekBar(Context context) {
        this(context, null);
    }

    public NodeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        listener = new OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
//                do something
            }
        };

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.NodeSeekBar);

        int count = mTypedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = mTypedArray.getIndex(i);
            if (R.styleable.NodeSeekBar_nodeNumber == attr) {
                nodeNumber = mTypedArray.getInt(attr, 1);
            } else if (R.styleable.NodeSeekBar_progress == attr) {
                progress = mTypedArray.getInt(attr, 1);
            } else if (R.styleable.NodeSeekBar_cycleRadius == attr) {
                cycleRadius = mTypedArray.getInt(attr, 10);
            } else if (R.styleable.NodeSeekBar_cycleBackgroundColorBefore == attr) {
                cycleBackgroundColorBefore = mTypedArray.getColor(attr, Color.WHITE);
            } else if (R.styleable.NodeSeekBar_cycleBackgroundColorAfter == attr) {
                cycleBackgroundColorAfter = mTypedArray.getColor(attr, Color.WHITE);
            } else if (R.styleable.NodeSeekBar_orientation == attr) {
                orientation = mTypedArray.getInt(attr, VERTICAL);
            } else if (R.styleable.NodeSeekBar_cycleBackgroundBefore == attr) {
                cycleBackgroundBefore = mTypedArray.getDrawable(attr);
            } else if (R.styleable.NodeSeekBar_cycleBackgroundAfter == attr) {
                cycleBackgroundAfter = mTypedArray.getDrawable(attr);
            }
        }

        mTypedArray.recycle();

        //初始化必要数据
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = getPaddingLeft() + getPaddingRight() + cycleRadius * 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = getPaddingTop() + getPaddingBottom() + cycleRadius * 2;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (orientation == VERTICAL) {
            //坚直方向
            drawVertical(canvas);
        } else {
            //水平方向
            drawHorienzontal(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventPosition;
        if (orientation == VERTICAL) {
            eventPosition = (int) (mHeight - event.getY());
        } else {
            eventPosition = (int) event.getX();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                progress = getProgress(eventPosition);
                Log.e("Tag_dispatch", "DOWN:" + progress);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                progress = getProgress(eventPosition);
                Log.e("Tag_dispatch", "MOVE:" + progress);
                break;
            }
            case MotionEvent.ACTION_UP: {
                progress = getProgress(eventPosition);
                Log.e("Tag_dispatch", "UP:" + progress);
                break;
            }
            default: {
                Log.e("Tag_NodeSeekBar", "default");
                break;
            }
        }

        //通知重绘
        invalidate();

        //调用回调函数
        listener.onProgressChanged(progress);

        return true;
    }

    private int getProgress(int eventPosition) {
        if (orientation == VERTICAL) {
            if (eventPosition <= mHeight && eventPosition > 0) {
                return eventPosition * nodeNumber / mHeight + 1;
            } else if (eventPosition <= 0) {
                return 1;
            } else {
                return nodeNumber;
            }
        } else {
            if (eventPosition <= mWidth && eventPosition > 0) {
                return eventPosition * nodeNumber / mWidth + 1;
            } else if (eventPosition <= 0) {
                return 1;
            } else {
                return nodeNumber;
            }
        }
    }

    //坚直方向绘制
    private void drawVertical(Canvas canvas) {
        if (nodeNumber > 1) {
            int lineHeight = (mHeight - (cycleRadius * 2 * nodeNumber)) / (nodeNumber - 1);
            Log.e("Tag_LineHeight", "" + lineHeight);
            Log.e("Tag_mHeight", "" + mHeight);
            Log.e("Tag_radius", "" + cycleRadius);
            Log.e("Tag_nodeNumber", "" + nodeNumber);

            for (int i = 0; i < nodeNumber; i++) {
                if (i < progress) {
                    mPaint.setColor(cycleBackgroundColorAfter);

                    //节点处作画
                    if (null != cycleBackgroundAfter) {
                        cycleBackgroundAfter.draw(canvas);
                    }

                } else {
                    mPaint.setColor(cycleBackgroundColorBefore);
                }

                //作圆
                canvas.drawCircle(mWidth / 2, mHeight - ((2 * i + 1) * cycleRadius + i * lineHeight), cycleRadius,
                        mPaint);
                Log.e("Tag_Point", "圆心(" + mWidth / 2 + "," + (mHeight - ((2 * i + 1) * cycleRadius + i * lineHeight)
                ) + ")<----" + i);

                //作线
                mPaint.setStrokeWidth(cycleRadius / 3);
                canvas.drawLine(mWidth / 2, (mHeight - ((2 * i) * cycleRadius + (i - 1) * lineHeight)),
                        mWidth / 2, mHeight - ((2 * i + 1) * cycleRadius + i * lineHeight), mPaint);
            }

        } else {
            mPaint.setColor(cycleBackgroundColorBefore);
            canvas.drawCircle(mWidth / 2, mHeight / 2, cycleRadius, mPaint);
            return;
        }
    }

    //水平方向绘制
    private void drawHorienzontal(Canvas canvas) {

        if (nodeNumber > 1) {
            int lineWidth = (mWidth - (cycleRadius * 2 * nodeNumber)) / (nodeNumber - 1);
            Log.e("Tag_LineHeight", "" + lineWidth);
            Log.e("Tag_mHeight", "" + mWidth);
            Log.e("Tag_radius", "" + cycleRadius);
            Log.e("Tag_nodeNumber", "" + nodeNumber);

            for (int i = 0; i < nodeNumber; i++) {
                if (i < progress) {
                    mPaint.setColor(cycleBackgroundColorAfter);
                } else {
                    mPaint.setColor(cycleBackgroundColorBefore);
                }

                //作圆
//                canvas.drawCircle(mWidth - ((2 * i + 1) * cycleRadius + i * lineWidth), mHeight / 2, cycleRadius,
//                        mPaint);
                canvas.drawCircle((2 * i + 1) * cycleRadius + i * lineWidth, mHeight / 2, cycleRadius,
                        mPaint);
                Log.e("Tag_Point", "圆心(" + ((2 * i + 1) * cycleRadius + i * lineWidth) + "," + mHeight / 2
                        + ")<----" + i);

                //作线
                mPaint.setStrokeWidth(cycleRadius / 3);
                canvas.drawLine(((2 * i) * cycleRadius + (i - 1) * lineWidth), mHeight / 2,
                        (2 * i + 1) * cycleRadius + i * lineWidth, mHeight / 2, mPaint);
            }

        } else {
            mPaint.setColor(cycleBackgroundColorBefore);
            canvas.drawCircle(mWidth / 2, mHeight / 2, cycleRadius, mPaint);
            return;
        }
    }

    //获取当前进度
    public int getProgress() {
        return progress;
    }

    //设置进度改变接口
    public void setOnProgressChangedListener(OnProgressChangedListener listener) {
        this.listener = listener;
    }

    //进度改变接口
    public interface OnProgressChangedListener {
        void onProgressChanged(int progress);
    }
}
