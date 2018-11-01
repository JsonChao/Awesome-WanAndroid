package json.chao.com.wanandroid.ui.wx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.base.fragment.BaseRootFragment;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.wx.WxContract;
import json.chao.com.wanandroid.core.bean.wx.WxAuthor;
import json.chao.com.wanandroid.core.event.JumpToTheTopEvent;
import json.chao.com.wanandroid.presenter.wx.WxArticlePresenter;
import json.chao.com.wanandroid.utils.CommonUtils;


/**
 * @author quchao
 * @date 2018/10/31
 */
public class WxArticleFragment extends BaseRootFragment<WxArticlePresenter> implements WxContract.View {

    @BindView(R.id.wx_detail_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.wx_detail_viewpager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wx_article;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getWxAuthorListData();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void reload() {
        if (mPresenter != null && mTabLayout.getVisibility() == View.INVISIBLE) {
            mPresenter.getWxAuthorListData();
        }
    }

    public static WxArticleFragment getInstance(String param1, String param2) {
        WxArticleFragment fragment = new WxArticleFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void initViewPagerAndTabLayout(List<WxAuthor> wxAuthors) {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null? 0 : mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return wxAuthors.get(position).getName();
            }
        });
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void showWxAuthorListView(List<WxAuthor> wxAuthors) {
        if (mPresenter.getCurrentPage() == Constants.TYPE_WX_ARTICLE) {
            setChildViewVisibility(View.VISIBLE);
        } else {
            setChildViewVisibility(View.INVISIBLE);
        }
        mFragments.clear();
        for (WxAuthor wxAuthor : wxAuthors) {
            mFragments.add(WxDetailArticleFragment.getInstance(wxAuthor.getId(), wxAuthor.getName()));
        }
        initViewPagerAndTabLayout(wxAuthors);
        showNormal();
    }

    @Override
    public void showError() {
        setChildViewVisibility(View.INVISIBLE);
        super.showError();
    }

    private void setChildViewVisibility(int visible) {
        mTabLayout.setVisibility(visible);
        mViewPager.setVisibility(visible);
    }

    public void jumpToTheTop() {
        if (mFragments != null) {
            RxBus.getDefault().post(new JumpToTheTopEvent());
        }
    }

}
