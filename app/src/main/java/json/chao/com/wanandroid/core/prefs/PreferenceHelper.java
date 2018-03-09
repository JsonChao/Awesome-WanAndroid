package json.chao.com.wanandroid.core.prefs;

/**
 * @author quchao
 * @date 2017/11/27
 */

public interface PreferenceHelper {

    /**
     * Set login account
     *
     * @param account Account
     */
    void setLoginAccount(String account);

    /**
     * Set login password
     *
     * @param password Password
     */
    void setLoginPassword(String password);

    /**
     * Get login account
     *
     * @return account
     */
    String getLoginAccount();

    /**
     * Get login password
     *
     * @return password
     */
    String getLoginPassword();

    /**
     * Set login status
     *
     * @param isLogin IsLogin
     */
    void setLoginStatus(boolean isLogin);

    /**
     * Get login status
     *
     * @return login status
     */
    boolean getLoginStatus();

    /**
     * Set current page
     *
     * @param position Position
     */
    void setCurrentPage(int position);

    /**
     * Get current page
     *
     * @return current page
     */
    int getCurrentPage();

    /**
     * Set project current page
     *
     * @param position Position
     */
    void setProjectCurrentPage(int position);

    /**
     * Get project current page
     *
     * @return current page
     */
    int getProjectCurrentPage();


}
