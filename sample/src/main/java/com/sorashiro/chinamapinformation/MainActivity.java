package com.sorashiro.chinamapinformation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sorashiro.chinamapinformation.tool.LogAndToastUtil;
import com.sorashiro.chinamapinfoview.ChinaMapInfoView;
import com.sorashiro.chinamapinfoview.CnMap;
import com.sorashiro.chinamapinfoview.CnMapConfig;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ChinaMapInfoView.ChinaMapViewProvinceListener {

    @BindView(R2.id.imgCnMap)
    ChinaMapInfoView imgCnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        imgCnMap.setChinaMapViewProvinceListener(this);
        // first get the config map
        // 首先获取总设置 map
        CnMap cnMap = imgCnMap.getCnMap();
        HashMap<String, CnMapConfig> cnConfigMap = cnMap.configMap;
        // or cnMap.configMap.get("Anhui"); but use cnMap.PROVINCE[0] better
        // 或者 cnMap.configMap.get("Anhui"); 但是用 cnMap.PROVINCE[0] 更好些
        CnMapConfig configAnhui = cnConfigMap.get(cnMap.PROVINCE[0]);
        // support method chaining
        // 支持链式调用（方法链）
//        configAnhui
//                .setFillColor(Color.parseColor("#ee0000"))
//                .setClickColor(Color.parseColor("#99ffff"));
    }

    @Override
    public void onProvinceClick(int i) {
        LogAndToastUtil.ToastOut(this, imgCnMap.getCnMap().PROVINCE[i] + " is clicked");
    }

    @Override
    public void onProvinceLongClick(int i) {
        LogAndToastUtil.ToastOut(this, imgCnMap.getCnMap().PROVINCE[i] + " is long clicked");
    }
}
