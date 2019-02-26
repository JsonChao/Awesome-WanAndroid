package json.chao.com.wanandroid.testStudyExample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import json.chao.com.wanandroid.BasePresenterTest;
import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.test.GithubService;
import json.chao.com.wanandroid.test.User;
import json.chao.com.wanandroid.utils.RxUtils;

/**
 * @author quchao
 * @date 2018/6/5
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MockResponseTest extends BasePresenterTest {

    @Test
    public void mockResponse() {
        GithubService.createGithubService()
                .getUser("JsonChao")
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(User::toString,
                        throwable -> ShadowLog.d("error", throwable.getMessage()));
    }
}