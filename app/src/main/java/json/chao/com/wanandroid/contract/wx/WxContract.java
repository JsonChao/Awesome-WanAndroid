package json.chao.com.wanandroid.contract.wx;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.AbstractView;
import json.chao.com.wanandroid.core.bean.wx.WxAuthor;


/**
 * @author quchao
 * @date 2018/10/31
 */
public interface WxContract {

    interface View extends AbstractView {

        /**
         * 显示公众号作者列表
         *
         * @param wxAuthors 公众号作者列表
         */
        void showWxAuthorListView(List<WxAuthor> wxAuthors);


    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 获取公众号作者列表
         */
        void getWxAuthorListData();

    }

}
