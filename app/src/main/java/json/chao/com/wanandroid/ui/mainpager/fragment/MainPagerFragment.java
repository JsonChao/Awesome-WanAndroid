package json.chao.com.wanandroid.ui.mainpager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BannerData;
import json.chao.com.wanandroid.core.bean.BannerResponse;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.BaseFragment;
import json.chao.com.wanandroid.contract.MainPagerContract;
import json.chao.com.wanandroid.core.event.CancelCollectSuccessEvent;
import json.chao.com.wanandroid.core.event.CollectSuccessEvent;
import json.chao.com.wanandroid.core.event.DismissErrorView;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.core.event.LogoutEvent;
import json.chao.com.wanandroid.core.event.ShowErrorView;
import json.chao.com.wanandroid.presenter.MainPagerPresenter;
import json.chao.com.wanandroid.ui.main.activity.LoginActivity;
import json.chao.com.wanandroid.ui.mainpager.adapter.KnowledgeHierarchyListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.GlideImageLoader;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2017/11/29
 */

public class MainPagerFragment extends BaseFragment<MainPagerPresenter> implements MainPagerContract.View {

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<FeedArticleData> mFeedArticleDataList;
    private KnowledgeHierarchyListAdapter mAdapter;
    private int mCurrentPage;
    private boolean isRefresh = true;

    @Inject
    DataManager mDataManager;
    private int articlePosition;
    private List<String> mBannerTitleList;
    private List<String> mBannerUrlList;
    private Banner mBanner;
    private int themeCount;

    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    public static MainPagerFragment getInstance(String param1, String param2) {
        MainPagerFragment fragment = new MainPagerFragment();
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
        return R.layout.fragment_main_pager;
    }

    @Override
    protected void initEventAndData() {
        setRefresh();
        mPresenter.getBannerData();
        mPresenter.getFeedArticleList(mCurrentPage);
        mFeedArticleDataList = new ArrayList<>();
        mAdapter = new KnowledgeHierarchyListAdapter(R.layout.item_search_pager, mFeedArticleDataList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            //记录点击的文章位置，便于在文章内点击收藏返回到此界面时能展示正确的收藏状态
            articlePosition = position;
            JudgeUtils.startArticleDetailActivity(_mActivity,
                    mAdapter.getData().get(position).getId(),
                    mAdapter.getData().get(position).getTitle(),
                    mAdapter.getData().get(position).getLink(),
                    mAdapter.getData().get(position).isCollect(),
                    false,
                    false);
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!mDataManager.getLoginStatus()) {
                startActivity(new Intent(_mActivity, LoginActivity.class));
                CommonUtils.showMessage(_mActivity, getString(R.string.login_tint));
                return;
            }
            if (mAdapter.getData().get(position).isCollect()) {
                mPresenter.cancelCollectArticle(position, mAdapter.getData().get(position));
            } else {
                mPresenter.addCollectArticle(position, mAdapter.getData().get(position));
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        //add head banner
        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.head_banner, null));
        mBanner = ((Banner) mHeaderGroup.findViewById(R.id.head_banner));
        mHeaderGroup.removeView(mBanner);
        mAdapter.addHeaderView(mBanner);

        mRecyclerView.setAdapter(mAdapter);

        //登录成功刷新数据
        RxBus.getDefault().toFlowable(LoginEvent.class)
                .subscribe(loginEvent -> {
                    mCurrentPage = 0;
                    mPresenter.getFeedArticleList(mCurrentPage);
                });

        RxBus.getDefault().toFlowable(LogoutEvent.class)
                .subscribe(loginEvent -> {
                    mCurrentPage = 0;
                    mPresenter.getFeedArticleList(mCurrentPage);
                });

        RxBus.getDefault().toFlowable(CollectSuccessEvent.class)
                .subscribe(collectSuccessEvent -> {
                    mAdapter.getData().get(articlePosition).setCollect(true);
                    mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
                });

        RxBus.getDefault().toFlowable(CancelCollectSuccessEvent.class)
                .subscribe(collectSuccessEvent -> {
                    mAdapter.getData().get(articlePosition).setCollect(false);
                    mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
                });
    }

    @Override
    public void showArticleList(FeedArticleListResponse feedArticleListResponse) {
        if (feedArticleListResponse == null || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null) {
            showArticleListFail();
            return;
        }
        RxBus.getDefault().post(new DismissErrorView());
        mRefreshLayout.setVisibility(View.VISIBLE);
        if (isRefresh) {
            mFeedArticleDataList = feedArticleListResponse.getData().getDatas();
            mAdapter.replaceData(feedArticleListResponse.getData().getDatas());
        } else {
            mFeedArticleDataList.addAll(feedArticleListResponse.getData().getDatas());
            mAdapter.addData(feedArticleListResponse.getData().getDatas());
        }
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showBannerData(BannerResponse bannerResponse) {
        if (bannerResponse == null || bannerResponse.getData() == null) {
            showBannerDataFail();
            return;
        }
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        List<BannerData> bannerDataList = bannerResponse.getData();
        for (BannerData bannerData : bannerDataList) {
            mBannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            mBannerUrlList.add(bannerData.getUrl());
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(bannerDataList.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);

        mBanner.setOnBannerListener(i -> JudgeUtils.startArticleDetailActivity(_mActivity,
                0, mBannerTitleList.get(i), mBannerUrlList.get(i),
                false, false, true));
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void showArticleListFail() {
        CommonUtils.showMessage(_mActivity, getString(R.string.failed_to_obtain_article_list));
    }

    @Override
    public void showBannerDataFail() {
        CommonUtils.showMessage(_mActivity, getString(R.string.failed_to_obtain_banner_data));
    }

    @Override
    public void showError() {
        mRefreshLayout.setVisibility(View.GONE);
        RxBus.getDefault().post(new ShowErrorView());
    }

    public void reLoad() {
        mRefreshLayout.autoRefresh();
        mPresenter.getBannerData();
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mCurrentPage = 0;
            mPresenter.getFeedArticleList(mCurrentPage);
            refreshLayout.finishRefresh(2000);
            setRefreshThemeColor();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mCurrentPage++;
            mPresenter.getFeedArticleList(mCurrentPage);
            refreshLayout.finishLoadMore(2000);
        });
    }

    private void setRefreshThemeColor() {
        themeCount++;
        if (themeCount % Constants.FOUR == Constants.ONE) {
            mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        } else if (themeCount % Constants.FOUR == Constants.TWO) {
            mRefreshLayout.setPrimaryColorsId(Constants.GREEN_THEME, R.color.white);
        } else if (themeCount % Constants.FOUR == Constants.THREE) {
            mRefreshLayout.setPrimaryColorsId(Constants.RED_THEME, R.color.white);
        } else if (themeCount % Constants.FOUR == Constants.ZERO) {
            mRefreshLayout.setPrimaryColorsId(Constants.ORANGE_THEME, R.color.white);
        }
    }
}
