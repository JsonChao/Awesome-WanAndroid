package json.chao.com.wanandroid.performance.wakelock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;

public class WakeLockUtils {

    private static PowerManager.WakeLock sWakeLock;

    public static void acquire(Context context){
        if(sWakeLock == null){
            sWakeLock = createWakeLock(context);
        }
        if(sWakeLock != null && !sWakeLock.isHeld()){
            sWakeLock.acquire();
            sWakeLock.acquire(1000);
        }
    }

    public static void release(){
        // 一些逻辑
        try{

        }catch (Exception e){

        }finally {
            // 为了演示正确的使用方式
            if(sWakeLock != null && sWakeLock.isHeld()){
                sWakeLock.release();
                sWakeLock = null;
            }
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    private static PowerManager.WakeLock createWakeLock(Context context){
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if(pm != null){
            return pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"");
        }
        return null;
    }

}
