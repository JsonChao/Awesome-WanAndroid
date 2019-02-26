package json.chao.com.wanandroid.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import json.chao.com.wanandroid.di.component.BaseActivityComponent;
import json.chao.com.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import json.chao.com.wanandroid.ui.main.activity.AboutUsActivity;
import json.chao.com.wanandroid.ui.main.activity.ArticleDetailActivity;
import json.chao.com.wanandroid.ui.main.activity.LoginActivity;
import json.chao.com.wanandroid.ui.main.activity.MainActivity;
import json.chao.com.wanandroid.ui.main.activity.RegisterActivity;
import json.chao.com.wanandroid.ui.main.activity.SearchListActivity;
import json.chao.com.wanandroid.ui.main.activity.SplashActivity;

/**
 * @author quchao
 * @date 2018/5/3
 */

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AbstractAllActivityModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity contributesSplashActivityInjector();

    @ContributesAndroidInjector(modules = ArticleDetailActivityModule.class)
    abstract ArticleDetailActivity contributesArticleDetailActivityInjector();

    @ContributesAndroidInjector(modules = KnowledgeHierarchyDetailActivityModule.class)
    abstract KnowledgeHierarchyDetailActivity contributesKnowledgeHierarchyDetailActivityInjector();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributesLoginActivityInjector();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributesRegisterActivityInjector();

    @ContributesAndroidInjector(modules = AboutUsActivityModule.class)
    abstract AboutUsActivity contributesAboutUsActivityInjector();

    @ContributesAndroidInjector(modules = SearchListActivityModule.class)
    abstract SearchListActivity contributesSearchListActivityInjector();


}
