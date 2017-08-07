package com.sorashiro.chinamapinfomation;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by GameKing on 2017/8/7.
 */

public class CnMapConfig {

    //文本（未实现）
    private String mText;
    //填充颜色
    private int   mFillColor;
    //边缘颜色
    private int   mStrokeColor;
    //边缘宽度
    private int   mStrokeWidth;

    public CnMapConfig() {
        this.mText = "";
        this.mFillColor = Color.parseColor("#8866ccff");
        this.mStrokeColor = Color.parseColor("#44000000");
        this.mStrokeWidth = 2;
    }

    public String getText() {
        return mText;
    }

    public CnMapConfig setText(String text) {
        mText = text;
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

}
