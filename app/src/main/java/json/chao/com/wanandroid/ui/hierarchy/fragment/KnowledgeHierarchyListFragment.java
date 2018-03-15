package json.chao.com.wanandroid.ui.hierarchy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import json.chao.com.wanandroid.core.event.CancelCollectSuccessEvent;
import json.chao.com.wanandroid.core.event.CollectSuccessEvent;
import json.chao.com.wanandroid.core.event.DismissDetailErrorView;
import json.chao.com.wanandroid.core.event.KnowledgeJumpTopEvent;
import json.chao.com.wanandroid.core.event.ReloadDetailEvent;
import json.chao.com.wanandroid.core.event.ShowDetailErrorView;
import json.chao.com.wanandroid.presenter.hierarchy.KnowledgeHierarchyListPresenter;
import json.chao.com.wanandroid.ui.main.activity.LoginActivity;
import json.chao.com.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2018/2/23
 */

public class KnowledgeHierarchyListFragment extends BaseFragment<KnowledgeHierarchyListPresenter>
        implements KnowledgeHierarchyListContract.View {

    @BindView(R.id.knowledge_hierarchy_list_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_list_recycler_view)
    RecyclerView mRecyclerView;

    private KnowledgeHierarchyData mKnowledgeHierarchyData;
    private int mCurrentPage;
    private List<FeedArticleData> mArticles;
    private ArticleListAdapter mAdapter;
    private boolean isRefresh = true;

    @Inject
    DataManager mDataManager;
    private int articlePosition;
    private int themeCount;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge_hierarchy_list;
    }

    @Override
    protected void initEventAndData() {
        isInnerFragment = true;
        setRefresh();
        Bundle bundle = getArguments();
        mKnowledgeHierarchyData = ((KnowledgeHierarchyData) bundle.getSerializable(Constants.ARG_PARAM1));
        if (mKnowledgeHierarchyData == null) {
            return;
        }
        //重置当前页数，防止页面切换后当前页数为较大而加载后面的数据或没有数据
        mCurrentPage = 0;
        mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, mKnowledgeHierarchyData.getId());
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mArticles);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            articlePosition = position;
            JudgeUtils.startArticleDetailActivity(_mActivity,
                    mAdapter.getData().get(position).getId(),
                    mAdapter.getData().get(position).getTitle().trim(),
                    mAdapter.getData().get(position).getLink().trim(),
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
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        RxBus.getDefault().toFlowable(CollectSuccessEvent.class)
                .filter(collectSuccessEvent -> mAdapter != null && mAdapter.getData().size() > articlePosition)
                .subscribe(collectSuccessEvent -> {
                    mAdapter.getData().get(articlePosition).setCollect(true);
                    mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
                });

        RxBus.getDefault().toFlowable(CancelCollectSuccessEvent.class)
                .filter(collectSuccessEvent -> mAdapter != null && mAdapter.getData().size() > articlePosition)
                .subscribe(collectSuccessEvent -> {
                    mAdapter.getData().get(articlePosition).setCollect(false);
                    mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
                });

        RxBus.getDefault().toFlowable(KnowledgeJumpTopEvent.class)
                .filter(knowledgeJumpTopEvent -> mRecyclerView != null)
                .subscribe(knowledgeJumpTopEvent -> mRecyclerView.smoothScrollToPosition(0));

        RxBus.getDefault().toFlowable(ReloadDetailEvent.class)
                .filter(reloadEvent -> mRefreshLayout != null)
                .subscribe(reloadEvent -> mRefreshLayout.autoRefresh());
    }

    @Override
    public void showKnowledgeHierarchyDetailData(FeedArticleListResponse feedArticleListResponse) {
        if (feedArticleListResponse == null
                || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null) {
            showKnowledgeHierarchyDetailDataFail();
            return;
        }
        RxBus.getDefault().post(new DismissDetailErrorView());
        mRefreshLayout.setVisibility(View.VISIBLE);
        mArticles = feedArticleListResponse.getData().getDatas();
        if (isRefresh) {
            mAdapter.replaceData(mArticles);
        } else {
            mAdapter.addData(mArticles);
        }
    }

    @Override
    public void showError() {
        RxBus.getDefault().post(new ShowDetailErrorView());
        mRefreshLayout.setVisibility(View.GONE);
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
    public void showKnowledgeHierarchyDetailDataFail() {
        CommonUtils.showMessage(_mActivity, getString(R.string.failed_to_obtain_knowledge_data));
    }

    public static KnowledgeHierarchyListFragment getInstance(Serializable param1, String param2) {
        KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            if (mKnowledgeHierarchyData != null) {
                isRefresh = true;
                mPresenter.getKnowledgeHierarchyDetailData(0, mKnowledgeHierarchyData.getId());
            }
            refreshLayout.finishRefresh(2000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            if (mKnowledgeHierarchyData != null) {
                isRefresh = false;
                mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, mKnowledgeHierarchyData.getId());
            }
            refreshLayout.finishLoadMore(2000);
        });
    }
}
