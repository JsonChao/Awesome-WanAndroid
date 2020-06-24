package json.chao.com.wanandroid.performance.net;

import android.text.TextUtils;

public class OkHttpEvent {

    public long callStartTime;
    public long callEndTime;
    public long dnsStartTime;
    public long dnsEndTime;
    public long connectStartTime;
    public long connectEndTime;
    public long secureConnectStart;
    public long secureConnectEnd;
    public long responseBodySize;
    public boolean apiSuccess;
    public String errorReason;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NetData: [").append("\n");
        sb.append("callTime: ").append(callEndTime - callStartTime).append("\n");
        sb.append("dnsParseTime: ").append(dnsEndTime - dnsStartTime).append("\n");
        sb.append("connectTime: ").append(connectEndTime - callStartTime).append("\n");
        sb.append("secureConnectTime: ").append(secureConnectEnd - secureConnectStart).append("\n");
        sb.append("responseBodySize: ").append(responseBodySize).append("\n");
        sb.append("apiSuccess: ").append(apiSuccess).append("\n");
        if (!TextUtils.isEmpty(errorReason)) {
            sb.append("errorReason: ").append(errorReason).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
