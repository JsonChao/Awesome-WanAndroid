package json.chao.com.wanandroid.presenter.mainpager;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.mainpager.MainPagerContract;
import json.chao.com.wanandroid.core.bean.main.banner.BannerData;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2017/12/7
 */

public class MainPagerPresenter extends BasePresenter<MainPagerContract.View> implements MainPagerContract.Presenter {

    private DataManager mDataManager;
    private boolean isRefresh = true;
    private int mCurrentPage;

    @Inject
    MainPagerPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(MainPagerContract.View view) {
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

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
                        .filter(LoginEvent::isLogin)
                        .subscribe(loginEvent -> mView.showLoginView()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
                .filter(loginEvent -> !loginEvent.isLogin())
                .subscribe(loginEvent -> mView.showLogoutView()));
    }

    @Override
    public void loadMainPagerData() {
        String account = mDataManager.getLoginAccount();
        String password = mDataManager.getLoginPassword();
        Observable<BaseResponse<LoginData>> mLoginObservable = mDataManager.getLoginData(account, password);
        Observable<BaseResponse<List<BannerData>>> mBannerObservable = mDataManager.getBannerData();
        Observable<BaseResponse<FeedArticleListData>> mArticleObservable = mDataManager.getFeedArticleList(0);
        addSubscribe(Observable.zip(mLoginObservable, mBannerObservable, mArticleObservable,
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
                        BaseResponse<LoginData> loginResponse = CommonUtils.cast(map.get(Constants.LOGIN_DATA));
                        if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            LoginData loginData = loginResponse.getData();
                            mDataManager.setLoginAccount(loginData.getUsername());
                            mDataManager.setLoginPassword(loginData.getPassword());
                            mDataManager.setLoginStatus(true);
                            mView.showAutoLoginSuccess();
                        } else {
                            mView.showAutoLoginFail();
                        }
                        mView.showBannerData(CommonUtils.cast(map.get(Constants.BANNER_DATA)));
                        mView.showArticleList(CommonUtils.cast(map.get(Constants.ARTICLE_DATA)), isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showAutoLoginFail();
                    }
                }));
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        mCurrentPage = 0;
        getBannerData();
        getFeedArticleList();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        mCurrentPage++;
        getFeedArticleList();
    }

    @Override
    public void getFeedArticleList() {
        addSubscribe(mDataManager.getFeedArticleList(mCurrentPage)
                .compose(RxUtils.rxSchedulerHelper())
                .filter(feedArticleListResponse -> mView != null)
                .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                    @Override
                    public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                        if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            mView.showArticleList(feedArticleListResponse, isRefresh);
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
                        .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                            @Override
                            public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
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

    @Override
    public void getBannerData() {
        addSubscribe(mDataManager.getBannerData()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<BaseResponse<List<BannerData>>>(mView) {
                            @Override
                            public void onNext(BaseResponse<List<BannerData>> bannerResponse) {
                                if (bannerResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showBannerData(bannerResponse);
                                } else {
                                    mView.showBannerDataFail();
                                }
                            }
                        }));
    }


}
