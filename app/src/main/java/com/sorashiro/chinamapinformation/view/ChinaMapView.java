package com.sorashiro.chinamapinformation.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RegionIterator;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.megatronking.svg.support.SVGDrawable;
import com.sorashiro.chinamapinformation.CnMap;
import com.sorashiro.chinamapinformation.CnSvgBigRenderer;
import com.sorashiro.chinamapinformation.tool.LogAndToastUtil;

import java.util.LinkedList;

/**
 * Created by SoraShiro on 2017/8/7.
 * <p>
 * ScaleGestureDetector refer to ldm, link : http://blog.csdn.net/true100/article/details/51141496
 */

public class ChinaMapView extends ImageView {

    interface ChinaMapViewProvinceListener {
        void onProvinceClick(int i);
    }

    ChinaMapViewProvinceListener mChinaMapViewProvinceListener;

    // 一个存储所有触摸点 ID 的 List
    LinkedList<Integer> pointerIdList = new LinkedList<>();
    boolean             canDrag       = false;
    boolean             canScale      = false;
    // 图片所在区域
    RectF mRectF;
    private Matrix mImageMatrix = new Matrix();
    PointF dragPoint        = new PointF(0, 0);
    PointF scaleFirstPoint  = new PointF(0, 0);
    PointF scaleSecondPoint = new PointF(0, 0);
    int scaleFirstPid;
    int scaleSecondPid;

    private float mBaseScale;
    private float mCurrentScale;
    private float mMaxScale;

    // 用于设定地图信息
    private CnMap mCnMap;
    // 用于获取地图各部分 Region ，之后可能不会设置 get/set 方法
    private CnSvgBigRenderer mCnSvgBigRenderer;

    public ChinaMapView(Context context) {
        super(context, null);
        init();
    }

    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public ChinaMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ChinaMapViewProvinceListener getChinaMapViewProvinceListener() {
        return mChinaMapViewProvinceListener;
    }

    public void setChinaMapViewProvinceListener(ChinaMapViewProvinceListener chinaMapViewProvinceListener) {
        mChinaMapViewProvinceListener = chinaMapViewProvinceListener;
    }

    private void init() {
        super.setScaleType(ScaleType.MATRIX);
        // Important! 非常重要！
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mImageMatrix = new Matrix();
        mCnMap = new CnMap();
        mCnSvgBigRenderer = new CnSvgBigRenderer(getContext(), mCnMap);
        Drawable drawable = new SVGDrawable(mCnSvgBigRenderer);
        setImageDrawable(drawable);
        mMapMatrix = new Matrix();
    }

    public CnMap getCnMap() {
        return mCnMap;
    }

    public void setCnMap(CnMap cnMap) {
        mCnMap = cnMap;
    }

    public CnSvgBigRenderer getCnSvgBigRenderer() {
        return mCnSvgBigRenderer;
    }

    public void setCnSvgBigRenderer(CnSvgBigRenderer cnSvgBigRenderer) {
        mCnSvgBigRenderer = cnSvgBigRenderer;
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

        // 如果图片宽高任意大于控件宽高，则缩小
        if(dw > w || dh > h) {
            mBaseScale = Math.min(w * 1.0f / dw, h * 1.0f / dh);
        }
        mCurrentScale = mBaseScale;
        // 最多放大 10 倍
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
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(20f);
//        canvas.drawPoint(x, y, mPaint);
    }

    int touchFlag = -1;
    int currentFlag = -1;
    private Matrix mMapMatrix;
    //    Paint mPaint = new Paint();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float[] pts = new float[2];
        pts[0] = event.getX();
        pts[1] = event.getY();
        // 根据当前图片矩阵特性转换点击坐标
        mImageMatrix.invert(mMapMatrix);
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        mMapMatrix.postScale(values[Matrix.MSCALE_X], values[Matrix.MSCALE_Y]);
//        LogAndToastUtil.LogV(values[Matrix.MSCALE_X] + " : scaleX");
//        LogAndToastUtil.LogV(values[Matrix.MTRANS_X] + " : transX");
//        LogAndToastUtil.LogV(values[Matrix.MSCALE_Y] + " : transY");
        mMapMatrix.mapPoints(pts);
        int x = (int) pts[0];
        int y = (int) pts[1];
        // 测试
//        invalidate();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchFlag(x, y);
                currentFlag = touchFlag;
            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIdList.add(event.getPointerId(event.getActionIndex()));
                // 当只有一个手指时，另一个手指按下会触发 ACTION_POINTER_DOWN ，这个时候得到的手指数是 2
                // 当只有一个手指时才能进行拖动
                if (event.getPointerCount() == 1 && mRectF.contains((int) event.getX(), (int) event.getY())) {
                    canDrag = true;
                    dragPoint.set(event.getX(0), event.getY(0));
                } else {
                    canDrag = false;
                }
                // 当刚好有两个手指时才能进行缩放
                if (event.getPointerCount() == 2 && mRectF.contains((int) event.getX(), (int) event.getY())) {
                    canScale = true;
                    scaleFirstPoint.set(event.getX(0), event.getY(0));
                    scaleFirstPid = event.getPointerId(0);
                    scaleSecondPoint.set(event.getX(1), event.getY(1));
                    scaleSecondPid = event.getPointerId(1);
                } else {
                    canScale = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                currentFlag = getTouchFlag(x, y);
                // 如果手指按下区域和抬起区域相同且不为空，则判断点击事件
                if (currentFlag == touchFlag && currentFlag != -1) {
                    LogAndToastUtil.ToastOut(getContext(), mCnMap.PROVINCE[currentFlag] + " is clicked");
                    if(mChinaMapViewProvinceListener != null) {
                        mChinaMapViewProvinceListener.onProvinceClick(currentFlag);
                    }
                }
                touchFlag = currentFlag = -1;
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
                    dragPoint.set(event.getX(index), event.getY(index));
                }
                // 小于两个手指时不能缩放
                if (event.getPointerCount() <= 2) {
                    canScale = false;
                }
                // 检查是否超出可见区域，如果是那么进行动画让其可见
                if (event.getPointerCount() == 1) {
                    invisibleCheck();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (canScale) {
                    float afterFirstX = event.getX(event.findPointerIndex(scaleFirstPid));
                    float afterFirstY = event.getY(event.findPointerIndex(scaleFirstPid));
                    float afterSecondX = event.getX(event.findPointerIndex(scaleSecondPid));
                    float afterSecondY = event.getY(event.findPointerIndex(scaleSecondPid));
                    float pivotX = (scaleFirstPoint.x + scaleSecondPoint.x) / 2;
                    float pivotY = (scaleFirstPoint.y + scaleSecondPoint.y) / 2;
                    double beforeDistance =
                        Math.sqrt(
                            Math.pow((scaleFirstPoint.x - scaleSecondPoint.x), 2) +
                            Math.pow((scaleFirstPoint.y - scaleSecondPoint.y), 2)
                        );
                    double afterDistance =
                        Math.sqrt(
                            Math.pow((afterFirstX - afterSecondX), 2) +
                            Math.pow((afterFirstY - afterSecondY), 2)
                        );
                    float scaleFactor = (float) (afterDistance / beforeDistance);

//                    onScale(scaleFactor, pivotX, pivotY);

                    mCurrentScale = scaleFactor;
                    onScale(scaleFactor, pivotX, pivotY);
//                    mImageMatrix.postScale(scaleFactor, scaleFactor, pivotX, pivotY);
//                    setImageMatrix(mImageMatrix);

                    scaleFirstPoint.set(afterFirstX, afterFirstY);
                    scaleSecondPoint.set(afterSecondX, afterSecondY);
                }
                if (canDrag) {
                    float tX = event.getX(0) - dragPoint.x;
                    float tY = event.getY(0) - dragPoint.y;

//                    mCurrentTranslateX += tX;
//                    mCurrentTranslateY += tY;

                    mImageMatrix.postTranslate(tX, tY);
                    setImageMatrix(mImageMatrix);

                    dragPoint.set(event.getX(0), event.getY(0));
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFlag = -1;
                currentFlag = -1;
                break;
        }

        return true;
    }

    private int getTouchFlag(int x, int y) {
        LogAndToastUtil.LogV("in");
        LogAndToastUtil.LogV(mCnSvgBigRenderer.mRegionList + "");
        for(int i = 0; i < mCnSvgBigRenderer.mRegionList.size(); i++) {
            if(mCnSvgBigRenderer.mRegionList.get(i).contains(x, y)){
                LogAndToastUtil.LogV(i + " : i");
                return i;
            }
        }
        LogAndToastUtil.LogV("no");
        return -1;
    }

    private void onScale(float scaleFactor, float pivotX, float pivotY) {
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
                    pivotX, pivotY);
            invisibleCheck();
            setImageMatrix(mImageMatrix);
        }
    }

    private float getScale() {
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    // 1:1 情况下，图片实际边缘离开控件边缘的像素单位
    private final float RIGHT_OUT = 400;
    private final float LEFT_OUT = 300;
    private final float BOTTOM_OUT = 300;
    private final float TOP_OUT = 500;
    private final float VIEW_MEASURE_WIDTH = 672;
    private final float VIEW_MEASURE_HEIGHT = 750;
    private boolean ifLeftOut = false;
    private boolean ifRightOut = false;
    private boolean ifTopOut = false;
    private boolean ifBottomOut = false;

    private void invisibleCheck() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        if(rect.right / mCurrentScale < RIGHT_OUT) {
            ifRightOut = true;
            deltaX = RIGHT_OUT*mCurrentScale - rect.right;
        }
        if((VIEW_MEASURE_WIDTH - rect.left) / mCurrentScale < VIEW_MEASURE_WIDTH - LEFT_OUT) {
            ifLeftOut = true;
            deltaX = (VIEW_MEASURE_WIDTH - LEFT_OUT)*mCurrentScale-VIEW_MEASURE_WIDTH+rect.left;
            deltaX = -deltaX;
        }
        if((VIEW_MEASURE_HEIGHT - rect.top) / mCurrentScale < VIEW_MEASURE_HEIGHT - TOP_OUT) {
            ifTopOut = true;
            deltaY = (VIEW_MEASURE_HEIGHT - TOP_OUT)*mCurrentScale-VIEW_MEASURE_HEIGHT+rect.top;
            deltaY = -deltaY;
        }
        if(rect.bottom / mCurrentScale < BOTTOM_OUT) {
            ifBottomOut = true;
            deltaY = BOTTOM_OUT*mCurrentScale - rect.bottom;
        }
        mImageMatrix.postTranslate(deltaX, deltaY);
        setImageMatrix(mImageMatrix);
    }

    /**
     * 获得当前图片的宽和高
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

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);

    }
}
