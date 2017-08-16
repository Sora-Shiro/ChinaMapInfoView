/*
 * Copyright (C) 2017, Sora Shiro (https://github.com/Sora-Shiro)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.sorashiro.chinamapinfoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.github.megatronking.svg.support.SVGDrawable;

import java.util.LinkedList;

/**
 * Created by SoraShiro on 2017/8/7.
 * <p>
 * Implements ChinaMapViewProvinceListener to interact province click event :)
 *
 * @author Sora Shiro
 * @since 2017/8/7
 */

public class ChinaMapInfoView extends ImageView {

    // Context 只要实现该接口就可以处理区域点击事件了
    public interface ChinaMapViewProvinceListener {
        void onProvinceClick(int i);
        void onProvinceLongClick(int i);
    }

    ChinaMapViewProvinceListener mChinaMapViewProvinceListener;

    // 一个存储所有触摸点 ID 的 List
    LinkedList<Integer> pointerIdList = new LinkedList<>();
    boolean             canDrag       = false;
    boolean             canScale      = false;
    // 图片所在区域
    RectF mRectF;
    private Matrix mImageMatrix = new Matrix();
    // 拖动点
    PointF dragPoint        = new PointF(0, 0);
    // 缩放点
    PointF scaleFirstPoint  = new PointF(0, 0);
    PointF scaleSecondPoint = new PointF(0, 0);
    // 缩放点的 pointId
    int scaleFirstPid;
    int scaleSecondPid;
    // 最小缩放比率，一般与该 view 的大小适配
    private float mBaseScale;
    // 当前缩放比率
    private float mCurrentScale;
    // 最大缩放比率，设定是最小的 10 倍
    private float mMaxScale;

    // 用于设定地图信息
    private CnMap            mCnMap;
    // 用于获取地图各部分 Region
    private CnSvgBigRenderer mCnSvgBigRenderer;

    public ChinaMapInfoView(Context context) {
        super(context, null);
        init();
    }

    public ChinaMapInfoView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public ChinaMapInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        // 禁止硬件加速，非常重要！
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mImageMatrix = new Matrix();
        mCnMap = new CnMap();

        resetDrawable();

        mMapMatrix = new Matrix();
    }

    // 每次地图设置有所更改都要调用该函数重新生成 drawable
    private void resetDrawable() {
        mCnSvgBigRenderer = new CnSvgBigRenderer(getContext(), mCnMap);
        Drawable drawable = new SVGDrawable(mCnSvgBigRenderer);
        setImageDrawable(drawable);
    }

    public CnMap getCnMap() {
        return mCnMap;
    }

    public void setCnMap(CnMap cnMap) {
        mCnMap = cnMap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取到对应图片的宽度和高度
        Drawable d = getDrawable();
        if (null == d) {
            return;
        }
        int dw = d.getIntrinsicWidth();
        int dh = d.getIntrinsicHeight();

        mRectF = new RectF(0, 0, w, h);

        // 如果图片宽高任意大于控件宽高，则缩小
        if (dw > w || dh > h) {
            mBaseScale = Math.min(w * 1.0f / dw, h * 1.0f / dh);
        }
        mCurrentScale = mBaseScale;
        // 最多放大 10 倍
        mMaxScale = mBaseScale * 10;

        // 将图片移动到 view 的中间位置
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

    // 记录触碰到的省份区域
    int saveTouchFlag    = -1;
    // 记录按下时的省份区域
    int currentTouchFlag = -1;
    private Matrix mMapMatrix;
    // 记录拖动点，拖动超过一定距离则取消点击事件
    float   firstDownX = -1;
    float   firstDownY = -1;
    // 长按功能实现
    LongClickRunnable mLongClickRunnable;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float[] original = new float[2];
        float[] pts = new float[2];
        pts[0] = original[0] = event.getX();
        pts[1] = original[1] = event.getY();
        // 根据当前图片矩阵特性转换点击坐标
        mImageMatrix.invert(mMapMatrix);
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        mMapMatrix.postScale(values[Matrix.MSCALE_X], values[Matrix.MSCALE_Y]);
        mMapMatrix.mapPoints(pts);
        int x = (int) pts[0];
        int y = (int) pts[1];
        switch (event.getActionMasked()) {
            // 任何新的手指加入、移动、离开都会取消旧长按事件
            case MotionEvent.ACTION_DOWN:
                if(mLongClickRunnable != null) {
                    mLongClickRunnable.cancelLongClick();
                }
                firstDownX = original[0];
                firstDownY = original[1];
                currentTouchFlag = getTouchFlag(x, y);
                // 第一个手指按下，如果手指不在高亮区域而且已经有高亮区域，取消该高亮区域
                // 但这个功能在缩放的时候体验不好，故取消该功能
//                if(currentTouchFlag != saveHighlightIndex && saveHighlightIndex != -1) {
//                    disHighlightRegion(saveHighlightIndex, saveHighlightMode);
//                }
                saveTouchFlag = currentTouchFlag;
                // 点到了省份才开始长按判断线程
                if(currentTouchFlag != -1) {
                    mLongClickRunnable = new LongClickRunnable(currentTouchFlag);
                    Thread longClickThread = new Thread(mLongClickRunnable);
                    longClickThread.start();
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIdList.add(event.getPointerId(event.getActionIndex()));
                // 当只有一个手指时，另一个手指按下会触发 ACTION_POINTER_DOWN ，这个时候得到的手指数是 2
                // 当只有一个手指时才能进行拖动
                if (event.getPointerCount() == 1 && mRectF.contains((int) event.getX(), (int) event.getY())) {
                    canDrag = true;
                    dragPoint.set(event.getX(0), event.getY(0));
                } else {
                    canDrag = false;
                    if(mLongClickRunnable != null) {
                        mLongClickRunnable.cancelLongClick();
                    }
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
                if(mLongClickRunnable != null) {
                    mLongClickRunnable.cancelLongClick();
                }
                currentTouchFlag = getTouchFlag(x, y);
                // 如果手指按下区域和抬起区域相同且不为空，则判断为点击事件
                if (currentTouchFlag == saveTouchFlag && currentTouchFlag != -1) {
                    if (mChinaMapViewProvinceListener != null) {
                        mChinaMapViewProvinceListener.onProvinceClick(currentTouchFlag);

                        highlightRegion(currentTouchFlag, HIGHLIGHT_CLICK);
                    }
                } else if(currentTouchFlag == -1 && saveHighlightIndex != -1){
                    disHighlightRegion(saveHighlightIndex, saveHighlightMode);
                }
                saveTouchFlag = currentTouchFlag = -1;
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
                    saveTouchFlag = -1;
                    // 根据两指的始末距离不断计算缩放比例
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

                    mCurrentScale = scaleFactor;
                    onScale(scaleFactor, pivotX, pivotY);

                    scaleFirstPoint.set(afterFirstX, afterFirstY);
                    scaleSecondPoint.set(afterSecondX, afterSecondY);
                }
                if (canDrag) {
                    // x 或 y 方向移动大于 20 像素时取消点击 / 长按效果
                    if (Math.abs(firstDownX - original[0]) > 20 ||
                            Math.abs(firstDownY - original[1]) > 20) {
                        saveTouchFlag = -1;
                        if(mLongClickRunnable != null) {
                            mLongClickRunnable.cancelLongClick();
                        }
                    }
                    float tX = event.getX(0) - dragPoint.x;
                    float tY = event.getY(0) - dragPoint.y;

                    // 根据拖动距离平移
                    mImageMatrix.postTranslate(tX, tY);
                    setImageMatrix(mImageMatrix);

                    dragPoint.set(event.getX(0), event.getY(0));
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                saveTouchFlag = -1;
                currentTouchFlag = -1;
                if(mLongClickRunnable != null) {
                    mLongClickRunnable.cancelLongClick();
                }
                break;
        }

        return true;
    }

    // 获取触摸区域
    private int getTouchFlag(int x, int y) {
        for (int i = 0; i < mCnSvgBigRenderer.mRegionList.size(); i++) {
            if (mCnSvgBigRenderer.mRegionList.get(i).contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    // 存储已被高亮的区域信息，同一时刻只能有一个区域被高亮
    private int              saveHighlightIndex   = -1;
    private int              saveHighlightMode    = -1;
    // 高亮模式：点击 / 长按
    private static final int HIGHLIGHT_CLICK      = 0;
    private static final int HIGHLIGHT_LONG_CLICK = 1;

    // 高亮某一区域
    private void highlightRegion(int index, int mode) {
        if (index == saveHighlightIndex && mode == saveHighlightMode) {
            return;
        }
        if (saveHighlightIndex != -1) {
            disHighlightRegion(saveHighlightIndex, saveHighlightMode);
        }
        CnMapConfig config = mCnMap.configMap.get(mCnMap.PROVINCE[index]);
        switch (mode) {
            case HIGHLIGHT_CLICK:
                config.setIfClick(true);
                break;
            case HIGHLIGHT_LONG_CLICK:
                config.setIfLongClick(true);
                break;
        }
        saveHighlightMode = mode;
        saveHighlightIndex = index;

        resetDrawable();
    }

    // 取消高亮某一区域
    private void disHighlightRegion(int index, int mode) {
        CnMapConfig config = mCnMap.configMap.get(mCnMap.PROVINCE[index]);
        switch (mode) {
            case HIGHLIGHT_CLICK:
                config.setIfClick(false);
                break;
            case HIGHLIGHT_LONG_CLICK:
                config.setIfLongClick(false);
                break;
        }
        saveHighlightMode = -1;
        saveHighlightIndex = -1;

        resetDrawable();
    }

    private class LongClickRunnable implements Runnable {

        // 应该被长按高亮的位置
        private int index;
        // 延时一定时间后是否继续
        private boolean ifContinue;

        LongClickRunnable(int i) {
            index = i;
            ifContinue = true;
        }

        @Override
        public void run() {
            Looper.prepare();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return ;
            }
            if(ifContinue) {
                // 既然触发了长按，自然不能触发点击事件
                saveTouchFlag = -1;

                Message disColorMsg = new Message();
                disColorMsg.what = 0;
                mLongClickHandler.sendMessage(disColorMsg);

                Message onLongClickMsg = new Message();
                onLongClickMsg.what = 1;
                onLongClickMsg.arg1 = index;
                mLongClickHandler.sendMessage(onLongClickMsg);
            }
        }

        // 取消该长按事件
        void cancelLongClick() {
            ifContinue = false;
        }

    }

    private Handler mLongClickHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if(saveHighlightIndex != -1) {
                        disHighlightRegion(saveHighlightIndex, saveHighlightMode);
                    }
                    break;
                case 1:
                    mChinaMapViewProvinceListener.onProvinceLongClick(msg.arg1);
                    highlightRegion(msg.arg1, HIGHLIGHT_LONG_CLICK);
                    saveHighlightIndex = msg.arg1;
                    break;
            }
        }

    };

    // 缩放处理函数
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
            setImageMatrix(mImageMatrix);
        }
    }

    // 获取当前缩放值
    private float getScale() {
        float[] values = new float[9];
        mImageMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    // 1:1 情况下，图片实际边缘离开控件边缘的像素单位，用于针对过度平移的回移处理
    private final float   RIGHT_OUT           = 600;
    private final float   LEFT_OUT            = 300;
    private final float   BOTTOM_OUT          = 500;
    private final float   TOP_OUT             = 450;
    private final float   VIEW_MEASURE_WIDTH  = 672;
    private final float   VIEW_MEASURE_HEIGHT = 750;
    private       boolean ifLeftOut           = false;
    private       boolean ifRightOut          = false;
    private       boolean ifTopOut            = false;
    private       boolean ifBottomOut         = false;

    // 回移处理函数
    private void invisibleCheck() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        if (rect.right / mCurrentScale < RIGHT_OUT) {
            ifRightOut = true;
            deltaX = RIGHT_OUT * mCurrentScale - rect.right;
        }
        if ((VIEW_MEASURE_WIDTH - rect.left) / mCurrentScale < VIEW_MEASURE_WIDTH - LEFT_OUT) {
            ifLeftOut = true;
            deltaX = (VIEW_MEASURE_WIDTH - LEFT_OUT) * mCurrentScale - VIEW_MEASURE_WIDTH + rect.left;
            deltaX = -deltaX;
        }
        if ((VIEW_MEASURE_HEIGHT - rect.top) / mCurrentScale < VIEW_MEASURE_HEIGHT - TOP_OUT) {
            ifTopOut = true;
            deltaY = (VIEW_MEASURE_HEIGHT - TOP_OUT) * mCurrentScale - VIEW_MEASURE_HEIGHT + rect.top;
            deltaY = -deltaY;
        }
        if (rect.bottom / mCurrentScale < BOTTOM_OUT) {
            ifBottomOut = true;
            deltaY = BOTTOM_OUT * mCurrentScale - rect.bottom;
        }
        mImageMatrix.postTranslate(deltaX, deltaY);
        setImageMatrix(mImageMatrix);
    }

    // 获得当前图片的宽和高
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
}
