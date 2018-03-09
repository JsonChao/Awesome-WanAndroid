package json.chao.com.wanandroid.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.ProjectListResponse;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.BaseFragment;
import json.chao.com.wanandroid.contract.ProjectListContract;
import json.chao.com.wanandroid.core.event.JumpToTheTopEvent;
import json.chao.com.wanandroid.presenter.ProjectListPresenter;
import json.chao.com.wanandroid.ui.adapter.ProjectListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class ProjectListFragment extends BaseFragment<ProjectListPresenter> implements ProjectListContract.View {

    @BindView(R.id.project_list_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.project_list_recycler_view)
    RecyclerView mRecyclerView;

    private List<FeedArticleData> mDatas;
    private ProjectListAdapter mAdapter;
    private boolean isRefresh = true;
    private int mCurrentPage;
    private int cid;
    private int themeCount;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initEventAndData() {
        isInnerFragment = true;
        setRefresh();
        Bundle bundle = getArguments();
        cid = bundle.getInt(Constants.ARG_PARAM1);
        mDatas = new ArrayList<>();
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, mDatas);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                JudgeUtils.startArticleDetailActivity(_mActivity,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle().trim(),
                mAdapter.getData().get(position).getLink().trim(),
                mAdapter.getData().get(position).isCollect(),
                false,
                true));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_project_list_install_tv:
                    if (TextUtils.isEmpty(mAdapter.getData().get(position).getApkLink())) {
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mAdapter.getData().get(position).getApkLink())));
                    break;
                default:
                    break;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mPresenter.getProjectListData(mCurrentPage, cid);

        RxBus.getDefault().toFlowable(JumpToTheTopEvent.class)
                .filter(jumpToTheTopEvent -> mRecyclerView != null)
                .subscribe(jumpToTheTopEvent -> mRecyclerView.smoothScrollToPosition(0));
    }

    public static ProjectListFragment getInstance(int param1, String param2) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showProjectListData(ProjectListResponse projectListResponse) {
        if (projectListResponse == null || projectListResponse.getData() == null ||
                projectListResponse.getData().getDatas() == null) {
            showProjectListFail();
            return;
        }
        mDatas = projectListResponse.getData().getDatas();

        if (isRefresh) {
            mAdapter.replaceData(mDatas);
        } else {
            mAdapter.addData(mDatas);
        }
    }

    @Override
    public void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showProjectListFail() {
        CommonUtils.showMessage(_mActivity, getString(R.string.failed_to_obtain_project_list));
    }

    private void setRefresh() {
        mCurrentPage = 1;
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 1;
            isRefresh = true;
            mPresenter.getProjectListData(mCurrentPage, cid);
            refreshLayout.finishRefresh(2000);
            setRefreshThemeColor();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isRefresh = false;
            mPresenter.getProjectListData(mCurrentPage, cid);
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
