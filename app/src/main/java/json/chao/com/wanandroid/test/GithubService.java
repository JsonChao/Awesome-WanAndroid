package json.chao.com.wanandroid.test;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author quchao
 * @date 2018/6/7
 */
public class GithubService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static GithubApi createGithubService() {
        return retrofit.create(GithubApi.class);
    }

    private static OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(new MockInterceptor("json数据"))
                .build();
    }
}