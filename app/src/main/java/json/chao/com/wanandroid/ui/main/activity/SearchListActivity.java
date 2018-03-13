package json.chao.com.wanandroid.ui.main.activity;

import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.contract.main.SearchListContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.presenter.main.SearchListPresenter;

/**
 * @author quchao
 * @date 2018/3/13
 */

public class SearchListActivity extends BaseActivity<SearchListPresenter> implements SearchListContract.View {

    @Override
    public void showSearchList(FeedArticleListResponse feedArticleListResponse) {

    }

    @Override
    public void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {

    }

    @Override
    public void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse) {

    }

    @Override
    public void showSearchListFail() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initInject() {

    }
}
