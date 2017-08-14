package com.sorashiro.chinamapinformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sorashiro.chinamapinformation.tool.LogAndToastUtil;
import com.sorashiro.chinamapinformation.view.ChinaMapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ChinaMapView.ChinaMapViewProvinceListener{

    @BindView(R2.id.textTest)
    TextView     textTest;
    @BindView(R2.id.imgCnMap)
    ChinaMapView imgCnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

//        initListener();
    }

    private void initView() {
        imgCnMap.setChinaMapViewProvinceListener(this);
        CnMap cnMap = imgCnMap.getCnMap();
        CnSvgBigRenderer cnSvgBigRenderer = imgCnMap.getCnSvgBigRenderer();
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

    @Override
    public void onProvinceClick(int i) {
        LogAndToastUtil.ToastOut(this, imgCnMap.getCnMap().PROVINCE[i] + " is clicked");
    }
}
