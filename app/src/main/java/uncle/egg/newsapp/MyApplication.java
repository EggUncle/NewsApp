package uncle.egg.newsapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.DB.NewsDB;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;

/**
 * Created by egguncle on 16.7.21.
 */
public class MyApplication extends Application {
    public static RequestQueue queues;
    public static NewsDB newsDB;

    public static List<News> DataAndroidNews = new ArrayList<>();
    public static List<News> DataIOSNews = new ArrayList<>();
    public static List<News> DataHtmlNews = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
        newsDB = new NewsDB(this, this.getFilesDir().toString() + "/news_db.db3", 1);

    }

    public static RequestQueue getHttpQueues() {
        return queues;
    }

    public static NewsDB getNewsDB() {
        return newsDB;
    }


    public static void ClearQueue(String tag) {
        queues.cancelAll(tag);
    }

    public static List<News> getDataAndroidNews() {

        DataAndroidNews = FindNews.getNews(FindNews.FIND_NEWS_ANDROID, 1);
        return DataAndroidNews;
    }

    public static List<News> getDataIOSNews() {

        DataIOSNews = FindNews.getNews(FindNews.FIND_NEWS_IOS, 1);
        return DataIOSNews;
    }


    public static List<News> getDataHtmlNews() {

        DataHtmlNews = FindNews.getNews(FindNews.FIND_NEWS_HTML, 2);

        return DataHtmlNews;
    }
}
