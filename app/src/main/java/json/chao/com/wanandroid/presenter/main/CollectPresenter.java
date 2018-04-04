package json.chao.com.wanandroid.presenter.main;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.main.CollectContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BaseResponse;
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
    public void getCollectList(int page) {
        addSubscribe(mDataManager.getCollectList(page)
                    .compose(RxUtils.rxSchedulerHelper())
                    .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                        @Override
                        public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                            if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                mView.showCollectList(feedArticleListResponse);
                            } else {
                                mView.showCollectListFail();
                            }
                        }
                    }));
    }

    @Override
    public void cancelCollectPageArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectPageArticle(feedArticleData.getId())
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                            @Override
                            public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    feedArticleData.setCollect(false);
                                    mView.showCancelCollectPageArticleData(position, feedArticleData, feedArticleListResponse);
                                } else {
                                    mView.showCancelCollectFail();
                                }
                            }
                        }));
    }

}
