package json.chao.com.wanandroid;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import json.chao.com.wanandroid.BasePresenterTest;
import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.test.GithubService;
import json.chao.com.wanandroid.rule.RxJavaRuler;
import json.chao.com.wanandroid.contract.project.ProjectContract;
import json.chao.com.wanandroid.core.DataManager;
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