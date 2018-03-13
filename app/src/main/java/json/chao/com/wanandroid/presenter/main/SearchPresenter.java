package json.chao.com.wanandroid.presenter.main;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import json.chao.com.wanandroid.app.GeeksApp;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.SearchContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchDataResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSitesResponse;
import json.chao.com.wanandroid.core.dao.DaoSession;
import json.chao.com.wanandroid.core.dao.HistoryData;
import json.chao.com.wanandroid.core.dao.HistoryDataDao;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/12
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SearchPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(SearchContract.View view) {
        super.attachView(view);
    }

    @Override
    public void addHistoryData(String data) {
        DaoSession daoSession = GeeksApp.getInstance().getDaoSession();
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();
        addSubscribe(Observable.create((ObservableOnSubscribe<List<HistoryData>>) e -> {
            List<HistoryData> historyDataList = historyDataDao.loadAll();
            HistoryData historyData = new HistoryData();
            historyData.setDate(System.currentTimeMillis());
            historyData.setData(data);
            //重复搜索时进行历史记录前移
            Iterator<HistoryData> iterator = historyDataList.iterator();
            //不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
            while (iterator.hasNext()) {
                HistoryData historyData1 = iterator.next();
                if (historyData1.getData().equals(data)) {
                    historyDataList.remove(historyData1);
                    historyDataList.add(historyData);
                    historyDataDao.deleteAll();
                    historyDataDao.insertInTx(historyDataList);
                    return;
                }
            }
            if (historyDataList.size() < 10) {
                historyDataDao.insert(historyData);
            } else {
                historyDataList.remove(0);
                historyDataList.add(historyData);
                historyDataDao.deleteAll();
                historyDataDao.insertInTx(historyDataList);
            }
            e.onNext(historyDataList);
        }).compose(RxUtils.rxSchedulerHelper()).subscribe(historyDataList -> {}));
    }

    @Override
    public void getSearchList(int page, String k) {
        addSubscribe(mDataManager.getSearchList(page, k)
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<FeedArticleListResponse>(mView) {
                            @Override
                            public void onNext(FeedArticleListResponse feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showSearchList(feedArticleListResponse);
                                } else {
                                    mView.showSearchListFail();
                                }
                            }
                        }));
    }

    @Override
    public void getTopSearchData() {
        addSubscribe(mDataManager.getTopSearchData()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<TopSearchDataResponse>(mView) {
                            @Override
                            public void onNext(TopSearchDataResponse topSearchDataResponse) {
                                if (topSearchDataResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showTopSearchData(topSearchDataResponse);
                                } else {
                                    mView.showTopSearchDataFail();
                                }
                            }
                        }));
    }

    @Override
    public void getUsefulSites() {
        addSubscribe(mDataManager.getUsefulSites()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<UsefulSitesResponse>(mView) {
                            @Override
                            public void onNext(UsefulSitesResponse usefulSitesResponse) {
                                if (usefulSitesResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showUsefulSites(usefulSitesResponse);
                                } else {
                                    mView.showUsefulSitesDataFail();
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
                                    mView.showUsefulSitesDataFail();
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
}
