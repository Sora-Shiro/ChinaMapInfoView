package com.sorashiro.chinamapinformation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by SoraShiro on 2017/8/7.
 * <p>
 * ScaleGestureDetector refer to ldm, link : http://blog.csdn.net/true100/article/details/51141496
 */

public class ChinaMapView extends ImageView {

    private Matrix mImageMatrix = new Matrix();
    RectF mRectF;     // 图片所在区域
    boolean canDrag    = false;
    PointF  firstPoint = new PointF(0, 0);
    boolean canScale = false;
    PointF scaleFirstPoint = new PointF(0, 0);
    PointF scaleSecondPoint = new PointF(0, 0);

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
        mImageMatrix = new Matrix();
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

        mRectF = new RectF(0, 0, w, h);

        mBaseScale = Math.min(w * 1.0f / dw, h * 1.0f / dh);
        mMaxScale = mBaseScale * 100;
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


    public void onScale(ScaleGestureDetector detector) {
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
            mImageMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());
            borderAndCenterCheck();
            setImageMatrix(mImageMatrix);
        }
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
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mImageMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()); // TODO 这句有用吗
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    // 一个存储所有触摸点 ID 的 List
    LinkedList<Integer> pointerIdList = new LinkedList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIdList.add(event.getPointerId(event.getActionIndex()));
                // 当只有一个手指时，另一个手指按下会触发 ACTION_POINTER_DOWN ，这个时候得到的手指数是 2
                // 当只有一个手指时才能进行拖动
                if (event.getPointerCount() == 1 && mRectF.contains((int) event.getX(), (int) event.getY())) {
                    canDrag = true;
                    firstPoint.set(event.getX(0), event.getY(0));
                } else {
                    canDrag = false;
                }
                if(event.getPointerCount() == 2 && mRectF.contains((int) event.getX(), (int) event.getY())) {
                    canScale = true;
                    scaleFirstPoint.set(event.getX(0), event.getY(0));
                    scaleSecondPoint.set(event.getX(1), event.getY(1));
                } else {
                    canScale = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                pointerIdList.remove(Integer.valueOf(event.getPointerId(event.getActionIndex())));
                // 分辨哪个手指留在最后（不进行该处理会造成“瞬移”现象）：
                // 当只剩两个手指时，其中一个手指抬起触发 ACTION_POINTER_UP ，这个时候得到的手指数还是 2
                // pointerIdList 在这个时候会只剩下一个手指的id
                // 这个时候 event.findPointerIndex(pointerIdList.get(0)) 拿到的
                // 一定是最后一个仍然留在屏幕上的手指
                if (event.getPointerCount() == 2) {
                    canDrag = true;
                    int index = event.findPointerIndex(pointerIdList.get(0));
                    firstPoint.set(event.getX(index), event.getY(index));
                }
                if (event.getPointerCount() <= 2) {
                    canScale = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canDrag) {
                    mImageMatrix.postTranslate(event.getX(0) - firstPoint.x, event.getY(0) - firstPoint.y);
                    firstPoint.set(event.getX(0), event.getY(0));
                    setImageMatrix(mImageMatrix);
                }
                break;
        }

        return true;
    }


}
