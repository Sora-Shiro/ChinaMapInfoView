package com.sorashiro.chinamapinformation;

import java.util.HashMap;

/**
 * Created by SoraShiro on 2017/8/7.
 *
 * PROVINCE[index] 省/市/自治区/特别行政区
 * 0 安徽
 * 1 北京
 * 2 重庆
 * 3 福建
 * 4 甘肃
 * 5 广东
 * 6 广西
 * 7 贵州
 * 8 海南
 * 9 河北
 * 10 黑龙江
 * 11 河南
 * 12 香港
 * 13 湖北
 * 14 湖南
 * 15 江苏
 * 16 江西
 * 17 吉林
 * 18 辽宁
 * 19 澳门
 * 20 内蒙古
 * 21 宁夏
 * 22 青海
 * 23 陕西
 * 24 上海
 * 25 山东
 * 26 山西
 * 27 四川
 * 28 台湾
 * 29 天津
 * 30 新疆
 * 31 西藏
 * 32 云南
 * 33 浙江
 *
 */

public class CnMap {

    public static final int LENGTH = 34;

    public String[] PROVINCE = {"Anhui", "Beijing", "Chongqing", "Fujian", "Gansu", "Guangdong",
            "Guangxi", "Guizhou", "Hainan", "Hebei", "Heilongjiang", "Henan", "Hong Kong", "Hubei",
            "Hunan", "Jiangsu", "Jiangxi", "Jilin", "Liaoning", "Macau", "Nei Mongol", "Ningxia",
            "Qinghai", "Shaanxi", "Shanghai", "Shandong", "Shanxi", "Sichuan", "Taiwan", "Tianjin",
            "Xinjiang", "Xizang", "Yunnan", "Zhejiang"};

    public HashMap<String, CnMapConfig> configMap;

    public CnMap() {
        configMap = new HashMap<>();
        for(String s : PROVINCE) {
            configMap.put(s, new CnMapConfig());
        }
    }


}
