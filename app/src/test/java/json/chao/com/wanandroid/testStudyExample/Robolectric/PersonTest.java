package json.chao.com.wanandroid.testStudyExample.Robolectric;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowLog;

import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.test.Person;

/**
 * @author quchao
 * @date 2018/6/6
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23, shadows = {ShadowPerson.class})
public class PersonTest {

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
    }

    @Test
    public void ShadowPerson() {
        Person person = new Person();
        Log.d("My Name", person.getName());
        Log.d("Age", String.valueOf(person.getAge()));

        ShadowPerson shadowPerson = Shadow.extract(person);
        Assert.assertEquals("JsonChao", shadowPerson.getName());
        Assert.assertEquals(25, shadowPerson.getAge());
    }

}