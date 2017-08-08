package com.sorashiro.chinamapinformation;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.megatronking.svg.support.SVGDrawable;
import com.sorashiro.chinamapinformation.tool.LogAndToastUtil;
import com.sorashiro.chinamapinformation.view.ChinaMapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.textTest)
    TextView     textTest;
    @BindView(R2.id.imgCnMap)
    ChinaMapView imgCnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initSVG();
    }

    private void initSVG() {
        Drawable drawable = new SVGDrawable(new CnSvgBig(this, new CnMap()));
//        Rect bounds = drawable.getBounds();
//        LogAndToastUtil.LogV(bounds.left + " : " + bounds.right);
//        drawable.setBounds(100, 100, 100, 100);
        imgCnMap.setImageDrawable(drawable);
//        imgCnMap.doScale();
    }
}
