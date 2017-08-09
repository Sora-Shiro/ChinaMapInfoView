package com.sorashiro.chinamapinformation;

import android.graphics.Color;

/**
 * Created by SoraShiro on 2017/8/7.
 *
 */

public class CnMapConfig {

    //文本（未实现）
    private String mText;
    //文本颜色（未实现）
    private int   mTextColor;
    //文本大小
    private int mTextSize;
    //填充颜色
    private int   mFillColor;
    //边缘颜色
    private int   mStrokeColor;
    //边缘宽度
    private int   mStrokeWidth;

    public CnMapConfig() {
        this.mText = "Test";
        this.mTextColor = Color.parseColor("#ee82ee");
        this.mTextSize = 15;
        this.mFillColor = Color.parseColor("#66ccff");
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

    public CnMapConfig setText(int color) {
        mTextColor = color;
        return this;
    }

    public int getFillColor() {
        return mFillColor;
    }

    public CnMapConfig setFillColor(int fillColor) {
        mFillColor = fillColor;
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
