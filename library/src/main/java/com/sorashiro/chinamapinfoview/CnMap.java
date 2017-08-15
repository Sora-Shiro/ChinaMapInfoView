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

import java.util.HashMap;

/**
 * Created by SoraShiro on 2017/8/7.
 *
 * Use CnMap.configMap.get(CnMap.PROVINCE[index]) to config different province.
 *
 * PROVINCE[i] 省/市/自治区/特别行政区
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
 * @author Sora Shiro
 * @since 2017/8/7
 *
 */

public class CnMap {

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
