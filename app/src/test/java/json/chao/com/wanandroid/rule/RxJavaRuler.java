package json.chao.com.wanandroid.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * @author quchao
 * @date 2018/6/5
 */
public class RxJavaRuler implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
                base.evaluate();
            }
        };
    }
}
