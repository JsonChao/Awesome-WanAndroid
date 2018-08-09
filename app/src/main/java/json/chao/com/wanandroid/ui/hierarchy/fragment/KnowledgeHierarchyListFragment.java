package json.chao.com.wanandroid.ui.hierarchy.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import json.chao.com.wanandroid.base.fragment.BaseRootFragment;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.event.SwitchNavigationEvent;
import json.chao.com.wanandroid.core.event.SwitchProjectEvent;
import json.chao.com.wanandroid.presenter.hierarchy.KnowledgeHierarchyListPresenter;
import json.chao.com.wanandroid.ui.main.activity.LoginActivity;
import json.chao.com.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2018/2/23
 */
public class KnowledgeHierarchyListFragment extends BaseRootFragment<KnowledgeHierarchyListPresenter>
        implements KnowledgeHierarchyListContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_list_recycler_view)
    RecyclerView mRecyclerView;

    private int id;
    private int mCurrentPage;
    private ArticleListAdapter mAdapter;
    private boolean isRefresh = true;
    private int articlePosition;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy_list;
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        isInnerFragment = true;
        setRefresh();
        Bundle bundle = getArguments();
        id = bundle.getInt(Constants.ARG_PARAM1, 0);
        if (id == 0) {
            return;
        }
        //重置当前页数，防止页面切换后当前页数为较大而加载后面的数据或没有数据
        mCurrentPage = 0;
        mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, id, true);
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showKnowledgeHierarchyDetailData(FeedArticleListData feedArticleListData) {
        if (isRefresh) {
            mAdapter.replaceData(feedArticleListData.getDatas());
        } else {
            if (feedArticleListData.getDatas().size() > 0) {
                mAdapter.addData(feedArticleListData.getDatas());
            } else {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void reload() {
        if (mPresenter != null) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(false));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        RxBus.getDefault().post(new CollectEvent(true));
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showJumpTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showReloadDetailEvent() {
        if (mRefreshLayout != null) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void showCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(true);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    @Override
    public void showCancelCollectSuccess() {
        if (mAdapter != null && mAdapter.getData().size() > articlePosition) {
            mAdapter.getData().get(articlePosition).setCollect(false);
            mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
        }
    }

    public static KnowledgeHierarchyListFragment getInstance(int id, String param2) {
        KnowledgeHierarchyListFragment fragment = new KnowledgeHierarchyListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, id);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void initRecyclerView() {
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view, position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void clickChildEvent(View view, int position) {
        switch (view.getId()) {
            case R.id.item_search_pager_chapterName:
                break;
            case R.id.item_search_pager_like_iv:
                likeEvent(position);
                break;
            case R.id.item_search_pager_tag_red_tv:
                clickTag(position);
                break;
            default:
                break;
        }
    }

    private void clickTag(int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        String superChapterName = mAdapter.getData().get(position).getSuperChapterName();
        if (superChapterName.contains(getString(R.string.open_project))) {
            RxBus.getDefault().post(new SwitchProjectEvent());
        } else if (superChapterName.contains(getString(R.string.navigation))) {
            RxBus.getDefault().post(new SwitchNavigationEvent());
        }
    }

    private void startArticleDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        articlePosition = position;
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        JudgeUtils.startArticleDetailActivity(_mActivity,
                options,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle().trim(),
                mAdapter.getData().get(position).getLink().trim(),
                mAdapter.getData().get(position).isCollect(),
                false,
                false);
    }

    private void likeEvent(int position) {
        if (!mPresenter.getLoginStatus()) {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            CommonUtils.showMessage(_mActivity, getString(R.string.login_tint));
            return;
        }
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        if (mAdapter.getData().get(position).isCollect()) {
            mPresenter.cancelCollectArticle(position, mAdapter.getData().get(position));
        } else {
            mPresenter.addCollectArticle(position, mAdapter.getData().get(position));
        }
    }

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            if (id != 0) {
                isRefresh = true;
                mPresenter.getKnowledgeHierarchyDetailData(0, id, false);
            }
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            if (id != 0) {
                isRefresh = false;
                mPresenter.getKnowledgeHierarchyDetailData(mCurrentPage, id, false);
            }
            refreshLayout.finishLoadMore(1000);
        });
    }
}
