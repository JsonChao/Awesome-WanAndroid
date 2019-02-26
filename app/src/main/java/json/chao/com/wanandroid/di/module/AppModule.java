package json.chao.com.wanandroid.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.db.DbHelper;
import json.chao.com.wanandroid.core.db.DbHelperImpl;
import json.chao.com.wanandroid.core.http.HttpHelper;
import json.chao.com.wanandroid.core.http.HttpHelperImpl;
import json.chao.com.wanandroid.core.prefs.PreferenceHelper;
import json.chao.com.wanandroid.core.prefs.PreferenceHelperImpl;

/**
 * @author quchao
 * @date 2017/11/27
 */

@Module
public class AppModule {

    private final WanAndroidApp application;

    public AppModule(WanAndroidApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    WanAndroidApp provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(HttpHelperImpl httpHelperImpl) {
        return httpHelperImpl;
    }

    @Provides
    @Singleton
    DbHelper provideDBHelper(DbHelperImpl realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferenceHelperImpl implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DbHelper dbhelper, PreferenceHelper preferencesHelper) {
        return new DataManager(httpHelper, dbhelper, preferencesHelper);
    }

}
