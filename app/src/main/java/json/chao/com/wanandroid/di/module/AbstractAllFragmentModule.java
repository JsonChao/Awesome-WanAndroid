package json.chao.com.wanandroid.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import json.chao.com.wanandroid.di.component.BaseFragmentComponent;
import json.chao.com.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyFragment;
import json.chao.com.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyListFragment;
import json.chao.com.wanandroid.ui.main.fragment.CollectFragment;
import json.chao.com.wanandroid.ui.main.fragment.SettingFragment;
import json.chao.com.wanandroid.ui.mainpager.fragment.MainPagerFragment;
import json.chao.com.wanandroid.ui.navigation.fragment.NavigationFragment;
import json.chao.com.wanandroid.ui.project.fragment.ProjectFragment;
import json.chao.com.wanandroid.ui.project.fragment.ProjectListFragment;
import json.chao.com.wanandroid.ui.wx.fragment.WxArticleFragment;
import json.chao.com.wanandroid.ui.wx.fragment.WxDetailArticleFragment;

/**
 * @author quchao
 * @date 2018/5/4
 */

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {

    @ContributesAndroidInjector(modules = CollectFragmentModule.class)
    abstract CollectFragment contributesCollectFragmentInject();

    @ContributesAndroidInjector(modules = KnowledgeFragmentModule.class)
    abstract KnowledgeHierarchyFragment contributesKnowledgeHierarchyFragmentInject();

    @ContributesAndroidInjector(modules = KnowledgeListFragmentModule.class)
    abstract KnowledgeHierarchyListFragment contributesKnowledgeHierarchyListFragmentInject();

    @ContributesAndroidInjector(modules = MainPagerFragmentModule.class)
    abstract MainPagerFragment contributesMainPagerFragmentInject();

    @ContributesAndroidInjector(modules = NavigationFragmentModule.class)
    abstract NavigationFragment contributesNavigationFragmentInject();

    @ContributesAndroidInjector(modules = WxArticleFragmentModule.class)
    abstract WxArticleFragment contributesWxArticleFragmentInject();

    @ContributesAndroidInjector(modules = WxDetailArticleFragmentModule.class)
    abstract WxDetailArticleFragment WxDetailArticleFragmentInject();

    @ContributesAndroidInjector(modules = ProjectFragmentModule.class)
    abstract ProjectFragment contributesProjectFragmentInject();

    @ContributesAndroidInjector(modules = ProjectListFragmentModule.class)
    abstract ProjectListFragment contributesProjectListFragmentInject();

    @ContributesAndroidInjector(modules = SettingFragmentModule.class)
    abstract SettingFragment contributesSettingFragmentInject();

}
