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
    // 点击文本颜色
    private int     mClickTextColor;
    // 长按文本颜色
    private int     mLongClickTextColor;
    // 文本大小
    private int     mTextSize;
    // 设定文本是否与图片一起缩放
    private boolean mIfTextScale;
    // 填充颜色
    private int     mFillColor;
    // 点击颜色
    private int     mClickColor;
    // 点击边缘颜色
    private int     mClickStrokeColor;
    // 长按颜色
    private int     mLongClickColor;
    // 长按边缘颜色
    private int     mLongClickStrokeColor;
    // 是否被点击
    private boolean mIfClick;
    // 是否被长按
    private boolean mIfLongClick;
    // 边缘颜色
    private int     mStrokeColor;
    // 边缘宽度
    private int     mStrokeWidth;

    public CnMapConfig() {
        this.mText = "";
        this.mTextColor = Color.parseColor("#ee82ee");
        this.mClickTextColor = Color.parseColor("#000000");
        this.mLongClickTextColor = Color.parseColor("#000000");
        this.mTextSize = 30;
        this.mIfTextScale = true;
        this.mFillColor = Color.parseColor("#66ccff");
        this.mClickColor = Color.parseColor("#8EDBFF");
        this.mClickStrokeColor = Color.parseColor("#ffffff");
        this.mLongClickColor = Color.parseColor("#FEE38A");
        this.mLongClickStrokeColor = Color.parseColor("#ffffff");
        this.mIfClick = false;
        this.mIfLongClick = false;
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

    public int getClickTextColor() {
        return mClickTextColor;
    }

    public CnMapConfig setClickTextColor(int clickTextColor) {
        mClickTextColor = clickTextColor;
        return this;
    }

    public int getLongClickTextColor() {
        return mLongClickTextColor;
    }

    public CnMapConfig setLongClickTextColor(int longClickTextColor) {
        mLongClickTextColor = longClickTextColor;
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

    public int getClickColor() {
        return mClickColor;
    }

    public CnMapConfig setClickColor(int clickColor) {
        mClickColor = clickColor;
        return this;
    }

    public int getClickStrokeColor() {
        return mClickStrokeColor;
    }

    public CnMapConfig setClickStrokeColor(int clickStrokeColor) {
        mClickStrokeColor = clickStrokeColor;
        return this;
    }

    public int getLongClickColor() {
        return mLongClickColor;
    }

    public CnMapConfig setLongClickColor(int longClickColor) {
        mLongClickColor = longClickColor;
        return this;
    }

    public int getLongClickStrokeColor() {
        return mLongClickStrokeColor;
    }

    public CnMapConfig setLongClickStrokeColor(int longClickStrokeColor) {
        mLongClickStrokeColor = longClickStrokeColor;
        return this;
    }

    protected boolean getIfClick() {
        return mIfClick;
    }

    protected CnMapConfig setIfClick(boolean ifClick) {
        mIfClick = ifClick;
        return this;
    }

    protected boolean getIfLongClick() {
        return mIfLongClick;
    }

    protected CnMapConfig setIfLongClick(boolean ifLongClick) {
        mIfLongClick = ifLongClick;
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
