package json.chao.com.wanandroid.utils;

import android.annotation.SuppressLint;

import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类
 *
 * @author chao.qu
 * @date 2017/9/29
 */

public class LogHelper {

    public synchronized static void v(String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        String toStringBuffer = "[" + traceElement.getFileName() + " | " +
                traceElement.getLineNumber() + " | " + traceElement.getMethodName() + "]" + msg;
        Logger.t("PEGASILOG").i(toStringBuffer);
    }

    public synchronized static void d(String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        String toStringBuffer = "[" + traceElement.getFileName() + " | " +
                traceElement.getLineNumber() + " | " + traceElement.getMethodName() + "] " + msg;
        Logger.t("PEGASILOG").i(toStringBuffer);
    }

    public synchronized static void i(String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        String toStringBuffer = "[" + traceElement.getFileName() + " | " +
                traceElement.getLineNumber() + " | " + traceElement.getMethodName() + "] " + msg;
        Logger.t("PEGASILOG").i(toStringBuffer);
    }

    public synchronized static void w(String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        String toStringBuffer = "[" + traceElement.getFileName() + " | " +
                traceElement.getLineNumber() + " | " + traceElement.getMethodName() + "] " + msg;
        Logger.t("PEGASILOG").w(toStringBuffer);
    }

    public synchronized static void e(String msg) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        String toStringBuffer = "[" + traceElement.getFileName() + " | " +
                traceElement.getLineNumber() + " | " + traceElement.getMethodName() + "] " + msg;
        Logger.t("PEGASILOG").e(toStringBuffer);
    }


    public synchronized static void v(String...msgs) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuilder toStringBuffer = new StringBuilder("[").append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("] ");
        if (msgs != null) {
            toStringBuffer.append("Log.v");
        }
        assert msgs != null;
        for (String msg : msgs) {
            toStringBuffer.append(String.format("===%s", msg));
        }
        Logger.t("PEGASILOG").i(toStringBuffer.toString());
    }

    public synchronized static void d(String...msgs) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuilder toStringBuffer = new StringBuilder("[").append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("] ");
        if (msgs != null) {
            toStringBuffer.append("Log.d");
        }
        assert msgs != null;
        for (String msg : msgs) {
            toStringBuffer.append(String.format("===%s", msg));
        }
        Logger.t("PEGASILOG").i(toStringBuffer.toString());
    }

    public synchronized static void i(String...msgs) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuilder toStringBuffer = new StringBuilder("[").append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("] ");
        if (msgs != null) {
            toStringBuffer.append("Log.i");
        }
        assert msgs != null;
        for (String msg : msgs) {
            toStringBuffer.append(String.format("===%s", msg));
        }
        Logger.t("PEGASILOG").i(toStringBuffer.toString());
    }

    public synchronized static void w(String...msgs) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuilder toStringBuffer = new StringBuilder("[").append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("] ");
        if (msgs != null) {
            toStringBuffer.append("Log.w");
        }
        assert msgs != null;
        for (String msg : msgs) {
            toStringBuffer.append(String.format("===%s", msg));
        }
        Logger.t("PEGASILOG").w(toStringBuffer.toString());
    }

    public synchronized static void e(String...msgs) {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        StringBuilder toStringBuffer = new StringBuilder("[").append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("] ");
        if (msgs != null) {
            toStringBuffer.append("Log.e");
        }
        assert msgs != null;
        for (String msg : msgs) {
            toStringBuffer.append(String.format("===%s", msg));
        }
        Logger.t("PEGASILOG").e(toStringBuffer.toString());
    }

    // 当前文件名
    public static String file() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getFileName();
    }

    // 当前方法名
    public static String func() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    // 当前行号
    public static int line() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    // 当前时间
    @SuppressLint("SimpleDateFormat")
    public static String time() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }
}
