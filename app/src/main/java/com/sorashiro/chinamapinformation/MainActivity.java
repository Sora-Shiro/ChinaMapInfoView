package com.sorashiro.chinamapinformation;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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

//        initListener();
    }

    private void initSVG() {
        Drawable drawable = new SVGDrawable(new CnSvgBig(this, new CnMap()));
        imgCnMap.setImageDrawable(drawable);
    }

    private void initListener(){
        imgCnMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        textTest.setText("起始位置为："+"("+event.getX()+" , "+event.getY()+")");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        textTest.setText("移动中坐标为："+"("+event.getX()+" , "+event.getY()+")");
                        break;
                    case MotionEvent.ACTION_UP:
                        textTest.setText("最后位置为："+"("+event.getX()+" , "+event.getY()+")");
                }
                return true;
            }
        });
    }
}
