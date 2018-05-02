package json.chao.com.wanandroid.di.component;


import javax.inject.Singleton;

import dagger.Component;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.db.GreenDaoHelper;
import json.chao.com.wanandroid.core.http.RetrofitHelper;
import json.chao.com.wanandroid.core.prefs.PreferenceHelperImpl;
import json.chao.com.wanandroid.app.WanAndroidApp;
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
    WanAndroidApp getContext();

    /**
     * 数据中心
     *
     * @return DataManager
     */
    DataManager getDataManager();

}
