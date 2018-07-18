package json.chao.com.wanandroid.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.app.WanAndroidApp;


/**
 * @author quchao
 * @date 2018/6/6
 */
public class MyReceiver extends BroadcastReceiver {

    public static final String APP_NAME = "app_name";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences mPreferences = WanAndroidApp.getInstance().getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        mPreferences.edit().putString(APP_NAME, intent.getStringExtra(APP_NAME)).apply();
    }
}
