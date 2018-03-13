package json.chao.com.wanandroid.ui.navigation.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.ZoomOutTransformer;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.bean.navigation.NavigationListData;
import json.chao.com.wanandroid.core.bean.navigation.NavigationListResponse;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.contract.navigation.NavigationContract;
import json.chao.com.wanandroid.core.event.DismissErrorView;
import json.chao.com.wanandroid.core.event.ShowErrorView;
import json.chao.com.wanandroid.presenter.navigation.NavigationPresenter;
import json.chao.com.wanandroid.ui.navigation.adapter.NavigationAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import q.rorbin.verticaltablayout.VerticalTabLayout;


/**
 * @author quchao
 * @date 2018/2/11
 */

public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout mTabLayout;
    @BindView(R.id.navigation_viewpager)
    VerticalViewPager mViewPager;

    private ArrayList<BaseFragment> mFragments;
    private NavigationAdapter mAdapter;

    public static NavigationFragment getInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initEventAndData() {
        mFragments = new ArrayList<>();
        mPresenter.getNavigationListData();
    }

    @Override
    public void showNavigationListData(NavigationListResponse navigationListResponse) {
        if (navigationListResponse == null || navigationListResponse.getData() == null) {
            showNavigationListFail();
            return;
        }
        RxBus.getDefault().post(new DismissErrorView());
        mTabLayout.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.tab_bac));
        mViewPager.setVisibility(View.VISIBLE);
        List<NavigationListData> data1 = navigationListResponse.getData();
        for (NavigationListData data : data1) {
            mFragments.add(NavigationListFragment.getInstance(data, null));
        }
        if (mAdapter == null) {
            mAdapter = new NavigationAdapter(getChildFragmentManager(), data1, mFragments);
        }
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutTransformer());

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(Constants.TAB_ONE);
    }

    @Override
    public void showNavigationListFail() {
        CommonUtils.showMessage(_mActivity, getString(R.string.failed_to_obtain_navigation_list));
    }

    @Override
    public void showError() {
        mTabLayout.setBackgroundColor(ContextCompat.getColor(_mActivity, R.color.transparent));
        mViewPager.setVisibility(View.GONE);
        RxBus.getDefault().post(new ShowErrorView());
    }

    public void reLoad() {
        mPresenter.getNavigationListData();
    }

}
