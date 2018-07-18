package json.chao.com.wanandroid.test;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author quchao
 * @date 2018/6/14
 */
public final class SimpleCountingIdlingResource implements IdlingResource {

    private final String mResourceName;

    //这个counter值就像一个标记，默认为0
    private final AtomicInteger counter = new AtomicInteger(0);

    private volatile ResourceCallback resourceCallback;

    public SimpleCountingIdlingResource(String resourceName) {
        mResourceName = resourceName;
    }

    @Override
    public String getName() {
        return mResourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    //每当我们开始异步请求，把counter值+1
    public void increment() {
        counter.getAndIncrement();
    }

    //当我们获取到网络数据后，counter值-1；
    public void decrement() {
        int counterVal = counter.decrementAndGet();
        //如果这时counter == 0，说明异步结束，执行回调。
        if (counterVal == 0) {
            //
            if (null != resourceCallback) {
                resourceCallback.onTransitionToIdle();
            }
        }

        if (counterVal < 0) {
            //如果小于0，抛出异常
            throw new IllegalArgumentException("Counter has been corrupted!");
        }
    }
}
