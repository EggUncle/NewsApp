package uncle.egg.newsapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;

/**
 * Created by egguncle on 16.7.22.
 */
public class NewsDB extends SQLiteOpenHelper {
    final String CREATE_NEWS_TABLE =
            "create table news(" +
                    "_id integer primary key autoincrement," +     //0 新闻ID
                    "desc varchar(30)," +                           //1 标题
                    "published_at varchar(20)," +                //2 发布时间
                    "type varchar(10)," +                         //3 类型
                    "url varchar(50)," +                          //4 链接
                    "who varchar(10)" +                            //5 作者
                    ")";

    public NewsDB(Context context, String name, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //获取数据库的数据数量
    public static int getMaxNum() {

        Cursor cursor = MyApplication.getNewsDB().getReadableDatabase().rawQuery("SELECT COUNT(*) FROM news", null);
        //游标移到第一条记录准备获取数据
        //    int count = cursor.getCount();
        cursor.moveToFirst();

        int maxNum = cursor.getInt(0);
        return maxNum;
    }

    //在数据库中取出对应的数据
    //参数 需要的数据的类型，以及需要的数据的条数
    public static List<News> getDBNews(int type, int newsNum) {
        int maxNum = getMaxNum();
        if(newsNum>=maxNum){
            newsNum=maxNum;
        }

        String strType=null;
        switch (type) {
            case FindNews.FIND_NEWS_ANDROID: {
              strType="Android";
            }
            break;
            case FindNews.FIND_NEWS_IOS: {
                strType="iOS";
            }
            break;
            case FindNews.FIND_NEWS_HTML: {
                strType="前端";
            }
            break;
            case FindNews.FIND_NEWS_GIRL: {
                 strType = "福利";
            }
            break;
        }

        List<News> listData = new ArrayList<>();

      //  select * from news limit 5
        Cursor cursor = MyApplication.getNewsDB().getReadableDatabase().rawQuery(
                "select * from news where type = ? limit ?", new String[]{strType,newsNum+""});
        if (cursor==null){
            return listData;
        }



       while(cursor.moveToNext()){
            News news  = new News();
            news.setDesc(cursor.getString(1).toString());
            news.setPublishedAt(cursor.getString(2).toString());
            news.setType(cursor.getString(3).toString());
            news.setUrl(cursor.getString(4).toString());
            news.setWho(cursor.getString(5).toString());
            listData.add(news);

        }

        return listData;
    }
}
