package json.chao.com.wanandroid.core.bean;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class LoginResponse extends BaseResponse {

    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
