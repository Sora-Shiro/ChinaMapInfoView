package com.sorashiro.chinamapinfomation;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.megatronking.svg.support.SVGDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.textTest)
    TextView textTest;
    @BindView(R2.id.imgCnMap)
    ImageView imgCnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initSVG();
    }

    private void initSVG() {
        Drawable drawable = new SVGDrawable(new CnSvg(this, new CnMap()));
        imgCnMap.setImageDrawable(drawable);
    }
}
