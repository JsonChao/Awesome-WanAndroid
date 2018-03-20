package json.chao.com.wanandroid.ui.main.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.contract.main.CollectContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.presenter.main.CollectPresenter;
import json.chao.com.wanandroid.ui.mainpager.adapter.ArticleListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;
import json.chao.com.wanandroid.utils.StatusBarUtil;

/**
 * @author quchao
 * @date 2018/2/27
 */

public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.collect_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.collect_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.collect_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private boolean isRefresh = true;
    private int mCurrentPage;
    private List<FeedArticleData> mArticles;
    private ArticleListAdapter mAdapter;
    private int articlePosition;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initEventAndData() {
        initToolbar();
        initView();
        setRefresh();
    }

    @Override
    public void showCollectList(BaseResponse<FeedArticleListData> feedArticleListResponse) {
        if (feedArticleListResponse == null
                || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null) {
            showCollectListFail();
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
            CommonUtils.showMessage(this, getString(R.string.no_collect));
        }
    }

    @Override
    public void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse) {
        mAdapter.remove(position);
        CommonUtils.showMessage(this, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showCollectListFail() {
        CommonUtils.showMessage(this, getString(R.string.failed_to_obtain_collection_data));
    }

    @Override
    public void showCancelCollectSuccess() {
        if (mAdapter.getData().size() > articlePosition) {
            mAdapter.remove(articlePosition);
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

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(getString(R.string.my_collect));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void initView() {
        mArticles = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mArticles);
        mAdapter.isCollectPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    articlePosition = position;
                    JudgeUtils.startArticleDetailActivity(this,
                            mAdapter.getData().get(position).getId(),
                            mAdapter.getData().get(position).getTitle(),
                            mAdapter.getData().get(position).getLink(),
                            true,
                            true,
                            false);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //取消收藏
            mPresenter.cancelCollectPageArticle(position, mAdapter.getData().get(position));
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.getCollectList(mCurrentPage);
    }

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            isRefresh = true;
            mPresenter.getCollectList(mCurrentPage);
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
