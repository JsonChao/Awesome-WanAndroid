package json.chao.com.wanandroid.presenter.mainpager;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.app.WanAndroidApp;
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
    public String getLoginAccount() {
        return mDataManager.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return mDataManager.getLoginPassword();
    }

    @Override
    public void loadMainPagerData() {
        Observable<BaseResponse<LoginData>> mLoginObservable = mDataManager.getLoginData(getLoginAccount(), getLoginPassword());
        Observable<BaseResponse<List<BannerData>>> mBannerObservable = mDataManager.getBannerData();
        Observable<BaseResponse<FeedArticleListData>> mArticleObservable = mDataManager.getFeedArticleList(0);
        addSubscribe(Observable.zip(mLoginObservable, mBannerObservable, mArticleObservable, this::createResponseMap)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<HashMap<String, Object>>(mView) {
                    @Override
                    public void onNext(HashMap<String, Object> map) {
                        BaseResponse<LoginData> loginResponse = CommonUtils.cast(map.get(Constants.LOGIN_DATA));
                        if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            loginSuccess(loginResponse);
                        } else {
                            mView.showAutoLoginFail();
                        }
                        BaseResponse<List<BannerData>> bannerResponse = CommonUtils.cast(map.get(Constants.BANNER_DATA));
                        if (bannerResponse != null) {
                            mView.showBannerData(bannerResponse.getData());
                        }
                        BaseResponse<FeedArticleListData> feedArticleListResponse = CommonUtils.cast(map.get(Constants.ARTICLE_DATA));
                        if (feedArticleListResponse != null) {
                            mView.showArticleList(feedArticleListResponse.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showAutoLoginFail();
                    }
                }));
    }

    @Override
    public void autoRefresh(boolean isShowError) {
        isRefresh = true;
        mCurrentPage = 0;
        getBannerData(isShowError);
        getFeedArticleList(isShowError);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        mCurrentPage++;
        loadMoreData();
    }

    @Override
    public void getBannerData(boolean isShowError) {
        addSubscribe(mDataManager.getBannerData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<BannerData>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_banner_data),
                        isShowError) {
                    @Override
                    public void onNext(List<BannerData> bannerDataList) {
                        mView.showBannerData(bannerDataList);
                    }
                }));
    }

    @Override
    public void getFeedArticleList(boolean isShowError) {
        addSubscribe(mDataManager.getFeedArticleList(mCurrentPage)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .filter(feedArticleListResponse -> mView != null)
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_article_list),
                        isShowError) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showArticleList(feedArticleListData, isRefresh);
                    }
                }));
    }

    @Override
    public void loadMoreData() {
        addSubscribe(mDataManager.getFeedArticleList(mCurrentPage)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .filter(feedArticleListResponse -> mView != null)
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_article_list),
                        false) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        mView.showArticleList(feedArticleListData, isRefresh);
                    }
                }));
    }

    @Override
    public void addCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.addCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(true);
                        mView.showCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }

    @Override
    public void cancelCollectArticle(int position, FeedArticleData feedArticleData) {
        addSubscribe(mDataManager.cancelCollectArticle(feedArticleData.getId())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleCollectResult())
                .subscribeWith(new BaseObserver<FeedArticleListData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.cancel_collect_fail)) {
                    @Override
                    public void onNext(FeedArticleListData feedArticleListData) {
                        feedArticleData.setCollect(false);
                        mView.showCancelCollectArticleData(position, feedArticleData, feedArticleListData);
                    }
                }));
    }

    private void loginSuccess(BaseResponse<LoginData> loginResponse) {
        LoginData loginData = loginResponse.getData();
        mDataManager.setLoginAccount(loginData.getUsername());
        mDataManager.setLoginPassword(loginData.getPassword());
        mDataManager.setLoginStatus(true);
        mView.showAutoLoginSuccess();
    }

    @NonNull
    private HashMap<String, Object> createResponseMap(BaseResponse<LoginData> loginResponse,
                                                      BaseResponse<List<BannerData>> bannerResponse,
                                                      BaseResponse<FeedArticleListData> feedArticleListResponse) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put(Constants.LOGIN_DATA, loginResponse);
        map.put(Constants.BANNER_DATA, bannerResponse);
        map.put(Constants.ARTICLE_DATA, feedArticleListResponse);
        return map;
    }


}
