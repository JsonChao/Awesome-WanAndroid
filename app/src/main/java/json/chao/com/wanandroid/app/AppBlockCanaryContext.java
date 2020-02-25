package json.chao.com.wanandroid.app;

import android.content.Context;

import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AppBlockCanaryContext extends BlockCanaryContext {
        // 实现各种上下文，包括应用标识符，用户uid，网络类型，卡顿判断阙值，Log保存位置等等

        /**
        * 提供应用的标识符
        *
        * @return 标识符能够在安装的时候被指定，建议为 version + flavor.
        */
        @Override
        public String provideQualifier() {
            return "unknown";
        }

        /**
        * 提供用户uid，以便在上报时能够将对应的
        * 用户信息上报至服务器
        *
        * @return user id
        */
        @Override
        public String provideUid() {
            return "uid";
        }

        /**
        * 提供当前的网络类型
        *
        * @return {@link String} like 2G, 3G, 4G, wifi, etc.
        */
        @Override
        public String provideNetworkType() {
            return "unknown";
        }

        /**
        * 配置监控的时间区间，超过这个时间区间    ，BlockCanary将会停止, use
        * with {@code BlockCanary}'s isMonitorDurationEnd
        *
        * @return monitor last duration (in hour)
        */
        @Override
        public int provideMonitorDuration() {
            return -1;
        }

        /**
        * 指定判定为卡顿的阈值threshold (in millis),
        * 你可以根据不同设备的性能去指定不同的阈值
        *
        * @return threshold in mills
        */
        @Override
        public int provideBlockThreshold() {
            return 500;
        }

        /**
        * 设置线程堆栈dump的间隔, 当阻塞发生的时候使用, BlockCanary 将会根据
        * 当前的循环周期在主线程去dump堆栈信息
        * <p>
        * 由于依赖于Looper的实现机制, 真实的dump周期
        * 将会比设定的dump间隔要长(尤其是当CPU很繁忙的时候).
        * </p>
        *
        * @return dump interval (in millis)
        */
        @Override
        public int provideDumpInterval() {
            return provideBlockThreshold();
        }

        /**
        * 保存log的路径, 比如 "/blockcanary/", 如果权限允许的话，
        * 会保存在本地sd卡中
        *
        * @return path of log files
        */
        @Override
        public String providePath() {
            return "/blockcanary/";
        }

        /**
        * 是否需要通知去通知用户发生阻塞
        *
        * @return true if need, else if not need.
        */
        @Override
        public boolean displayNotification() {
            return true;
        }

        /**
        * 用于将多个文件压缩为一个.zip文件
        *
        * @param src  files before compress
        * @param dest files compressed
        * @return true if compression is successful
        */
        @Override
        public boolean zip(File[] src, File dest) {
            return false;
        }

        /**
        * 用于将已经被压缩好的.zip log文件上传至
        * APM后台
        *
        * @param zippedFile zipped file
        */
        @Override
        public void upload(File zippedFile) {
            throw new UnsupportedOperationException();
        }

        /**
        * 用于设定包名, 默认使用进程名，
        *
        * @return null if simply concern only package with process name.
        */
        @Override
        public List<String> concernPackages() {
            return null;
        }

        /**
        * 使用 @{code concernPackages}方法指定过滤的堆栈信息
        *
        * @return true if filter, false it not.
        */
        @Override
        public boolean filterNonConcernStack() {
            return false;
        }

        /**
        * 指定一个白名单, 在白名单的条目将不会出现在展示阻塞信息的UI中
        *
        * @return return null if you don't need white-list filter.
        */
        @Override
        public List<String> provideWhiteList() {
            LinkedList<String> whiteList = new LinkedList<>();
            whiteList.add("org.chromium");
            return whiteList;
        }

        /**
        * 使用白名单的时候，是否去删除堆栈在白名单中的文件
        *
        * @return true if delete, false it not.
        */
        @Override
        public boolean deleteFilesInWhiteList() {
            return true;
        }

        /**
        * 阻塞拦截器, 我们可以指定发生阻塞时应该做的工作
        */
        @Override
        public void onBlock(Context context, BlockInfo blockInfo) {

        }

}