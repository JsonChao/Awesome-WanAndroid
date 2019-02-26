package json.chao.com.wanandroid.test;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author quchao
 * @date 2018/6/7
 */
public class MockInterceptor implements Interceptor {

    private String responseString;

    public MockInterceptor(String responseString) {
        this.responseString = responseString;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();

        return response;
    }
}
