package json.chao.com.wanandroid.di.component;


import javax.inject.Singleton;

import dagger.Component;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.db.GreenDaoHelper;
import json.chao.com.wanandroid.core.http.RetrofitHelper;
import json.chao.com.wanandroid.core.prefs.PreferenceHelperImpl;
import json.chao.com.wanandroid.app.GeeksApp;
import json.chao.com.wanandroid.di.module.AppModule;
import json.chao.com.wanandroid.di.module.HttpModule;

/**
 * @author quchao
 * @date 2017/11/27
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    /**
     * 提供App的Context
     *
     * @return GeeksApp context
     */
    GeeksApp getContext();

    /**
     * 数据中心
     *
     * @return DataManager
     */
    DataManager getDataManager();

    /**
     * 提供http的帮助类
     *
     * @return RetrofitHelper
     */
    RetrofitHelper retrofitHelper();

    /**
     * 提供数据库帮助类
     *
     * @return GreenDaoHelper
     */
    GreenDaoHelper realmHelper();

    /**
     * 提供sp帮助类
     *
     * @return PreferenceHelperImpl
     */
    PreferenceHelperImpl preferencesHelper();

}
