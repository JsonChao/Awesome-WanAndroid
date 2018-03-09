package json.chao.com.wanandroid.utils.logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Abstract class that takes care of background threading the file log operation on Android.
 * implementing classes are free to directly perform I/O operations there.
 * @author quchao
 */

public class DiskLogStrategy implements LogStrategy {

    private final Handler handler;

    DiskLogStrategy(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void log(int level, String tag, String message) {
        // do nothing on the calling thread, simply pass the tag/msg to the background thread
        handler.sendMessage(handler.obtainMessage(level, message));
    }

    static class WriteHandler extends Handler {

        /**
         * 保留3天的日志
         */
        private static final long SAVE_DAYS = 1000 * 60 * 60 * 24 * 7;

        /**
         * 日志文件名格式
         */
        private SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy-MM-dd_HH", Locale.ENGLISH);
        private final String folder;
        private String appName = "Logger";

        WriteHandler(Looper looper, String folder, String appName) {
            super(looper);
            this.folder = folder + new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
            this.appName = appName;
            deleteLoggerFile(folder);
        }

        @Override
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;

            FileWriter fileWriter = null;
            File logFile = getLogFile(folder, appName);

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content);

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) { // fail silently  }
                    }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private void writeLog(FileWriter fileWriter, String content) throws IOException {
            fileWriter.append(content);
        }

        private File getLogFile(String folderName, String fileName) {
            File folder = new File(folderName);
            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                folder.mkdirs();
            }
            return new File(folder, String.format("%s_%s.log", fileName, fileFormat.format(new Date())));
        }

        /**
         * 删除SAVE_DAYS天前的日志
         */
        private synchronized void deleteLoggerFile(String path) {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            File[] files = file.listFiles();
            for (File fil : files) {
                // 删除最后修改日期早于三天前的日志
                if (System.currentTimeMillis() - fil.lastModified() > SAVE_DAYS) {
                    deleteDirWhiteFile(fil);
                }
            }
        }

        /**
         * 删除指定文件目录下的所有文件
         *
         * @param dir FileDir
         */
        private synchronized void deleteDirWhiteFile(File dir) {
            if (dir == null || !dir.exists() || !dir.isDirectory()) {
                return;
            }
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    // 删除所有文件
                    file.delete();
                } else if (file.isDirectory()) {
                    // 递规的方式删除文件夹
                    deleteDirWhiteFile(file);
                }
            }
            // 删除目录本身
            dir.delete();
        }
    }
}