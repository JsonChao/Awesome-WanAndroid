package json.chao.com.wanandroid.base.view;


/**
 *  View 基类
 *
 * @author quchao
 * @date 2017/11/27
 */

public interface BaseView {

    /**
     * Show error message
     *
     * @param errorMsg error message
     */
    void showErrorMsg(String errorMsg);

    /**
     * Show error
     */
    void showError();

    /**
     * Show loading
     */
    void showLoading();

    /**
     * Show collect fail
     */
    void showCollectFail();

    /**
     * Show cancel collect fail
     */
    void showCancelCollectFail();

}
