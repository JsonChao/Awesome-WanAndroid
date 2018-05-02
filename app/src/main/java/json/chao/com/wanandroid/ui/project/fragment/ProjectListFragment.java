package json.chao.com.wanandroid.ui.project.fragment;

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
import json.chao.com.wanandroid.base.fragment.AbstractRootFragment;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.bean.project.ProjectListData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.contract.project.ProjectListContract;
import json.chao.com.wanandroid.presenter.project.ProjectListPresenter;
import json.chao.com.wanandroid.ui.project.adapter.ProjectListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class ProjectListFragment extends AbstractRootFragment<ProjectListPresenter> implements ProjectListContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.project_list_recycler_view)
    RecyclerView mRecyclerView;

    private List<FeedArticleData> mDatas;
    private ProjectListAdapter mAdapter;
    private boolean isRefresh = true;
    private int mCurrentPage;
    private int cid;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        setRefresh();
        Bundle bundle = getArguments();
        cid = bundle.getInt(Constants.ARG_PARAM1);
        mDatas = new ArrayList<>();
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, mDatas);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                        return;
                    }
                    JudgeUtils.startArticleDetailActivity(_mActivity,
                            null,
                            mAdapter.getData().get(position).getId(),
                            mAdapter.getData().get(position).getTitle().trim(),
                            mAdapter.getData().get(position).getLink().trim(),
                            mAdapter.getData().get(position).isCollect(),
                            false,
                            true);
                });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_project_list_install_tv:
                    if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                        return;
                    }
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
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
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
    public void reload() {
        if (mPresenter != null) {
            mPresenter.getProjectListData(0, cid);
        }
    }

    @Override
    public void showProjectListData(ProjectListData projectListData) {
        mDatas = projectListData.getDatas();
        if (isRefresh) {
            mAdapter.replaceData(mDatas);
        } else {
            if (mDatas.size() > 0) {
                mAdapter.addData(mDatas);
            } else {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.cancel_collect_success));
    }

    @Override
    public void showJumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void setRefresh() {
        mCurrentPage = 1;
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 1;
            isRefresh = true;
            mPresenter.getProjectListData(mCurrentPage, cid);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isRefresh = false;
            mPresenter.getProjectListData(mCurrentPage, cid);
            refreshLayout.finishLoadMore(1000);
        });
    }

}
