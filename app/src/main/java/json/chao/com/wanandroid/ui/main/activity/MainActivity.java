package json.chao.com.wanandroid.ui.main.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.device.yearclass.YearClass;

import org.jay.launchstarter.TaskDispatcher;
import org.jay.launchstarter.utils.DispatcherExecutor;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.contract.main.MainContract;
import json.chao.com.wanandroid.performance.net.NetUtils;
import json.chao.com.wanandroid.presenter.main.MainPresenter;
import json.chao.com.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyFragment;
import json.chao.com.wanandroid.ui.main.fragment.CollectFragment;
import json.chao.com.wanandroid.ui.main.fragment.SearchDialogFragment;
import json.chao.com.wanandroid.ui.main.fragment.SettingFragment;
import json.chao.com.wanandroid.ui.main.fragment.UsageDialogFragment;
import json.chao.com.wanandroid.ui.mainpager.fragment.MainPagerFragment;
import json.chao.com.wanandroid.ui.navigation.fragment.NavigationFragment;
import json.chao.com.wanandroid.ui.project.fragment.ProjectFragment;
import json.chao.com.wanandroid.ui.wx.fragment.WxArticleFragment;
import json.chao.com.wanandroid.utils.BottomNavigationViewHelper;
import json.chao.com.wanandroid.utils.CommonAlertDialog;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.LogHelper;
import json.chao.com.wanandroid.utils.StatusBarUtil;


/**
 * @author quchao
 * @date 2017/11/28
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;

    private ArrayList<BaseFragment> mFragments;
    private TextView mUsTv;
    private MainPagerFragment mMainPagerFragment;
    private KnowledgeHierarchyFragment mKnowledgeHierarchyFragment;
    private WxArticleFragment mWxArticleFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private int mLastFgIndex;
    private UsageDialogFragment usageDialogFragment;
    private SearchDialogFragment searchDialogFragment;
    private long backNetUseData;
    private long foreNetUseData;
    private long totalNetUseData;
    private boolean isBackground;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.d("start");
        CommonAlertDialog.newInstance().cancelDialog(true);
        LogHelper.d("end");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(getString(R.string.home_pager));
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_blue), 1f);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initEventAndData() {
        // 以下代码是为了演示Msg导致的主线程卡顿
//        new Handler().post(() -> {
//            LogHelper.i("Msg 执行");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        int year = YearClass.get(WanAndroidApp.getInstance());
        if (year >= YearClass.CLASS_2016) {
            // 配置较高的手机可以 开启复杂的动画 或 "重功能"。
            // 通常来说，从 2016年开始 的手机配置就比较好了，
            // 我们统一按照这个模板使用即可。

        } else {
            // 低端机用户可以 关闭复杂的动画 或 "重功能"、在系统资源不够时我们应该主动去做降级处理。

        }

        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                isBackground = false;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                isBackground = true;
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
         // 前后台流浪监控（每隔30秒上报一次）
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay((Runnable) () -> {

            long currentTime = System.currentTimeMillis();
            long netUseData = NetUtils.getNetStats(this, currentTime - 30 * 1000, currentTime);

            // 判断是前台还是后台
            if (isBackground) {
                backNetUseData += netUseData;
            } else {
                foreNetUseData += netUseData;
            }
            LogHelper.i("backNetUseData: " + backNetUseData / 1024 / 1024 + " MB");
            LogHelper.i("foreNetUseData: " + foreNetUseData / 1024 / 1024 + " MB");

            totalNetUseData = backNetUseData + foreNetUseData;
            LogHelper.i("totalNetUseData: " + totalNetUseData / 1024 / 1024 + " MB");
        }, 30, 30, TimeUnit.SECONDS);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        if (savedInstanceState == null) {
            mPresenter.setNightModeState(false);
            initPager(false, Constants.TYPE_MAIN_PAGER);
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.tab_main_pager);
            initPager(true, Constants.TYPE_SETTING);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_usage:
                if (usageDialogFragment == null) {
                    usageDialogFragment = new UsageDialogFragment();
                }
                if (!isDestroyed() && usageDialogFragment.isAdded()) {
                    usageDialogFragment.dismiss();
                }
                usageDialogFragment.show(getSupportFragmentManager(), "UsageDialogFragment");
                break;
            case R.id.action_search:
                if (searchDialogFragment == null) {
                    searchDialogFragment = new SearchDialogFragment();
                }
                if (!isDestroyed() && searchDialogFragment.isAdded()) {
                    searchDialogFragment.dismiss();
                }
                searchDialogFragment.show(getSupportFragmentManager(), "SearchDialogFragment");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @OnClick({R.id.main_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_floating_action_btn:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }

    @Override
    public void showSwitchProject() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView.setSelectedItemId(R.id.tab_project);
        }
    }

    @Override
    public void showSwitchNavigation() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView.setSelectedItemId(R.id.tab_navigation);
        }
    }

    @Override
    public void showAutoLoginView() {
        showLoginView();
    }

    @Override
    public void showLogoutSuccess() {
        CommonAlertDialog.newInstance().cancelDialog(true);
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showLoginView() {
        if (mNavigationView == null) {
            return;
        }
        mUsTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        mUsTv.setText(mPresenter.getLoginAccount());
        mUsTv.setOnClickListener(null);
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
    }

    @Override
    public void showLogoutView() {
        mUsTv.setText(R.string.login_in);
        mUsTv.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        if (mNavigationView == null) {
            return;
        }
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }

    private void initPager(boolean isRecreate, int position) {
        mMainPagerFragment = MainPagerFragment.getInstance(isRecreate, null);
        mFragments.add(mMainPagerFragment);
        initFragments();
        init();
        switchFragment(position);
    }

    private void init() {
        mPresenter.setCurrentPage(Constants.TYPE_MAIN_PAGER);
        initNavigationView();
        initBottomNavigationView();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mDrawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
    }

    private void initBottomNavigationView() {
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_pager:
                    loadPager(getString(R.string.home_pager), 0,
                            mMainPagerFragment, Constants.TYPE_MAIN_PAGER);
                    break;
                case R.id.tab_knowledge_hierarchy:
                    loadPager(getString(R.string.knowledge_hierarchy), 1,
                            mKnowledgeHierarchyFragment, Constants.TYPE_KNOWLEDGE);
                    break;
                case R.id.tab_wx_article:
                    loadPager(getString(R.string.wx_article), 2,
                            mWxArticleFragment, Constants.TYPE_WX_ARTICLE);
                    break;
                case R.id.tab_navigation:
                    loadPager(getString(R.string.navigation), 3,
                            mNavigationFragment, Constants.TYPE_NAVIGATION);
                    break;
                case R.id.tab_project:
                    loadPager(getString(R.string.project), 4,
                            mProjectFragment, Constants.TYPE_PROJECT);
//                    startActivity(new Intent(this, VueActivity.class));
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void loadPager(String title, int position, BaseFragment mFragment, int pagerType) {
        mTitleTv.setText(title);
        switchFragment(position);
        mFragment.reload();
        mPresenter.setCurrentPage(pagerType);
    }

    private void jumpToTheTop() {
        switch (mPresenter.getCurrentPage()) {
            case Constants.TYPE_MAIN_PAGER:
                if (mMainPagerFragment != null) {
                    mMainPagerFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_KNOWLEDGE:
                if (mKnowledgeHierarchyFragment != null) {
                    mKnowledgeHierarchyFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_WX_ARTICLE:
                if (mWxArticleFragment != null) {
                    mWxArticleFragment.jumpToTheTop();
                }
            case Constants.TYPE_NAVIGATION:
                if (mNavigationFragment != null) {
                    mNavigationFragment.jumpToTheTop();
                }
                break;
            case Constants.TYPE_PROJECT:
                if (mProjectFragment != null) {
                    mProjectFragment.jumpToTheTop();
                }
                break;
            default:
                break;
        }
    }

    private void initFragments() {
        mKnowledgeHierarchyFragment = KnowledgeHierarchyFragment.getInstance(null, null);
        mWxArticleFragment = WxArticleFragment.getInstance(null, null);
        mNavigationFragment = NavigationFragment.getInstance(null, null);
        mProjectFragment = ProjectFragment.getInstance(null, null);
        CollectFragment collectFragment = CollectFragment.getInstance(null, null);
        SettingFragment settingFragment = SettingFragment.getInstance(null, null);

        mFragments.add(mKnowledgeHierarchyFragment);
        mFragments.add(mWxArticleFragment);
        mFragments.add(mNavigationFragment);
        mFragments.add(mProjectFragment);
        mFragments.add(collectFragment);
        mFragments.add(settingFragment);
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        if (position >= Constants.TYPE_COLLECT) {
            mFloatingActionButton.setVisibility(View.INVISIBLE);
            mBottomNavigationView.setVisibility(View.INVISIBLE);
        } else {
            mFloatingActionButton.setVisibility(View.VISIBLE);
            mBottomNavigationView.setVisibility(View.VISIBLE);
        }
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commitAllowingStateLoss();
            ft.add(R.id.fragment_group, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    private void initNavigationView() {
        mUsTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        if (mPresenter.getLoginStatus()) {
            showLoginView();
        } else {
            showLogoutView();
        }

        mNavigationView.getMenu().findItem(R.id.nav_item_wan_android)
                .setOnMenuItemClickListener(item -> {
                    startMainPager();
                    return true;
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_my_collect)
                .setOnMenuItemClickListener(item -> {
                    if (mPresenter.getLoginStatus()) {
                        startCollectFragment();
                        return true;
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                        CommonUtils.showMessage(this, getString(R.string.login_tint));
                        return true;
                    }
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_about_us)
                .setOnMenuItemClickListener(item -> {
                    startActivity(new Intent(this, AboutUsActivity.class));
                    return true;
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_logout)
                .setOnMenuItemClickListener(item -> {
                    logout();
                    return true;
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_setting)
                .setOnMenuItemClickListener(item -> {
                    startSettingFragment();
                    return true;
                });
    }

    private void startSettingFragment() {
        mTitleTv.setText(getString(R.string.setting));
        switchFragment(Constants.TYPE_SETTING);
        mDrawerLayout.closeDrawers();
    }

    private void startMainPager() {
        mTitleTv.setText(getString(R.string.home_pager));
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mBottomNavigationView.setSelectedItemId(R.id.tab_main_pager);
        mDrawerLayout.closeDrawers();
    }

    private void startCollectFragment() {
        mTitleTv.setText(getString(R.string.my_collect));
        switchFragment(Constants.TYPE_COLLECT);
        mDrawerLayout.closeDrawers();
    }

    private void logout() {
        CommonAlertDialog.newInstance().showDialog(
                this, getString(R.string.logout_tint),
                getString(R.string.ok),
                getString(R.string.no),
                v -> mPresenter.logout(),
                v -> CommonAlertDialog.newInstance().cancelDialog(true));
    }





}
