package com.mfzj.parttimer.utils;

/**
 * Created by SuHongJin on 2018/11/6.
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mfzj.parttimer.R;

/**
 * 封装Toast类
 */
public class ToastUtils {
    private static Toast toast;
    private static TextView tv_toast;

    /**
     * @param context 上下文对象
     * @param message 提示的消息
     */
    public static void setOkToast(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_ok_layout, null);
        tv_toast = (TextView) view.findViewById(R.id.tv_toast);
        if (toast == null) {    //避免连续点击Toast时，产生显示不停的不友好效果
            tv_toast.setText(message);
            toast = new Toast(context);
        } else {
            tv_toast.setText(message);
            toast = new Toast(context);
        }
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
