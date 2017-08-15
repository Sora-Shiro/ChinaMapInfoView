package com.sorashiro.chinamapinformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sorashiro.chinamapinformation.tool.LogAndToastUtil;
import com.sorashiro.chinamapinfoview.ChinaMapInfoView;
import com.sorashiro.chinamapinfoview.CnMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ChinaMapInfoView.ChinaMapViewProvinceListener{

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
        CnMap cnMap = imgCnMap.getCnMap();
    }

    @Override
    public void onProvinceClick(int i) {
        LogAndToastUtil.ToastOut(this, imgCnMap.getCnMap().PROVINCE[i] + " is clicked");
    }
}
