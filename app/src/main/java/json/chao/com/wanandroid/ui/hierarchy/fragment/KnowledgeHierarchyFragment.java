package json.chao.com.wanandroid.ui.hierarchy.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import json.chao.com.wanandroid.base.fragment.BaseRootFragment;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import json.chao.com.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;
import json.chao.com.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import json.chao.com.wanandroid.ui.hierarchy.adapter.KnowledgeHierarchyAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;

/**
 * @author quchao
 * @date 2017/11/29
 */

public class KnowledgeHierarchyFragment extends BaseRootFragment<KnowledgeHierarchyPresenter>
        implements KnowledgeHierarchyContract.View {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView mRecyclerView;

    private List<KnowledgeHierarchyData> mKnowledgeHierarchyDataList;
    private KnowledgeHierarchyAdapter mAdapter;
    private boolean isRefresh;

    public static KnowledgeHierarchyFragment getInstance(String param1, String param2) {
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        setRefresh();
        mPresenter.getKnowledgeHierarchyData(true);
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    public void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList) {
        if (mPresenter.getCurrentPage() == 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mAdapter.getData().size() < knowledgeHierarchyDataList.size()) {
            mKnowledgeHierarchyDataList = knowledgeHierarchyDataList;
            mAdapter.replaceData(mKnowledgeHierarchyDataList);
        } else {
            if (!isRefresh) {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void reload() {
        if (mPresenter != null && mRecyclerView.getVisibility() == View.INVISIBLE) {
            mPresenter.getKnowledgeHierarchyData(false);
        }
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mKnowledgeHierarchyDataList = new ArrayList<>();
        mAdapter = new KnowledgeHierarchyAdapter(R.layout.item_knowledge_hierarchy, mKnowledgeHierarchyDataList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startDetailPager(view, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void startDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
            return;
        }
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
        Intent intent = new Intent(_mActivity, KnowledgeHierarchyDetailActivity.class);
        intent.putExtra(Constants.ARG_PARAM1, mAdapter.getData().get(position));
        if (modelFiltering()) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    /**
     * 机型适配
     *
     * @return 返回true表示非三星机型且Android 6.0以上
     */
    private boolean modelFiltering() {
        return !Build.MANUFACTURER.contains(Constants.SAMSUNG) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPresenter.getKnowledgeHierarchyData(false);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPresenter.getKnowledgeHierarchyData(false);
            refreshLayout.finishLoadMore(1000);
        });
    }

}
