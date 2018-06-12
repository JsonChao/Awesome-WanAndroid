package json.chao.com.wanandroid.presenter.main;

import javax.inject.Inject;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.main.CollectContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/27
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    private DataManager mDataManager;

    @Inject
    CollectPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CollectContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .subscribe(collectEvent -> mView.showRefreshEvent()));
    }

    @Override
    public void getCollectList(int page, boolean isShowError) {
        addSubscribe(mDataManager.getCollectList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_collection_data),
                        isShowError) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showCollectList(feedArticleListData);
                    }
                }));
    }

    @Override
    public void cancelCollectPageArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectPageArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(false);
                        mView.showCancelCollectPageArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }

}
