package json.chao.com.wanandroid.performance.net;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import javax.annotation.Nullable;

import json.chao.com.wanandroid.utils.LogHelper;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * FileName: OkHttpEventListener
 * Date: 2020/5/8 16:28
 * Description: OkHttp 网络请求质量监控
 * @author quchao
 */
public class OkHttpEventListener extends EventListener {

    public static final Factory FACTORY = new Factory() {
        @Override
        public EventListener create(Call call) {
            return new OkHttpEventListener();
        }
    };

    OkHttpEvent okHttpEvent;
    public OkHttpEventListener() {
        super();
        okHttpEvent = new OkHttpEvent();
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);
        LogHelper.i("okHttp Call Start");
        okHttpEvent.callStartTime = System.currentTimeMillis();
    }

    /**
     * DNS 解析开始
     *
     * @param call
     * @param domainName
     */
    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        okHttpEvent.dnsStartTime = System.currentTimeMillis();
    }

    /**
     * DNS 解析结束
     *
     * @param call
     * @param domainName
     * @param inetAddressList
     */
    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        okHttpEvent.dnsEndTime = System.currentTimeMillis();
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        okHttpEvent.connectStartTime = System.currentTimeMillis();
    }

    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
        okHttpEvent.secureConnectStart = System.currentTimeMillis();
    }

    @Override
    public void secureConnectEnd(Call call, @Nullable Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        okHttpEvent.secureConnectEnd = System.currentTimeMillis();
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        okHttpEvent.connectEndTime = System.currentTimeMillis();
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol, IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        super.connectionAcquired(call, connection);
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
    }

    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        // 记录响应体的大小
        okHttpEvent.responseBodySize = byteCount;
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        okHttpEvent.callEndTime = System.currentTimeMillis();
        // 记录 API 请求成功
        okHttpEvent.apiSuccess = true;
        LogHelper.i(okHttpEvent.toString());
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        LogHelper.i("callFailed ");
        super.callFailed(call, ioe);
        // 记录 API 请求失败及原因
        okHttpEvent.apiSuccess = false;
        okHttpEvent.errorReason = Log.getStackTraceString(ioe);
        LogHelper.i("reason " + okHttpEvent.errorReason);
        LogHelper.i(okHttpEvent.toString());
    }
}
