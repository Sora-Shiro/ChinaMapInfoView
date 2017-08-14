package com.sorashiro.chinamapinformation;

import android.graphics.Color;

/**
 * Created by SoraShiro on 2017/8/7.
 *
 */

public class CnMapConfig {

    // 文本
    private String mText;
    // 文本颜色
    private int   mTextColor;
    // 文本大小
    private int mTextSize;
    // 设定文本是否与图片一起缩放
    private boolean mIfTextScale;
    // 填充颜色
    private int   mFillColor;
    // 触摸颜色
    private int mTouchColor;
    // 边缘颜色
    private int   mStrokeColor;
    // 边缘宽度
    private int   mStrokeWidth;

    public CnMapConfig() {
        this.mText = "Test";
        this.mTextColor = Color.parseColor("#ee82ee");
        this.mTextSize = 30;
        this.mIfTextScale = true;
        this.mFillColor = Color.parseColor("#66ccff");
        this.mTouchColor = Color.parseColor("#00ffff");
        this.mStrokeColor = Color.parseColor("#000000");
        this.mStrokeWidth = 3;
    }

    public String getText() {
        return mText;
    }

    public CnMapConfig setText(String text) {
        mText = text;
        return this;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public CnMapConfig setTextColor(int color) {
        mTextColor = color;
        return this;
    }

    public boolean getIfTextScale() {
        return mIfTextScale;
    }

    public CnMapConfig setIfTextScale(boolean ifTextScale) {
        mIfTextScale = ifTextScale;
        return this;
    }

    public int getFillColor() {
        return mFillColor;
    }

    public CnMapConfig setFillColor(int fillColor) {
        mFillColor = fillColor;
        return this;
    }

    public int getTouchColor() {
        return mTouchColor;
    }

    public CnMapConfig setTouchColor(int touchColor) {
        mTouchColor = touchColor;
        return this;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public CnMapConfig setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        return this;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public CnMapConfig setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public CnMapConfig setTextSize(int textSize) {
        mTextSize = textSize;
        return this;
    }
}
