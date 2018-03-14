package json.chao.com.wanandroid.ui.main.activity;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.main.MainContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.event.DismissErrorView;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.core.event.LogoutEvent;
import json.chao.com.wanandroid.core.event.ShowErrorView;
import json.chao.com.wanandroid.core.http.cookies.CookiesManager;
import json.chao.com.wanandroid.presenter.main.MainPresenter;
import json.chao.com.wanandroid.ui.hierarchy.fragment.KnowledgeHierarchyFragment;
import json.chao.com.wanandroid.ui.mainpager.fragment.MainPagerFragment;
import json.chao.com.wanandroid.ui.navigation.fragment.NavigationFragment;
import json.chao.com.wanandroid.ui.project.fragment.ProjectFragment;
import json.chao.com.wanandroid.ui.main.fragment.SearchDialogFragment;
import json.chao.com.wanandroid.utils.BottomNavigationViewHelper;
import json.chao.com.wanandroid.utils.CommonAlertDialog;
import json.chao.com.wanandroid.utils.CommonUtils;


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
    BottomNavigationView bottomNavigationBar;
    @BindView(R.id.main_view_stub)
    ViewStub mErrorView;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Inject
    DataManager mDataManager;

    private ArrayList<BaseFragment> mFragments;
    private TextView mUsTv;
    private int currentPage;
    private MainPagerFragment mMainPagerFragment;
    private KnowledgeHierarchyFragment mKnowledgeHierarchyFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private int mLastFgIndex;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonAlertDialog.newInstance().cancelDialog();
        mDataManager.setCurrentPage(currentPage);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        initData();
        initNavigationView();
        currentPage = mDataManager.getCurrentPage();

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationBar);
        bottomNavigationBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_pager:
                    mTitleTv.setText(getString(R.string.home_pager));
                    switchFragment(0);
                    break;
                case R.id.tab_knowledge_hierarchy:
                    mTitleTv.setText(getString(R.string.knowledge_hierarchy));
                    switchFragment(1);
                    break;
                case R.id.tab_navigation:
                    mTitleTv.setText(getString(R.string.navigation));
                    switchFragment(2);
                    break;
                case R.id.tab_project:
                    mTitleTv.setText(getString(R.string.project));
                    switchFragment(3);
                    break;
                default:
                    break;
            }
            return true;
        });

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

        RxBus.getDefault().toFlowable(LoginEvent.class)
                .subscribe(loginEvent -> setLoginView());

        RxBus.getDefault().toFlowable(LogoutEvent.class)
                .subscribe(logoutEvent -> setLogoutView());

        RxBus.getDefault().toFlowable(ShowErrorView.class)
                .filter(showErrorView -> mErrorView != null)
                .subscribe(showErrorView -> showError());

        RxBus.getDefault().toFlowable(DismissErrorView.class)
                .filter(dismissErrorView -> mErrorView != null)
                .subscribe(dismissErrorView -> mErrorView.setVisibility(View.GONE));
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        TextView reloadTv = (TextView) findViewById(R.id.error_reload_tv);
        reloadTv.setOnClickListener(v -> {
            switch (currentPage) {
                case Constants.FIRST:
                    mMainPagerFragment.reLoad();
                    break;
                case Constants.SECOND:
                    mKnowledgeHierarchyFragment.reLoad();
                    break;
                case Constants.THIRD:
                    mNavigationFragment.reLoad();
                    break;
                case Constants.FOURTH:
                    mProjectFragment.reLoad();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            SearchDialogFragment searchDialogFragment = new SearchDialogFragment();
            searchDialogFragment.show(getFragmentManager(), "SearchDialogFragment");
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

    private void jumpToTheTop() {
        switch (currentPage) {
            case Constants.FIRST:
                if (mMainPagerFragment != null) {
                    mMainPagerFragment.jumpToTheTop();
                }
                break;
            case Constants.SECOND:
                if (mKnowledgeHierarchyFragment != null) {
                    mKnowledgeHierarchyFragment.jumpToTheTop();
                }
                break;
            case Constants.FOURTH:
                if (mProjectFragment != null) {
                    mProjectFragment.jumpToTheTop();
                }
                break;
            default:
                break;
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(getString(R.string.home_pager));
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mMainPagerFragment = MainPagerFragment.getInstance(null, null);
        mKnowledgeHierarchyFragment = KnowledgeHierarchyFragment.getInstance(null, null);
        mNavigationFragment = NavigationFragment.getInstance(null, null);
        mProjectFragment = ProjectFragment.getInstance(null, null);
        mFragments.add(mMainPagerFragment);
        mFragments.add(mKnowledgeHierarchyFragment);
        mFragments.add(mNavigationFragment);
        mFragments.add(mProjectFragment);
        switchFragment(0);
    }

    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            ft.add(R.id.fragment_group, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    private void initNavigationView() {
        mUsTv = ((TextView) mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv));
        if (mDataManager.getLoginStatus()) {
            setLoginView();
        } else {
            setLogoutView();
        }

        mNavigationView.getMenu().findItem(R.id.nav_item_my_collect)
                .setOnMenuItemClickListener(item -> {
                    if (mDataManager.getLoginStatus()) {
                        startActivity(new Intent(this, CollectActivity.class));
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
    }


    private void setLoginView() {
        if (mNavigationView == null) {
            return;
        }
        mUsTv = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        mUsTv.setText(mDataManager.getLoginAccount());
        mUsTv.setOnClickListener(null);
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
    }

    private void setLogoutView() {
        mUsTv.setText(R.string.login_in);
        mUsTv.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        if (mNavigationView == null) {
            return;
        }
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
    }

    private void logout() {
        CommonAlertDialog.newInstance().showDialog(
                this, getString(R.string.logout_tint),
                getString(R.string.ok),
                getString(R.string.no),
                v -> {
                    CommonAlertDialog.newInstance().cancelDialog();
                    mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
                    mDataManager.setLoginStatus(false);
                    CookiesManager.clearAllCookies();
                    RxBus.getDefault().post(new LogoutEvent());
                    startActivity(new Intent(this, LoginActivity.class));
                },
                v -> CommonAlertDialog.newInstance().cancelDialog());
    }

}
