package json.chao.com.wanandroid.testStudyExample.Robolectric;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.test.MyService;

/**
 * @author quchao
 * @date 2018/6/6
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MyServiceTest {

    private ServiceController<MyService> mServiceController;
    private MyService mMyService;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        mServiceController = Robolectric.buildService(MyService.class);
        mMyService = mServiceController.get();
    }

    @Test
    public void serviceLifecycle() {
        mServiceController.create();
        mServiceController.startCommand(0, 0);
        mServiceController.bind();
        mServiceController.unbind();
        mServiceController.destroy();
    }

}