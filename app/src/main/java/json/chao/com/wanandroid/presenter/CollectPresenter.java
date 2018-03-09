package json.chao.com.wanandroid.presenter;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.RxPresenter;
import json.chao.com.wanandroid.contract.CollectContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/27
 */

public class CollectPresenter extends RxPresenter<CollectContract.View> implements CollectContract.Presenter {

    private DataManager mDataManager;

    @Inject
    CollectPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getCollectList(int page) {
        addSubscribe(mDataManager.getCollectList(page)
                    .compose(RxUtils.rxSchedulerHelper())
                    .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                        @Override
                        public void onNext(FeedArticleListResponse feedArticleListResponse) {
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
                        .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                            @Override
                            public void onNext(FeedArticleListResponse feedArticleListResponse) {
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
