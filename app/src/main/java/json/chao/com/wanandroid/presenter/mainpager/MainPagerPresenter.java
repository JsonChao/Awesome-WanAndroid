package json.chao.com.wanandroid.presenter.mainpager;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.mainpager.MainPagerContract;
import json.chao.com.wanandroid.core.bean.main.banner.BannerResponse;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.main.login.LoginResponse;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2017/12/7
 */

public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private DataManager mDataManager;

    @Inject
    MainPagerPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void loadMainPagerData() {
        String account = mDataManager.getLoginAccount();
        String password = mDataManager.getLoginPassword();
        Observable<LoginResponse> mLoginObservable = mDataManager.getLoginData(account, password);
        Observable<BannerResponse> mBannerObservable = mDataManager.getBannerData();
        Observable<FeedArticleListResponse> mArticleObservable = mDataManager.getFeedArticleList(Constants.FIRST);
        Observable.zip(mLoginObservable, mBannerObservable, mArticleObservable,
                (loginResponse, bannerResponse, feedArticleListResponse) -> {
                    HashMap<String, Object> map = new HashMap<>(3);
                    map.put(Constants.LOGIN_DATA, loginResponse);
                    map.put(Constants.BANNER_DATA, bannerResponse);
                    map.put(Constants.ARTICLE_DATA, feedArticleListResponse);
                    return map;
                }).compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<HashMap<String, Object>>(mView) {
                    @Override
                    public void onNext(HashMap<String, Object> map) {
                        LoginResponse loginResponse = (LoginResponse) map.get(Constants.LOGIN_DATA);
                        if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            mView.showAutoLoginSuccess();
                        }
                        mView.showBannerData((BannerResponse) map.get(Constants.BANNER_DATA));
                        mView.showArticleList((FeedArticleListResponse) map.get(Constants.ARTICLE_DATA));
                    }
                });
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
