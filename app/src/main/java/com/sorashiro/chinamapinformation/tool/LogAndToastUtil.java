package com.sorashiro.chinamapinformation.tool;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Sora
 * @date 2016/11/5
 * <p>
 * A util class about debug and toast.
 * Too lazy to produce this class.
 * 跟调试和显示Toast相关的工具类，懒到一定境界的产物。
 */

public class LogAndToastUtil {

    private static final boolean IF_PRINT = true;

    public static void LogV(String s) {
        if (IF_PRINT) {
            Log.v("aaa", s);
        }
    }

    private static Toast toast;

    public static void ToastOut(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(
                    context,
                    s,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(s);
        }
        toast.show();
    }
}
