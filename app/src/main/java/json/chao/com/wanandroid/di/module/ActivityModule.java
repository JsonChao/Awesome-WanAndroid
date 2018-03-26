package json.chao.com.wanandroid.di.module;

import android.app.Activity;


import dagger.Module;
import dagger.Provides;
import json.chao.com.wanandroid.di.scope.ActivityScope;

/**
 * @author quchao
 * @date 2017/11/27
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return mActivity;
    }

}
