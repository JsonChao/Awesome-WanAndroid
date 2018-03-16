package json.chao.com.wanandroid.ui.hierarchy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyResponse;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import json.chao.com.wanandroid.core.event.DismissErrorView;
import json.chao.com.wanandroid.core.event.ShowErrorView;
import json.chao.com.wanandroid.presenter.hierarchy.KnowledgeHierarchyPresenter;
import json.chao.com.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import json.chao.com.wanandroid.ui.hierarchy.adapter.KnowledgeHierarchyAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;

/**
 * @author quchao
 * @date 2017/11/29
 */

public class KnowledgeHierarchyFragment extends BaseFragment<KnowledgeHierarchyPresenter>
        implements KnowledgeHierarchyContract.View {

    @BindView(R.id.knowledge_hierarchy_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    DataManager mDataManager;
    private List<KnowledgeHierarchyData> mKnowledgeHierarchyDataList;
    private KnowledgeHierarchyAdapter mAdapter;

    public static KnowledgeHierarchyFragment getInstance(String param1, String param2) {
        KnowledgeHierarchyFragment fragment = new KnowledgeHierarchyFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initEventAndData() {
        setRefresh();
        mAdapter = new KnowledgeHierarchyAdapter(R.layout.item_knowledge_hierarchy, mKnowledgeHierarchyDataList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(_mActivity, KnowledgeHierarchyDetailActivity.class);
            intent.putExtra(Constants.ARG_PARAM1, mAdapter.getData().get(position));
            startActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mPresenter.getKnowledgeHierarchyData();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showKnowledgeHierarchyData(KnowledgeHierarchyResponse knowledgeHierarchyResponse) {
        if (knowledgeHierarchyResponse == null || knowledgeHierarchyResponse.getData() == null) {
            showKnowledgeHierarchyDetailDataFail();
            return;
        }
        RxBus.getDefault().post(new DismissErrorView());
        if (mDataManager.getCurrentPage() == Constants.SECOND) {
            mRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            mRefreshLayout.setVisibility(View.GONE);
        }
        mKnowledgeHierarchyDataList = knowledgeHierarchyResponse.getData();
        mAdapter.replaceData(mKnowledgeHierarchyDataList);
    }

    @Override
    public void showKnowledgeHierarchyDetailDataFail() {
        CommonUtils.showMessage(_mActivity, getString(R.string.failed_to_obtain_knowledge_data));
    }

    @Override
    public void showError() {
        mRefreshLayout.setVisibility(View.GONE);
        RxBus.getDefault().post(new ShowErrorView());
    }

    public void reLoad() {
        if (mPresenter != null) {
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
            mPresenter.getKnowledgeHierarchyData();
            refreshLayout.finishRefresh(2000);
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.getKnowledgeHierarchyData();
            refreshLayout.finishLoadMore(2000);
        });
    }

}
