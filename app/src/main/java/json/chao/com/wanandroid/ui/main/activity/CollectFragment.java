package json.chao.com.wanandroid.ui.main.activity;

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
import json.chao.com.wanandroid.base.fragment.AbstractRootFragment;
import json.chao.com.wanandroid.contract.main.CollectContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
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

public class CollectFragment extends AbstractRootFragment<CollectPresenter> implements CollectContract.View {

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
    private ActivityOptions mOptions;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        initView();
        setRefresh();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showCollectList(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        if (feedArticleListResponse == null
                || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null) {
            showCollectListFail();
            return;
        }
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mArticles = feedArticleListResponse.getData().getDatas();
            mAdapter.replaceData(mArticles);
        } else {
            mArticles.addAll(feedArticleListResponse.getData().getDatas());
            mAdapter.addData(feedArticleListResponse.getData().getDatas());
        }
        if (mAdapter.getData().size() == 0) {
            CommonUtils.showSnackMessage(_mActivity, getString(R.string.no_collect));
        }
        showNormal();
    }

    @Override
    public void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        mAdapter.remove(position);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showCollectListFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_collection_data));
    }

    @Override
    public void showRefreshEvent() {
        mCurrentPage = 0;
        isRefresh = true;
        mPresenter.getCollectList(mCurrentPage);
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

    private void initView() {
        mArticles = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mArticles);
        mAdapter.isCollectPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mOptions = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
            JudgeUtils.startArticleDetailActivity(_mActivity, mOptions,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle(),
                mAdapter.getData().get(position).getLink(),
                true,
                true,
                false);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_search_pager_chapterName:
                    JudgeUtils.startKnowledgeHierarchyDetailActivity(_mActivity,
                            true,
                            mAdapter.getData().get(position).getChapterName(),
                            mAdapter.getData().get(position).getChapterName(),
                            mAdapter.getData().get(position).getChapterId());
                    break;
                case R.id.item_search_pager_like_iv:
                    //取消收藏
                    mPresenter.cancelCollectPageArticle(position, mAdapter.getData().get(position));
                    break;
                default:
                    break;
            }

        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mPresenter.getCollectList(mCurrentPage);
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
            mPresenter.getCollectList(mCurrentPage);
            refreshLayout.finishLoadMore(1000);
        });
    }

}
