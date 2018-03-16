package json.chao.com.wanandroid.core.event;

/**
 * @author quchao
 * @date 2018/2/28
 */

public class LoginEvent {

    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public LoginEvent(boolean isLogin) {

        this.isLogin = isLogin;
    }
}
