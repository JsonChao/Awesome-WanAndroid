package json.chao.com.wanandroid.base.view;


/**
 *  View 基类
 *
 * @author quchao
 * @date 2017/11/27
 */

public interface BaseView {

    /**
     * Use night mode
     *
     * @param isNightMode if is night mode
     */
    void useNightMode(boolean isNightMode);

    /**
     * Show error message
     *
     * @param errorMsg error message
     */
    void showErrorMsg(String errorMsg);

    /**
     * showNormal
     */
    void showNormal();

    /**
     * Show error
     */
    void showError();

    /**
     * Show loading
     */
    void showLoading();

    /**
     * Reload
     */
    void reload();

    /**
     * Show login view
     */
    void showLoginView();

    /**
     * Show logout view
     */
    void showLogoutView();

    /**
     * Show collect fail
     */
    void showCollectFail();

    /**
     * Show cancel collect fail
     */
    void showCancelCollectFail();

    /**
     * Show collect success
     */
    void showCollectSuccess();

    /**
     * Show cancel collect success
     */
    void showCancelCollectSuccess();

}
