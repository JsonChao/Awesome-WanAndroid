package json.chao.com.wanandroid.ui.project.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import json.chao.com.wanandroid.base.fragment.BaseRootFragment;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.bean.project.ProjectClassifyData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.contract.project.ProjectContract;
import json.chao.com.wanandroid.core.event.JumpToTheTopEvent;
import json.chao.com.wanandroid.presenter.project.ProjectPresenter;
import json.chao.com.wanandroid.utils.CommonUtils;

/**
 * @author quchao
 * @date 2018/2/11
 */

public class ProjectFragment extends BaseRootFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.project_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.project_divider)
    View mDivider;
    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    private List<ProjectClassifyData> mData;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private int currentPage;

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.setProjectCurrentPage(currentPage);
        }
        super.onDestroyView();
    }

    public static ProjectFragment getInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getProjectClassifyData();
        currentPage = mPresenter.getProjectCurrentPage();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showProjectClassifyData(List<ProjectClassifyData> projectClassifyDataList) {
        if (mPresenter.getCurrentPage() == Constants.TYPE_PROJECT) {
            setChildViewVisibility(View.VISIBLE);
        } else {
            setChildViewVisibility(View.INVISIBLE);
        }
        mData = projectClassifyDataList;
        initViewPagerAndTabLayout();
        showNormal();
    }

    @Override
    public void showError() {
        setChildViewVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void reload() {
        if (mPresenter != null && mTabLayout.getVisibility() == View.INVISIBLE) {
            mPresenter.getProjectClassifyData();
        }
    }

    private void initViewPagerAndTabLayout() {
        for (ProjectClassifyData data : mData) {
            ProjectListFragment projectListFragment = ProjectListFragment.getInstance(data.getId(), null);
            mFragments.add(projectListFragment);
        }
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mData == null? 0 : mData.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mData.get(position).getName();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void setChildViewVisibility(int visibility) {
        mTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
        mViewPager.setVisibility(visibility);
    }

    public void jumpToTheTop() {
        if (mFragments != null) {
            RxBus.getDefault().post(new JumpToTheTopEvent());
        }
    }

}

