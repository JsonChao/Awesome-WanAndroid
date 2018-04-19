package json.chao.com.wanandroid.ui.hierarchy.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import json.chao.com.wanandroid.base.fragment.AbstractRootFragment;
import json.chao.com.wanandroid.core.bean.BaseResponse;
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

public class KnowledgeHierarchyFragment extends AbstractRootFragment<KnowledgeHierarchyPresenter>
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
        mAdapter = new KnowledgeHierarchyAdapter(R.layout.item_knowledge_hierarchy, mKnowledgeHierarchyDataList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() <= position) {
                return;
            }
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(_mActivity, view, getString(R.string.share_view));
            Intent intent = new Intent(_mActivity, KnowledgeHierarchyDetailActivity.class);
            intent.putExtra(Constants.ARG_PARAM1, mAdapter.getData().get(position));
            if (!Build.BOARD.contains("samsung") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mPresenter.getKnowledgeHierarchyData();
        if (CommonUtils.isNetworkConnected()) {
            showLoading();
        }
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showKnowledgeHierarchyData(BaseResponse<List<KnowledgeHierarchyData>> knowledgeHierarchyResponse) {
        if (knowledgeHierarchyResponse == null || knowledgeHierarchyResponse.getData() == null) {
            showKnowledgeHierarchyDetailDataFail();
            return;
        }
        if (mPresenter.getCurrentPage() == 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mAdapter.getData().size() < knowledgeHierarchyResponse.getData().size()) {
            mKnowledgeHierarchyDataList = knowledgeHierarchyResponse.getData();
            mAdapter.replaceData(mKnowledgeHierarchyDataList);
        } else {
            if (!isRefresh) {
                CommonUtils.showMessage(_mActivity, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void showKnowledgeHierarchyDetailDataFail() {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.failed_to_obtain_knowledge_data));
    }

    @Override
    public void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        super.showError();
    }

    @Override
    public void reload() {
        if (mPresenter != null && mRecyclerView.getVisibility() == View.INVISIBLE) {
            mPresenter.getKnowledgeHierarchyData();
        }
    }

    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void setRefresh() {
        mRefreshLayout.setPrimaryColorsId(Constants.BLUE_THEME, R.color.white);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mPresenter.getKnowledgeHierarchyData();
            refreshLayout.finishRefresh(1000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isRefresh = false;
            mPresenter.getKnowledgeHierarchyData();
            refreshLayout.finishLoadMore(1000);
        });
    }

}
