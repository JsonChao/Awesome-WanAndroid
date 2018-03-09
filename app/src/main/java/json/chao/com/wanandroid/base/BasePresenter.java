package json.chao.com.wanandroid.base;

/**
 * Presenter 基类
 *
 * @author quchao
 * @date 2017/11/27
 */

public interface BasePresenter<T extends BaseView> {

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

}
