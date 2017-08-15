# ChinaMapInfoView

Used to show China geographic information.

用于显示中国地理信息。

Use  [SVG-Android](https://github.com/MegatronKing/SVG-Android/blob/master/README.zh-cn.md) to generate Drawable, API 14 (Android 4.0)+ supported.

使用 [SVG-Android](https://github.com/MegatronKing/SVG-Android/blob/master/README.zh-cn.md) 生成 Drawable ， 支持 API 14（Android 4.0）+ 版本。

# Function

Supporting:
- Translate 
- Scale
- Highlight clicked province
- Rollback when out of view
- Implement ChinaMapViewProvinceListener callback to interact
- Custom fill / highlight (stroke) color 、stroke width

支持：
- 平移
- 缩放
- 高亮选中省份
- 当图片过度移动则回滚
- 实现 ChinaMapViewProvinceListener 接口来进行回调交互
- 自定义填充 / 高亮（边界）颜色、边界宽度

# Preview

![preview](https://github.com/Sora-Shiro/ChinaMapInfoView/blob/master/extra/preview.gif)

# Download

## Maven

```
<dependency>
  <groupId>com.sorashiro.ChinaMapInfoView</groupId>
  <artifactId>library</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

## Gradle

```
compile 'com.sorashiro.ChinaMapInfoView:library:1.0.1'
```

# How To Use

## In `activity_main.xml`

```xml
<com.sorashiro.chinamapinfoview.ChinaMapInfoView
    android:id="@+id/imgCnMap"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
/>
```

## In `MainActivity.java` (using [Butter Knife](https://github.com/JakeWharton/butterknife))

```java
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
    }

    @Override
    public void onProvinceClick(int i) {
        LogAndToastUtil.ToastOut(this, imgCnMap.getCnMap().PROVINCE[i] + " is clicked");
    }
}
```

## Other

If you want to change Anhui's `fill color` and `highlight color` together, just do like this: 

如果你想同时改变安徽省的 `填充颜色` 和 `高亮颜色` ，只要这么做：

```java
// first get the config map
// 首先获取总设置 map
CnMap cnMap = imgCnMap.getCnMap();
HashMap<String, CnMapConfig> cnConfigMap = cnMap.configMap;
// or configMap.get("Anhui"); but use cnMap.PROVINCE[0] better
// 或者 configMap.get("Anhui"); 但是用 cnMap.PROVINCE[0] 更好些
CnMapConfig configAnhui = cnConfigMap.get(cnMap.PROVINCE[0]);
// support method chaining
// 支持链式调用（方法链）
configAnhui
        .setFillColor(Color.parseColor("#ee0000"))
        .setHighlightColor(Color.parseColor("#99ffff"));
```

# Todo
- Text showed (but when province too small they should invisible)
- Long clicked event

# License

```
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
```