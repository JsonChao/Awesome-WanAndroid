package json.chao.com.wanandroid.performance.ui;

import android.os.Looper;
import android.os.MessageQueue;

import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.ReflectUtils;

/**
 * Copyright (C), 2020
 * FileName: UiUtils
 * Date: 2020-01-07 17:53
 * Description: 用于Ui优化的工具类
 *
 * @author quchao
 */
public class UiUtils {

    /**
     * 实现将子线程Looper中的MessageQueue替换为主线程中Looper的
     * MessageQueue，这样就能够在子线程中异步创建UI。
     *
     * 注意：需要在子线程中调用。
     *
     * @param reset 是否将子线程中的MessageQueue重置为原来的，false则表示需要进行替换
     * @return 替换是否成功
     */
    public static boolean replaceLooperWithMainThreadQueue(boolean reset) {
        if (CommonUtils.isMainThread()) {
            return true;
        } else {
            // 1、获取子线程的ThreadLocal实例
            ThreadLocal<Looper> threadLocal = ReflectUtils.reflect(Looper.class).field("sThreadLocal").get();
            if (threadLocal == null) {
                return false;
            } else {
                Looper looper = null;
                if (!reset) {
                    Looper.prepare();
                    looper = Looper.myLooper();
                    // 2、通过调用MainLooper的getQueue方法区获取主线程Looper中的MessageQueue实例
                    Object queue = ReflectUtils.reflect(Looper.getMainLooper()).method("getQueue").get();
                    if (!(queue instanceof MessageQueue)) {
                        return false;
                    }
                    // 3、将子线程中的MessageQueue字段的值设置为主线的MessageQueue实例
                    ReflectUtils.reflect(looper).field("mQueue", queue);
                }

                // 4、reset为false，表示需要将子线程Looper中的MessageQueue重置为原来的。
                ReflectUtils.reflect(threadLocal).method("set", looper);
                return true;
            }
        }
    }

}
