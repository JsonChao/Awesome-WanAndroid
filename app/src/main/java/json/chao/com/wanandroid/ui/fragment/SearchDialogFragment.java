package json.chao.com.wanandroid.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.app.GeeksApp;
import json.chao.com.wanandroid.base.BaseDialogFragment;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.SearchContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.TopSearchData;
import json.chao.com.wanandroid.core.bean.TopSearchDataResponse;
import json.chao.com.wanandroid.core.bean.UsefulSiteData;
import json.chao.com.wanandroid.core.bean.UsefulSitesResponse;
import json.chao.com.wanandroid.core.dao.HistoryData;
import json.chao.com.wanandroid.core.event.CancelCollectSuccessEvent;
import json.chao.com.wanandroid.core.event.CollectSuccessEvent;
import json.chao.com.wanandroid.presenter.SearchPresenter;
import json.chao.com.wanandroid.ui.activity.LoginActivity;
import json.chao.com.wanandroid.ui.adapter.KnowledgeHierarchyListAdapter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;
import json.chao.com.wanandroid.utils.KeyBoardUtils;
import json.chao.com.wanandroid.widget.CircularRevealAnim;

/**
 * @author quchao
 * @date 2018/3/1
 */

public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter> implements
        SearchContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener ,
        DialogInterface.OnKeyListener {

    @BindView(R.id.search_coordinator_group)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.search_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_back_ib)
    ImageButton mBackIb;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;
    @BindView(R.id.history_search_flow_layout)
    TagFlowLayout mHistorySearchFlowLayout;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout mTopSearchFlowLayout;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;
    @BindView(R.id.search_recycler_view)
    RecyclerView mSearchRecyclerView;
    @BindView(R.id.search_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private List<TopSearchData> mTopSearchDataList;
    private List<UsefulSiteData> mUsefulSiteDataList;
    private int mCurrentPage;
    private List<FeedArticleData> mArticleList;
    private KnowledgeHierarchyListAdapter mAdapter;
    private boolean isSearchView;
    private boolean isAddData;

    @Inject
    DataManager mDataManager;
    private int articlePosition;
    private CircularRevealAnim mCircularRevealAnim;
    private int themeCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }
    
    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        mTopSearchDataList = new ArrayList<>();
        mUsefulSiteDataList = new ArrayList<>();
        mArticleList = new ArrayList<>();

        showHistoryData(GeeksApp.getInstance().getDaoSession().getHistoryDataDao().loadAll());

        mAdapter = new KnowledgeHierarchyListAdapter(R.layout.item_search_pager, mArticleList);
        mAdapter.isSearchPage();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            articlePosition = position;
            JudgeUtils.startArticleDetailActivity(getActivity(),
                    mAdapter.getData().get(position).getId(),
                    mAdapter.getData().get(position).getTitle(),
                    mAdapter.getData().get(position).getLink(),
                    mAdapter.getData().get(position).isCollect(),
                    false,
                    false);
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!mDataManager.getLoginStatus()) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                CommonUtils.showMessage(getActivity(), getString(R.string.login_tint));
                return;
            }
            if (mAdapter.getData().get(position).isCollect()) {
                mPresenter.cancelCollectArticle(position, mAdapter.getData().get(position));
            } else {
                mPresenter.addCollectArticle(position, mAdapter.getData().get(position));
            }
        });
        mSearchRecyclerView.setAdapter(mAdapter);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setRefresh();
        mPresenter.getTopSearchData();
        mPresenter.getUsefulSites();
        RxView.clicks(mSearchTv)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .subscribe(o -> {
                    isSearchView = true;
                    isAddData = false;
                    mCurrentPage = 0;
                    mPresenter.addHistoryData(mSearchEdit.getText().toString().trim());
                    mPresenter.getSearchList(mCurrentPage, mSearchEdit.getText().toString().trim());
                });

        RxBus.getDefault().toFlowable(CollectSuccessEvent.class)
                .filter(collectSuccessEvent -> mAdapter.getData().size() > articlePosition)
                .subscribe(collectSuccessEvent -> {
                    mAdapter.getData().get(articlePosition).setCollect(true);
                    mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
                });

        RxBus.getDefault().toFlowable(CancelCollectSuccessEvent.class)
                .filter(cancelCollectSuccessEvent -> mAdapter.getData().size() > articlePosition)
                .subscribe(collectSuccessEvent -> {
                    mAdapter.getData().get(articlePosition).setCollect(false);
                    mAdapter.setData(articlePosition, mAdapter.getData().get(articlePosition));
                });
    }

    @Override
    public void showTopSearchData(TopSearchDataResponse topSearchDataResponse) {
        if (topSearchDataResponse == null) {
            showTopSearchDataFail();
            return;
        }
        mTopSearchDataList = topSearchDataResponse.getData();
        mTopSearchFlowLayout.setAdapter(new TagAdapter<TopSearchData>(mTopSearchDataList) {
            @Override
            public View getView(FlowLayout parent, int position, TopSearchData topSearchData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert topSearchData != null;
                String name = topSearchData.getName();
                tv.setText(name);
                tv.setTextColor(CommonUtils.intrandomColor());
                mTopSearchFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    mSearchEdit.setText(mTopSearchDataList.get(position1).getName().trim());
                    mSearchEdit.setSelection(mSearchEdit.getText().length());
                    isSearchView = true;
                    isAddData = false;
                    mCurrentPage = 0;
                    mPresenter.getSearchList(mCurrentPage, mTopSearchDataList.get(position1).getName().trim());
                    mPresenter.addHistoryData(mTopSearchDataList.get(position1).getName().trim());
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void showUsefulSites(UsefulSitesResponse usefulSitesResponse) {
        if (usefulSitesResponse == null) {
            showUsefulSitesDataFail();
            return;
        }
        mUsefulSiteDataList = usefulSitesResponse.getData();
        mUsefulSitesFlowLayout.setAdapter(new TagAdapter<UsefulSiteData>(mUsefulSiteDataList) {
            @Override
            public View getView(FlowLayout parent, int position, UsefulSiteData usefulSiteData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert usefulSiteData != null;
                String name = usefulSiteData.getName();
                tv.setText(name);
                tv.setTextColor(CommonUtils.intrandomColor());
                mUsefulSitesFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    JudgeUtils.startArticleDetailActivity(getActivity(),
                            mUsefulSiteDataList.get(position1).getId(),
                            mUsefulSiteDataList.get(position1).getName().trim(),
                            mUsefulSiteDataList.get(position1).getLink().trim(),
                            false,
                            false,
                            true);
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        if (historyDataList == null) {
            return;
        }
        Collections.reverse(historyDataList);
        mHistorySearchFlowLayout.setAdapter(new TagAdapter<HistoryData>(historyDataList) {
            @Override
            public View getView(FlowLayout parent, int position, HistoryData historyData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert historyData != null;
                String data = historyData.getData();
                tv.setText(data);
                tv.setTextColor(CommonUtils.intrandomColor());
                mHistorySearchFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    mSearchEdit.setText(historyDataList.get(position1).getData().trim());
                    mSearchEdit.setSelection(mSearchEdit.getText().length());
                    isSearchView = true;
                    isAddData = false;
                    mCurrentPage = 0;
                    mPresenter.getSearchList(mCurrentPage, historyDataList.get(position1).getData().trim());
                    mPresenter.addHistoryData(historyDataList.get(position1).getData().trim());
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void showSearchList(FeedArticleListResponse feedArticleListResponse) {
        if (feedArticleListResponse == null
                || feedArticleListResponse.getData() == null
                || feedArticleListResponse.getData().getDatas() == null) {
            showSearchListFail();
            return;
        }
        FeedArticleListData articleData = feedArticleListResponse.getData();
        mArticleList = articleData.getDatas();
        if (isAddData) {
            mAdapter.addData(mArticleList);
        } else {
            mAdapter.replaceData(mArticleList);
        }
        assert getActivity() != null;
        mRefreshLayout.setVisibility(View.VISIBLE);
        mSearchScrollView.setVisibility(View.GONE);
    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showMessage(getActivity(), getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {
        mAdapter.setData(position, feedArticleData);
        CommonUtils.showMessage(getActivity(), getString(R.string.cancel_collect_success));
    }

    @Override
    public void showSearchListFail() {
        CommonUtils.showMessage(getActivity(), getString(R.string.failed_to_obtain_search_data_list));
    }

    @Override
    public void showTopSearchDataFail() {
        CommonUtils.showMessage(getActivity(), getString(R.string.failed_to_obtain_top_data));
    }

    @Override
    public void showUsefulSitesDataFail() {
        CommonUtils.showMessage(getActivity(), getString(R.string.failed_to_obtain_useful_sites_data));
    }

    @Override
    public void onHideAnimationEnd() {
        mSearchEdit.setText("");
        dismiss();
    }

    @Override
    public void onShowAnimationEnd() {
        KeyBoardUtils.openKeyboard(getActivity(), mSearchEdit);
    }

    @Override
    public boolean onPreDraw() {
        mSearchEdit.getViewTreeObserver().removeOnPreDrawListener(this);
        mCircularRevealAnim.show(mSearchEdit, mRootView);
        return true;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backEvent();
            return true;
        } else {
            return false;
        }
    }

    @OnClick({R.id.search_back_ib, R.id.search_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_ib:
                backEvent();
                break;
            case R.id.search_floating_action_btn:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }

    private void jumpToTheTop() {
        if (mSearchScrollView.getVisibility() == View.VISIBLE) {
            mSearchScrollView.smoothScrollTo(0, 0);
        } else {
            mSearchRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void initCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);
        mSearchEdit.getViewTreeObserver().addOnPreDrawListener(this);
    }

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mCurrentPage = 0;
            isAddData = false;
            mPresenter.getSearchList(mCurrentPage, mSearchEdit.getText().toString().trim());
            isSearchView = true;
            refreshLayout.finishRefresh(2000);
            setRefreshThemeColor();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mCurrentPage++;
            isAddData = true;
            mPresenter.getSearchList(mCurrentPage, mSearchEdit.getText().toString().trim());
            isSearchView = true;
            refreshLayout.finishLoadMore(2000);
        });
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    private void backEvent() {
        if (isSearchView) {
            showHistoryData(GeeksApp.getInstance().getDaoSession().getHistoryDataDao().loadAll());
            isSearchView = false;
            mSearchScrollView.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        } else {
            KeyBoardUtils.closeKeyboard(getActivity(), mSearchEdit);
            mCircularRevealAnim.hide(mSearchEdit, mRootView);
        }
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
