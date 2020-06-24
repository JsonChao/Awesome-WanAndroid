package json.chao.com.wanandroid.performance.net;

import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

public class NetUtils {

    private static volatile String sSubscriberId = null;
    private static volatile int sUId = -1;

    @TargetApi(Build.VERSION_CODES.M)
    public static long getNetStats(@NonNull Context context, long startTime, long endTime) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            long netUsedByWifi = getNetStats(context, startTime, endTime, NetworkCapabilities.TRANSPORT_WIFI);
            long netUsedByCellular = getNetStats(context, startTime, endTime, NetworkCapabilities.TRANSPORT_CELLULAR);
            return netUsedByWifi + netUsedByCellular;
        } else {
            return 0;
        }
    }

    /**
     * Given the start time and end time, then you can get the traffic usage during this time.
     *
     * @param startTime Start of period. Defined in terms of "Unix time", see
     *                  {@link System#currentTimeMillis}.
     * @param endTime   End of period. Defined in terms of "Unix time", see
     *                  {@link System#currentTimeMillis}.
     * @param netType   the netWorkType you want to query
     * @return Number of bytes.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static long getNetStats(@NonNull Context context, long startTime, long endTime, int netType) {
        long netDataReceive = 0;
        long netDataSend = 0;
        String subId = null;
        NetworkStatsManager manager = (NetworkStatsManager) context.getApplicationContext().
                getSystemService(Context.NETWORK_STATS_SERVICE);

        if (manager == null) {
            return 0;
        }
        NetworkStats networkStats = null;
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        try {
            networkStats = manager.querySummary(netType, subId, startTime, endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (networkStats != null && networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket);
            int uid = bucket.getUid();
            if (getAppUid(context) == uid) {
                netDataReceive += bucket.getRxBytes();
                netDataSend += bucket.getTxBytes();
            }
        }
        return (netDataReceive + netDataSend);
    }

    private static int getAppUid(@NonNull Context context) {
        if (sUId == -1) {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
                if (packageInfo != null) {
                    sUId = packageInfo.applicationInfo.uid;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sUId;
    }

}
