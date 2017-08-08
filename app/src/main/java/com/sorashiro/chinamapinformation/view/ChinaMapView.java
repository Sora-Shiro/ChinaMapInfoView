package com.sorashiro.chinamapinformation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by GameKing on 2017/8/7.
 */

public class ChinaMapView extends ImageView {

    Matrix matrix = new Matrix();

    public ChinaMapView(Context context) {
        super(context);
    }

    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChinaMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.setScaleType(ScaleType.MATRIX);
        matrix = new Matrix();
    }

    public void doScale() {
    }

    float width;
    float height;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setTranslationX(100f);
        setScaleX(1f);
        setScaleY(1f);

    }
}
