package json.chao.com.wanandroid.ui.main.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseRootFragment;
import json.chao.com.wanandroid.contract.main.CollectContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.presenter.main.CollectPresenter;
import json.chao.com.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;


/**
 * @author quchao
 * @date 2018/2/27
 */

public class CollectFragment extends BaseRootFragment<CollectPresenter> implements CollectContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.collect_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.collect_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private boolean isRefresh = true;
    private int mCurrentPage;
    private List<FeedArticleData> mArticles;
    private ArticleListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mPresenter.getCollectList(mCurrentPage, true);
        setRefresh();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showRefreshEvent();
    }

    @Override
    public void showCollectList(FeedArticleListData feedArticleListData) {
        if (mAdapter == null) {
            return;
        }
        mArticles = feedArticleListData.getDatas();
        if (isRefresh) {
            mAdapter.replaceData(mArticles);
        } else {
            showLoadMore(feedArticleListData);
        }
        if (mAdapter.getData().size() == 0) {
            CommonUtils.showSnackMessage(_mActivity, getString(R.string.no_collect));
        }
        showNormal();
    }

    @Override
    public void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.remove(position);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showRefreshEvent() {
        if (isVisible()) {
            mCurrentPage = 0;
            isRefresh = true;
            mPresenter.getCollectList(mCurrentPage, false);
        }
    }

    @OnClick({R.id.collect_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_floating_action_btn:
                mRecyclerView.smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void reload() {
        mRefreshLayout.autoRefresh();
    }

    public static CollectFragment getInstance(String param1, String param2) {
        CollectFragment fragment = new CollectFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void initRecyclerView() {
        mArticles = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mArticles);
        mAdapter.isCollectPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.item_search_pager_chapterName:
                startSingleChapterKnowledgePager(position);
                break;
            case R.id.item_search_pager_like_iv:
                cancelCollect(position);
                break;
            default:
                break;
        }
    }

    private void cancelCollect(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        mPresenter.cancelCollectPageArticle(position, mAdapter.getData().get(position));
    }

    private void startSingleChapterKnowledgePager(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        JudgeUtils.startKnowledgeHierarchyDetailActivity(_mActivity,
                true,
                mAdapter.getData().get(position).getChapterName(),
                mAdapter.getData().get(position).getChapterName(),
                mAdapter.getData().get(position).getChapterId());
    }

    private void startArticleDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(_mActivity, options,
            mAdapter.getData().get(position).getId(),
            mAdapter.getData().get(position).getTitle(),
            mAdapter.getData().get(position).getLink(),
            true,
            true,
            false);
    }

    private void showLoadMore(FeedArticleListData feedArticleListData) {
        if (mArticles.size() > 0) {
            mArticles.addAll(feedArticleListData.getDatas());
            mAdapter.addData(feedArticleListData.getDatas());
        } else {
            if (mAdapter.getData().size() != 0) {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
    }

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            showRefreshEvent();
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isRefresh = false;
            mPresenter.getCollectList(mCurrentPage, false);
            refreshLayout.finishLoadMore(1000);
        });
    }

}
