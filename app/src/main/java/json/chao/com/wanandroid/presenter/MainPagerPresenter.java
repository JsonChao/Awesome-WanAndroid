package json.chao.com.wanandroid.presenter;

import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.RxPresenter;
import json.chao.com.wanandroid.contract.MainPagerContract;
import json.chao.com.wanandroid.core.bean.BannerResponse;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2017/12/7
 */

public class MainPagerPresenter extends RxPresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private DataManager mDataManager;

    @Inject
    MainPagerPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getFeedArticleList(int page) {
        addSubscribe(mDataManager.getFeedArticleList(page)
                .compose(RxUtils.rxSchedulerHelper())
                .filter(feedArticleListResponse -> mView != null)
                .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                    @Override
                    public void onNext(FeedArticleListResponse feedArticleListResponse) {
                        if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            mView.showArticleList(feedArticleListResponse);
                        } else {
                            mView.showArticleListFail();
                        }
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                            @Override
                            public void onNext(FeedArticleListResponse feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    feedArticleData.setCollect(true);
                                    mView.showCollectArticleData(position, feedArticleData, feedArticleListResponse);
                                } else {
                                    mView.showCollectFail();
                                }
                            }
                        }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                            @Override
                            public void onNext(FeedArticleListResponse feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    feedArticleData.setCollect(false);
                                    mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListResponse);
                                } else {
                                    mView.showCancelCollectFail();
                                }
                            }
                        }));
    }

    @Override
    public void getBannerData() {
        addSubscribe(mDataManager.getBannerData()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<BannerResponse>(mView) {
                            @Override
                            public void onNext(BannerResponse bannerResponse) {
                                if (bannerResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showBannerData(bannerResponse);
                                } else {
                                    mView.showBannerDataFail();
                                }
                            }
                        }));
    }


}
