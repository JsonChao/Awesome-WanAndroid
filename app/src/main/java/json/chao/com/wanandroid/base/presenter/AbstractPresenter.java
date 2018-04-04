package json.chao.com.wanandroid.base.presenter;

import json.chao.com.wanandroid.base.view.BaseView;

/**
 * Presenter 基类
 *
 * @author quchao
 * @date 2017/11/27
 */

public interface AbstractPresenter<T extends BaseView> {

    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();

    /**
     * Get night mode state
     *
     * @return if is night mode
     */
    boolean getNightModeState();

}
