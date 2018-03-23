package json.chao.com.wanandroid.app;


import java.io.File;

import json.chao.com.wanandroid.R;

/**
 * @author quchao
 * @date 2017/11/27
 */

public class Constants {

    static final String  BUGLY_ID = "a29fb52485";

    /**
     * Path
     */
    private static final String PATH_DATA = GeeksApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    /**
     * Tag fragment classify
     */
    public static final int FIRST = 0;

    public static final int SECOND = 1;

    public static final int THIRD = 2;

    public static final int FOURTH = 3;

    /**
     * Bottom Navigation tab classify
     */
    public static final int TAB_ONE = 0;

    /**
     * Intent params
     */
    public static final String ARG_PARAM1 = "param1";

    public static final String ARG_PARAM2 = "param2";


    /**
     * Main Pager
     */
    public static final String SEARCH_TEXT = "search_text";

    public static final String MENU_BUILDER = "MenuBuilder";

    public static final String LOGIN_DATA = "login_data";

    public static final String BANNER_DATA = "banner_data";

    public static final String ARTICLE_DATA = "article_data";

    /**
     * Refresh theme color
     */
    public static final int BLUE_THEME = R.color.colorPrimary;

    public static final int GREEN_THEME = android.R.color.holo_green_light;

    public static final int RED_THEME = android.R.color.holo_red_light;

    public static final int ORANGE_THEME = android.R.color.holo_orange_light;

    /**
     * Avoid double click time area
     */
    public static final long CLICK_TIME_AREA = 1000;


    public static final String ARTICLE_LINK = "article_link";

    public static final String ARTICLE_TITLE = "article_title";

    public static final String ARTICLE_ID = "article_id";

    public static final String IS_COLLECT = "is_collect";

    public static final String IS_COMMON_SITE = "is_common_site";

    public static final String IS_COLLECT_PAGE = "is_collect_page";

    public static final String CHAPTER_ID = "chapter_id";

    public static final String IS_SINGLE_CHAPTER = "is_single_chapter";

    public static final String CHAPTER_NAME = "is_chapter_name";

    public static final String SUPER_CHAPTER_NAME = "super_chapter_name";


    /**
     * Shared Preference key
     */
    public static final String ACCOUNT = "account";

    public static final String PASSWORD = "password";

    public static final String LOGIN_STATUS = "login_status";


    static final String DB_NAME = "aws_wan_android.db";

    public static final String CURRENT_PAGE = "current_page";

    public static final String PROJECT_CURRENT_PAGE = "project_current_page";

    /**
     * number
     */
    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int TWO = 2;

    public static final int THREE = 3;

    public static final int FOUR = 4;

    public static final int PROGRESS_OK = 100;


}
