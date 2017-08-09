package com.sorashiro.chinamapinformation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.sorashiro.chinamapinformation.tool.LogAndToastUtil;

/**
 * Created by GameKing on 2017/8/7.
 */

public class ChinaMapView extends ImageView implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener{

    // 检测两个手指在屏幕上做缩放的手势工具类
    private ScaleGestureDetector mScaleGestureDetector;
    // 图片缩放工具操作类Matrix
    private Matrix mImageMatrix = new Matrix();

    private float mMaxScale;
    private float mBaseScale;

    public ChinaMapView(Context context) {
        super(context, null);
        init(context);
    }

    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public ChinaMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取控件的宽度和高度
        // 获取到ImageView对应图片的宽度和高度
        Drawable d = getDrawable();
        if (null == d) {
            return;
        }
        int dw = d.getIntrinsicWidth();// 图片固有宽度
        int dh = d.getIntrinsicHeight();// 图片固有高度
        float scale = 1.0f;
        if (dw > w && dh > h) {// 图片宽度大于控件宽度且高度大于控件高度
            scale = w * 1.0f / dw;// 缩小一定值
        }
        // 图片宽度大于控件宽度但高度小于控件高度& 图片的宽高都小于控件的宽高
        if ((dw < w && dh < h) || (dw > w && dh < h)) {
            scale = Math.min(w * 1.0f / dw, h * 1.0f / dh);// 按照宽度对应缩放最小值进行缩放
        }
        if (dw < w && dh > h) {// 图片宽度小于控件宽度，但图片高度大于控件高度
            scale = h * 1.0f / dh;// 缩小一定的比例
        }
        mBaseScale = scale;
        mMaxScale = mBaseScale * 10;
        // 将图片移动到手机屏幕的中间位置
        float dx = w / 2 - dw / 2;
        float dy = h / 2 - dh / 2;
        mImageMatrix.postTranslate(dx, dy);
        mImageMatrix.postScale(mBaseScale, mBaseScale, w / 2,
                h / 2);
        setImageMatrix(mImageMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        // 前一个伸缩事件至当前伸缩事件的伸缩比率
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();
        // 控件图片的缩放范围
        if ((scale < mMaxScale && scaleFactor > 1.0f)
                || (scale > mBaseScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mBaseScale) {
                scaleFactor = mBaseScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            // 以屏幕中央位置进行缩放
            // mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2,
            // getHeight() / 2);
            // 以手指所在地方进行缩放
            mImageMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            borderAndCenterCheck();
            setImageMatrix(mImageMatrix);
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        // 一定要返回true才会进入onScale()这个函数
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    private float getScale() {
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    private void borderAndCenterCheck() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 缩放时进行边界检测，防止出现白边
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽度或者高度小于控件的宽或者高；则让其居中
        if (rect.width() < width) {
            deltaX = width / 2f - rect.right + rect.width() / 2f;

        }
        if (rect.height() < height) {
            deltaY = height / 2f - rect.bottom + rect.height() / 2f;
        }
        mImageMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 获得图片放大缩小以后的宽和高
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mImageMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;//这里要return true才行
    }
}
