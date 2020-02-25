package json.chao.com.wanandroid.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import json.chao.com.wanandroid.utils.LogHelper;

@Aspect
public class PerformanceAop {

//    @Around("call(* com.optimize.performance.PerformanceApp.**(..))")
//    public void getTime(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        long time = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
////        LogUtils.i(name + " cost " + (System.currentTimeMillis() - time));
//    }

    @Around("execution(* android.app.Activity.setContentView(..))")
    public void getSetContentViewTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.toShortString();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LogHelper.i(name + " cost " + (System.currentTimeMillis() - time));
    }

    // 注意：加入aspectj这个切面可能造成dex缺少
//    @After("execution(org.jay.launchstarter.Task.new(..)")
//    public void newObject(JoinPoint point) {
//        LogHelper.i(" new " + point.getTarget().getClass().getSimpleName());
//    }




}
