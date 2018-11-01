package json.chao.com.wanandroid.presenter.wx;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.wx.WxContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.wx.WxAuthor;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;


/**
 * @author quchao
 * @date 2018/10/31
 */
public class WxArticlePresenter extends BasePresenter<WxContract.View> implements WxContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public WxArticlePresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void getWxAuthorListData() {
        addSubscribe(mDataManager.getWxAuthorListData()
                    .compose(RxUtils.rxSchedulerHelper())
                    .compose(RxUtils.handleResult())
                    .subscribeWith(new BaseObserver<List<WxAuthor>>(mView,
                            WanAndroidApp.getInstance().getString(R.string.fail_to_obtain_wx_author)) {
                        @Override
                        public void onNext(List<WxAuthor> wxAuthors) {
                            mView.showWxAuthorListView(wxAuthors);
                        }
                    }));
    }


}
