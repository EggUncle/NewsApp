package uncle.egg.newsapp;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.DB.NewsDB;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.FindNewsByInternet;

/**
 * Created by egguncle on 16.7.21.
 */
public class MyApplication extends Application {
    private static RequestQueue queues;
//    private static NewsDB newsDB;
   // private static NewsDB todayDB;

    private  static List<News> dataAndroidNews = new ArrayList<>();
    private static List<News> dataIOSNews = new ArrayList<>();
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

    public static List<News> getDataAndroidNews() {


            dataAndroidNews = FindNewsByInternet.getAndroidData(1);


        return dataAndroidNews;
    }

    public static List<News> getDataIOSNews() {

        dataIOSNews =FindNewsByInternet.getiOSData(1);
        return dataIOSNews;
    }
//
//
//    public static List<News> getDataHtmlNews() {
//
//        DataHtmlNews = FindNews.getNews(FindNews.FIND_NEWS_HTML, 2);
//
//        return DataHtmlNews;
//    }
}
