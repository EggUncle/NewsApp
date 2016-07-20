package uncle.egg.newsapp.util;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.module.News;

/**
 * Created by egguncle on 16.7.21.
 */
public class FindNews {
    //获取信息的工具类

    private final static int FIND_NEWS_ANDROID = 1;
    private final static int FIND_NEWS_IOS = 2;
    private final static int FIND_NEWS_HTML = 3;
    private final static int FIND_NEWS_GIRL = 4;


    public static List<News> DataNews = new ArrayList<>();

    public static List<News> getNews(int type, int page) {
        String strType = null;
        switch (type) {
            case FIND_NEWS_ANDROID: {
                strType = "Android";
            }
            break;
            case FIND_NEWS_IOS: {
                strType = "IOS";
            }
            break;
            case FIND_NEWS_HTML: {
                strType = "前端";
            }
            break;
            case FIND_NEWS_GIRL: {
                strType = "福利";
            }
            break;
        }

        String volley_url = "http://gank.io/api/search/query/listview/category/" + strType + "/count/10/page/" + page;

        return null;
    }
}
