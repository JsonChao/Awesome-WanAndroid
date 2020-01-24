package json.chao.com.wanandroid.performance.handler;

import android.os.Message;

import java.util.concurrent.ConcurrentHashMap;

public class GetDetailHandlerHelper {

    private static ConcurrentHashMap<Message, String> sMsgDetail = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Message, String> getMsgDetail() {
        return sMsgDetail;
    }

}
