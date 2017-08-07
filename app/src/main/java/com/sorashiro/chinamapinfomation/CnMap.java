package com.sorashiro.chinamapinfomation;

import java.util.HashMap;

/**
 * Created by GameKing on 2017/8/7.
 */

public class CnMap {

    private int length = 34;

    public String[] PROVINCE = {"Anhui", "Beijing", "Chongqing", "Fujian", "Gansu", "Guangdong",
            "Guangxi", "Guizhou", "Hainan", "Hebei", "Heilongjiang", "Henan", "Hong Kong", "Hubei",
            "Hunan", "Jiangsu", "Jiangxi", "Jilin", "Liaoning", "Macau", "Nei Mongol", "Ningxia",
            "Qinghai", "Shaanxi", "Shanghai", "Shandong", "Shanxi", "Sichuan", "Taiwan", "Tianjin",
            "Xinjiang", "Xizang", "Yunnan", "Zhejiang"};

    public HashMap<String, CnMapConfig> config;

    public CnMap() {
        config = new HashMap<>();
        for(String s : PROVINCE) {
            config.put(s, new CnMapConfig());
        }
    }


}
