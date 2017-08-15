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

import android.graphics.Color;

/**
 * Created by SoraShiro on 2017/8/7.
 * <p>
 * Use CnMap.configMap.get(CnMap.PROVINCE[i]) to config different province.
 *
 * @author Sora Shiro
 * @since 2017/8/7
 */

public class CnMapConfig {

    // 文本
    private String  mText;
    // 文本颜色
    private int     mTextColor;
    // 高亮文本颜色
    private int     mHighlightTextColor;
    // 文本大小
    private int     mTextSize;
    // 设定文本是否与图片一起缩放
    private boolean mIfTextScale;
    // 填充颜色
    private int     mFillColor;
    // 高亮颜色
    private int     mHighlightColor;
    // 高亮边缘颜色
    private int     mHighlightStrokeColor;
    // 是否被触摸
    private boolean mIfTouch;
    // 边缘颜色
    private int     mStrokeColor;
    // 边缘宽度
    private int     mStrokeWidth;

    public CnMapConfig() {
        this.mText = "";
        this.mTextColor = Color.parseColor("#ee82ee");
        this.mHighlightTextColor = Color.parseColor("#000000");
        this.mTextSize = 30;
        this.mIfTextScale = true;
        this.mFillColor = Color.parseColor("#66ccff");
        this.mHighlightColor = Color.parseColor("#8EDBFF");
        this.mHighlightStrokeColor = Color.parseColor("#ffffff");
        this.mIfTouch = false;
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

    public int getHighlightTextColor() {
        return mHighlightTextColor;
    }

    public CnMapConfig setHighlightTextColor(int highlightTextColor) {
        mHighlightTextColor = highlightTextColor;
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

    public int getHighlightColor() {
        return mHighlightColor;
    }

    public CnMapConfig setHighlightColor(int highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public int getHighlightStrokeColor() {
        return mHighlightStrokeColor;
    }

    public CnMapConfig setHighlightStrokeColor(int highlightStrokeColor) {
        mHighlightStrokeColor = highlightStrokeColor;
        return this;
    }

    protected boolean getIfTouch() {
        return mIfTouch;
    }

    protected CnMapConfig setIfTouch(boolean ifTouch) {
        mIfTouch = ifTouch;
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
