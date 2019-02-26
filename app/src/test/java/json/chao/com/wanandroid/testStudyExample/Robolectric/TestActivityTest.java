package json.chao.com.wanandroid.testStudyExample.Robolectric;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;

import java.util.regex.Pattern;

import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.test.TestActivity;
import json.chao.com.wanandroid.test.TestFragment;
import json.chao.com.wanandroid.ui.main.activity.AboutUsActivity;

/**
 * @author quchao
 * @date 2018/6/6
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class TestActivityTest {

    private TestActivity mTestActivity;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        mTestActivity = Robolectric.setupActivity(TestActivity.class);
    }

    @Test
    public void testActivity() {
        Assert.assertNotNull(mTestActivity);
    }

    @Test
    public void testRegex() {
        String regex = "{\"duration\":5,\"type\":2},{\"duration\":17,\"type\":1},{\"duration\":43,\"type\":2},{\"duration\":16,\"type\":1},{\"duration\":66,\"type\":2},{\"duration\":18,\"type\":1},{\"duration\":13,\"type\":2},{\"duration\":21,\"type\":1},{\"duration\":107,\"type\":2},{\"duration\":2,\"type\":3},{\"duration\":3,\"type\":2},";
        Pattern compile = Pattern.compile("},");
        String[] strings = compile.split(regex);
    }

    @Test
    public void jumpAboutActivity() {
        Button button = (Button) mTestActivity.findViewById(R.id.button);
        button.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(mTestActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        Assert.assertNotNull(intent.getComponent().getClassName(), AboutUsActivity.class.getName());
    }

    @Test
    public void showToast() {
        Toast latestToast = ShadowToast.getLatestToast();
        Assert.assertNull(latestToast);

        Button button = (Button) mTestActivity.findViewById(R.id.button2);
        button.performClick();

        latestToast = ShadowToast.getLatestToast();
        Assert.assertNotNull(latestToast);

        ShadowToast shadowToast = Shadows.shadowOf(latestToast);
        ShadowLog.d("toast_time", shadowToast.getDuration() + "");
        Assert.assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        Assert.assertEquals("hahaha", ShadowToast.getTextOfLatestToast());
    }

    //返回的Dialog都为app包下的，暂时不支持v7。。。
    @Test
    public void showDialog() {
        AlertDialog latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog();
        Assert.assertNull(latestAlertDialog);

        Button button = (Button) mTestActivity.findViewById(R.id.button3);
        button.performClick();

        latestAlertDialog = ShadowAlertDialog.getLatestAlertDialog();
        Assert.assertNotNull(latestAlertDialog);

        ShadowAlertDialog shadowAlertDialog = Shadows.shadowOf(latestAlertDialog);
        Assert.assertEquals("Dialog", shadowAlertDialog.getTitle());
        ShadowLog.d("dialog_tag", (String) shadowAlertDialog.getMessage());
        Assert.assertEquals("showDialog", shadowAlertDialog.getMessage());
    }

    @Test
    public void inverse() {
        CheckBox checkBox = (CheckBox) mTestActivity.findViewById(R.id.checkBox);
        Button button = (Button) mTestActivity.findViewById(R.id.button4);

        Assert.assertFalse(checkBox.isChecked());

        button.performClick();

        Assert.assertTrue(checkBox.isChecked());

        button.performClick();

        Assert.assertFalse(checkBox.isChecked());
    }


    @Test
    public void startTestFragment() {
        TestFragment testFragment = new TestFragment();
        mTestActivity.startTestFragment(testFragment);

        Assert.assertNotNull(testFragment.getView());
    }

    @Test
    public void testApplication() {
        Application application = RuntimeEnvironment.application;
        String appName = application.getString(R.string.app_name);
        Assert.assertEquals("WanAndroid", appName);
    }

    @Test
    public void testActivityLifecycle() {
        ActivityController<TestActivity> activityController =
                Robolectric.buildActivity(TestActivity.class);
        TestActivity testActivity = activityController.get();
        Assert.assertNull(testActivity.getLifeCycleStatus());

        activityController.create();
        Assert.assertEquals("onCreate", testActivity.getLifeCycleStatus());

        activityController.start();
        Assert.assertEquals("onStart", testActivity.getLifeCycleStatus());

        activityController.resume();
        Assert.assertEquals("onResume", testActivity.getLifeCycleStatus());

        activityController.pause();
        Assert.assertEquals("onPause", testActivity.getLifeCycleStatus());

        activityController.stop();
        Assert.assertEquals("onStop", testActivity.getLifeCycleStatus());

        activityController.restart();
        Assert.assertEquals("onStart", testActivity.getLifeCycleStatus());

        activityController.destroy();
        Assert.assertEquals("onDestroy", testActivity.getLifeCycleStatus());
    }




}