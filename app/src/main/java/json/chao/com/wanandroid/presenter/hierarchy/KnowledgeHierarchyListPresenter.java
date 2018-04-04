package json.chao.com.wanandroid.presenter.hierarchy;

import javax.inject.Inject;

import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.event.KnowledgeJumpTopEvent;
import json.chao.com.wanandroid.core.event.ReloadDetailEvent;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/23
 */

public class KnowledgeHierarchyListPresenter extends BasePresenter<KnowledgeHierarchyListContract.View>
        implements KnowledgeHierarchyListContract.Presenter {

    private DataManager mDataManager;

    @Inject
    KnowledgeHierarchyListPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(KnowledgeHierarchyListContract.View view) {
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

        addSubscribe(RxBus.getDefault().toFlowable(KnowledgeJumpTopEvent.class)
                .subscribe(knowledgeJumpTopEvent -> mView.showJumpTheTop()));

        addSubscribe(RxBus.getDefault().toFlowable(ReloadDetailEvent.class)
                .subscribe(reloadEvent -> mView.showReloadDetailEvent()));
    }

    @Override
    public void getKnowledgeHierarchyDetailData(int page, int cid) {
        addSubscribe(mDataManager.getKnowledgeHierarchyDetailData(page, cid)
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<BaseResponse<FeedArticleListData>>(mView) {
                            @Override
                            public void onNext(BaseResponse<FeedArticleListData> feedArticleListResponse) {
                                if (feedArticleListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showKnowledgeHierarchyDetailData(feedArticleListResponse);
                                } else {
                                    mView.showKnowledgeHierarchyDetailDataFail();
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
}
