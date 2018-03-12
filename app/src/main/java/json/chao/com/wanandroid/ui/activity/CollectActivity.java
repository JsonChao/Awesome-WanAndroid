package json.chao.com.wanandroid.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.BaseActivity;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.CollectContract;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.core.event.CancelCollectSuccessEvent;
import json.chao.com.wanandroid.presenter.CollectPresenter;
import json.chao.com.wanandroid.ui.adapter.KnowledgeHierarchyListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2018/2/27
 */

public class CollectActivity extends BaseActivity<CollectPresenter> implements CollectContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_back_ib)
    ImageButton mBackIb;
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
    private KnowledgeHierarchyListAdapter mAdapter;
    private int articlePosition;
    private int themeCount;

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

        RxBus.getDefault().toFlowable(CancelCollectSuccessEvent.class)
                .filter(cancelCollectSuccessEvent -> mAdapter.getData().size() > articlePosition)
                .subscribe(cancelCollectSuccessEvent -> mAdapter.remove(articlePosition));
    }

    @Override
    public void showCollectList(FeedArticleListResponse feedArticleListResponse) {
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
    public void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.remove(position);
        CommonUtils.showMessage(this, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showCollectListFail() {
        CommonUtils.showMessage(this, getString(R.string.failed_to_obtain_collection_data));
    }

    @OnClick({R.id.common_toolbar_back_ib, R.id.collect_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_toolbar_back_ib:
                onBackPressedSupport();
                break;
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
    }

    private void initView() {
        mArticles = new ArrayList<>();
        mAdapter = new KnowledgeHierarchyListAdapter(R.layout.item_search_pager, mArticles);
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
            refreshLayout.finishRefresh(2000);
            setRefreshThemeColor();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isRefresh = false;
            mPresenter.getCollectList(mCurrentPage);
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
