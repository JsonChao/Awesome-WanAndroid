package json.chao.com.wanandroid.di.component;

import android.app.Activity;


import dagger.Component;
import json.chao.com.wanandroid.di.module.FragmentModule;
import json.chao.com.wanandroid.di.scope.FragmentScope;
import json.chao.com.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyFragment;
import json.chao.com.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyListFragment;
import json.chao.com.wanandroid.ui.main.activity.CollectFragment;
import json.chao.com.wanandroid.ui.main.activity.SettingFragment;
import json.chao.com.wanandroid.ui.main.fragment.UsageDialogFragment;
import json.chao.com.wanandroid.ui.mainpager.fragment.MainPagerFragment;
import json.chao.com.wanandroid.ui.navigation.fragment.NavigationFragment;
import json.chao.com.wanandroid.ui.project.fragment.ProjectFragment;
import json.chao.com.wanandroid.ui.project.fragment.ProjectListFragment;
import json.chao.com.wanandroid.ui.main.fragment.SearchDialogFragment;

/**
 * @author quchao
 * @date 2017/11/27
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    /**
     * 获取Activity实例
     *
     * @return Activity
     */
    Activity getActivity();

    /**
     * 注入MainPagerFragment所需的依赖
     *
     * @param mainPagerFragment MainPagerFragment
     */
    void inject(MainPagerFragment mainPagerFragment);

    /**
     * 注入KnowledgeHierarchyFragment所需的依赖
     *
     * @param knowledgeHierarchyFragment KnowledgeHierarchyFragment
     */
    void inject(KnowledgeHierarchyFragment knowledgeHierarchyFragment);

    /**
     * 注入KnowledgeHierarchyListFragment所需的依赖
     *
     * @param knowledgeHierarchyListFragment KnowledgeHierarchyListFragment
     */
    void inject(KnowledgeHierarchyListFragment knowledgeHierarchyListFragment);

    /**
     * 注入VideoFrequencyFragment所需的依赖
     *
     * @param projectFragment ProjectFragment
     */
    void inject(ProjectFragment projectFragment);

    /**
     * 注入NewsFragment所需的依赖
     *
     * @param navigationFragment NavigationFragment
     */
    void inject(NavigationFragment navigationFragment);

    /**
     * 注入ProjectListFragment所需的依赖
     *
     * @param projectListFragment ProjectListFragment
     */
    void inject(ProjectListFragment projectListFragment);

    /**
     * 注入ProjectListFragment所需的依赖
     *
     * @param searchDialogFragment SearchDialogFragment
     */
    void inject(SearchDialogFragment searchDialogFragment);

    /**
     * 注入UsageDialogFragment所需的依赖
     *
     * @param usageDialogFragment UsageDialogFragment
     */
    void inject(UsageDialogFragment usageDialogFragment);

    /**
     * 注入CollectFragment所需的依赖
     *
     * @param collectFragment CollectFragment
     */
    void inject(CollectFragment collectFragment);

    /**
     * 注入SettingFragment所需的依赖
     *
     * @param settingFragment SettingFragment
     */
    void inject(SettingFragment settingFragment);

}
