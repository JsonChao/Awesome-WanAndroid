package json.chao.com.wanandroid.contract.main;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchData;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSiteData;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.dao.HistoryData;

/**
 * @author quchao
 * @date 2018/2/12
 */

public interface SearchContract {

    interface View extends BaseView {

        /**
         * Show history data
         *
         * @param historyDataList List<HistoryData>
         */
        void showHistoryData(List<HistoryData> historyDataList);

        /**
         * Show top search data
         *
         * @param topSearchDataResponse BaseResponse<List<TopSearchData>>
         */
        void showTopSearchData(BaseResponse<List<TopSearchData>> topSearchDataResponse);

        /**
         * Show top search data fail
         */
        void showTopSearchDataFail();

        /**
         * Judge to the search list activity
         */
        void judgeToTheSearchListActivity();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Add history data
         *
         * @param data history data
         */
        void addHistoryData(String data);

        /**
         * 热搜
         */
        void getTopSearchData();

        /**
         * Clear history data
         */
        void clearHistoryData();
    }

}
