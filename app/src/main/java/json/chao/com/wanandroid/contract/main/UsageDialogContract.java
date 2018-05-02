package json.chao.com.wanandroid.contract.main;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSiteData;


/**
 * @author quchao
 * @date 2018/4/2
 */

public interface UsageDialogContract {

    interface View extends BaseView {

        /**
         * Show useful sites
         *
         * @param usefulSiteDataList List<UsefulSiteData>
         */
        void showUsefulSites(List<UsefulSiteData> usefulSiteDataList);
    }

    interface Presenter extends AbstractPresenter<UsageDialogContract.View> {

        /**
         * 常用网站
         */
        void getUsefulSites();
    }

}
