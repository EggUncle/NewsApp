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
    private static RequestQueue queues;
//    private static NewsDB newsDB;
   // private static NewsDB todayDB;

//    public static List<News> DataAndroidNews = new ArrayList<>();
//    public static List<News> DataIOSNews = new ArrayList<>();
//    public static List<News> DataHtmlNews = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
           //     createDB();


    }

    public static RequestQueue getHttpQueues() {
        return queues;
    }

//    public void createDB() {
//        newsDB = new NewsDB(MyApplication.this, this.getFilesDir().toString() + "/news_db.db3", 1);
//    //    todayDB = new NewsDB(MyApplication.this, this.getFilesDir().toString() + "/news_today_db.db3", 1);
//    }
//
//    public static NewsDB getNewsDB() {
//        return newsDB;
//    }

//    public static NewsDB getTodayDB() {
//        return todayDB;
//    }


  //  public static void ClearQueue(String tag) {
   //     queues.cancelAll(tag);
    //}

//    public static List<News> getDataAndroidNews() {
//
//        DataAndroidNews = FindNews.getNews(FindNews.FIND_NEWS_ANDROID, 1);
//        return DataAndroidNews;
//    }
//
//    public static List<News> getDataIOSNews() {
//
//        DataIOSNews = FindNews.getNews(FindNews.FIND_NEWS_IOS, 1);
//        return DataIOSNews;
//    }
//
//
//    public static List<News> getDataHtmlNews() {
//
//        DataHtmlNews = FindNews.getNews(FindNews.FIND_NEWS_HTML, 2);
//
//        return DataHtmlNews;
//    }
}
