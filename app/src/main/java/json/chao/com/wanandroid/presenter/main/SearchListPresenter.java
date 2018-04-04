package json.chao.com.wanandroid.presenter.main;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.main.SearchListContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/3/13
 */

public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter {

    private DataManager mDataManager;

    @Inject
    SearchListPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(SearchListContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(collectEvent -> !collectEvent.isCancelCollectSuccess())
                .subscribe(collectEvent -> mView.showCollectSuccess()));

        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class)
                .filter(CollectEvent::isCancelCollectSuccess)
                .subscribe(collectEvent -> mView.showCancelCollectSuccess()));
    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }

    @Override
    public void getSearchList(int page, String k) {
        addSubscribe(mDataManager.getSearchList(page, k)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                    @Override
                    public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                        if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            mView.showSearchList(feedArticleListResponse);
                        } else {
                            mView.showSearchListFail();
                        }
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                    @Override
                    public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                        if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            feedArticleData.setCollect(true);
                            mView.showCollectArticleData(position, feedArticleData, feedArticleListResponse);
                        } else {
                            mView.showCancelCollectFail();
                        }
                    }
                }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                    @Override
                    public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                        if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            feedArticleData.setCollect(false);
                            mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListResponse);
                        } else {
                            mView.showCancelCollectFail();
                        }
                    }
                }));
    }
}
