package json.chao.com.wanandroid.test;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;


/**
 * @author quchao
 * @date 2018/6/13
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class MyServiceTest {

    @Rule
    public final ServiceTestRule mServiceTestRule = new ServiceTestRule();
    
    @Test
    public void startService() throws TimeoutException {
        mServiceTestRule.startService(new Intent(InstrumentationRegistry.getContext(),
                MyService.class));
    }
    
    @Test
    public void bindService() throws TimeoutException {
        IBinder iBinder = mServiceTestRule.bindService(new Intent(InstrumentationRegistry.getContext(),
                MyService.class));
        MyService myService = ((MyService.LocalBinder) iBinder).getService();
    }

}