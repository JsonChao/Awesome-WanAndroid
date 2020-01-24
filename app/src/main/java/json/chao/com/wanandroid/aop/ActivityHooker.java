package json.chao.com.wanandroid.aop;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import json.chao.com.wanandroid.performance.wakelock.WakeLockUtils;
import json.chao.com.wanandroid.utils.LogHelper;
import me.ele.lancet.base.Origin;
import me.ele.lancet.base.Scope;
import me.ele.lancet.base.annotations.Insert;
import me.ele.lancet.base.annotations.Proxy;
import me.ele.lancet.base.annotations.TargetClass;

public class ActivityHooker {

    public static ActivityRecord sActivityRecord;

    static {
        sActivityRecord = new ActivityRecord();
    }

    public static String trace;

    @Insert(value = "onCreate",mayCreateSuper = true)
    @TargetClass(value = "android.support.v7.app.AppCompatActivity",scope = Scope.ALL)
    protected void onCreate(Bundle savedInstanceState) {
        sActivityRecord.isNewCreate = true;
        sActivityRecord.mOnCreateTime = System.currentTimeMillis();
        Origin.callVoid();
    }

    @Insert(value = "onWindowFocusChanged",mayCreateSuper = true)
    @TargetClass(value = "android.support.v7.app.AppCompatActivity",scope = Scope.ALL)
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!sActivityRecord.isNewCreate) {
            return;
        }
        sActivityRecord.isNewCreate = false;
        sActivityRecord.mOnWindowsFocusChangedTime = System.currentTimeMillis();
        LogHelper.i(getClass().getCanonicalName() + " onWindowFocusChanged cost "+(sActivityRecord.mOnWindowsFocusChangedTime - sActivityRecord.mOnCreateTime));
        Origin.callVoid();
    }


//    public static long sStartTime = 0;
//
//    @Insert(value = "acquire")
//    @TargetClass(value = "com.optimize.performance.wakelock.WakeLockUtils",scope = Scope.SELF)
//    public static void acquire(Context context){
//        trace = Log.getStackTraceString(new Throwable());
//        sStartTime = System.currentTimeMillis();
//        Origin.callVoid();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                WakeLockUtils.release();
//            }
//        },1000);
//    }
//
//    @Insert(value = "release")
//    @TargetClass(value = "com.optimize.performance.wakelock.WakeLockUtils",scope = Scope.SELF)
//    public static void release(){
//        LogHelper.i("PowerManager "+(System.currentTimeMillis() - sStartTime)+"/n"+trace);
//        Origin.callVoid();
//    }
//
//
//    public static long runTime = 0;
//
//    @Insert(value = "run")
//    @TargetClass(value = "java.lang.Runnable",scope = Scope.ALL)
//    public void run(){
//        runTime = System.currentTimeMillis();
//        Origin.callVoid();
//        LogHelper.i("runTime "+(System.currentTimeMillis() - runTime));
//    }

//    @Proxy("i")
//    @TargetClass("android.util.Log")
//    public static int i(String tag, String msg) {
//        msg = msg + "JsonChao";
//        return (int) Origin.call();
//    }

}
