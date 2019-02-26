package json.chao.com.wanandroid.testStudyExample.Robolectric;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.test.MyReceiver;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.app.WanAndroidApp;

/**
 * @author quchao
 * @date 2018/6/6
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MyReceiverTest {

    private static final String action = "test_broadcast_receiver";
    private Intent intent;

    @Before
    public void setUp() {
        intent = new Intent(action);

    }

    @Test
    public void registerReceiver() {
        ShadowApplication application = ShadowApplication.getInstance();
        Assert.assertNotNull(application.hasReceiverForIntent(intent));
    }

    @Test
    public void onReceive() {
        intent.putExtra(MyReceiver.APP_NAME, "WanAndroid");
        MyReceiver myReceiver = new MyReceiver();
        myReceiver.onReceive(WanAndroidApp.getInstance(), intent);

        SharedPreferences mPreferences = WanAndroidApp.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String appName = mPreferences.getString(MyReceiver.APP_NAME, null);
        Assert.assertEquals("WanAndroid", appName);
    }


}