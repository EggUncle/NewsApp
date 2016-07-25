package uncle.egg.newsapp.util;

import android.database.Cursor;
import android.text.Html;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.module.News;

/**
 * Created by egguncle on 16.7.21.
 */
public class FindNews {
    //获取信息的工具类
    //数据来源文档 http://gank.io/api

    public final static int FIND_NEWS_ANDROID = 0;
    public final static int FIND_NEWS_IOS = 1;
    public final static int FIND_NEWS_HTML = 2;
    public final static int FIND_NEWS_GIRL = 3;

    public final static String TAG = "MY_TAG";

    public static List<News> DataAndroidNews = new ArrayList<>();
    public static List<News> DataIOSNews = new ArrayList<>();
    public static List<News> DataHtmlNews = new ArrayList<>();

    public static List<News> DataNews = new ArrayList<>();

    public static String getUrl(int type, int page) {
        String strType = null;
        switch (type) {
            case FIND_NEWS_ANDROID: {
                strType = "Android";
            }
            break;
            case FIND_NEWS_IOS: {
                strType = "iOS";
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

        return volley_url;
    }

    //type 请求的数据内容类型 android|IOS|前端|妹子图
    //page 请求了第几页
    public static List<News> getNews(int type, int page) {

        String volley_url = getUrl(type, page);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, volley_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray;
                        try {
                            //若解析错误，则返回空
//                            Boolean errorKey = jsonObject.getBoolean("error");
//                            if(errorKey){
//                                return;
//                            }
                            jsonArray = jsonObject.getJSONArray("results");

                            Log.v(TAG, jsonArray.length() + "  jsonArray.length()");

                            //解析json数据，存入list中
                            for (int i = 0; i < jsonArray.length(); i++) {
                                News news = null;
                                news = new News();

                                Log.v(TAG, "TEST:" + jsonArray.optJSONObject(i).get("desc").toString());
                                Log.v(TAG, "type:" + jsonArray.optJSONObject(i).get("type").toString());





                                String desc = jsonArray.optJSONObject(i).get("desc").toString();
                                String url = jsonArray.optJSONObject(i).get("url").toString();
                                //若数据库中有这个干货的链接，则不添加进去,开始下一次循环 （依靠链接来判断是否重复
                                Cursor cursor = MyApplication.getNewsDB().getReadableDatabase().rawQuery("select * from news where url = '" + url+"'", null);
                                //依靠标题来判断是否重复
                                // Cursor cursor = MyApplication.getNewsDB().getReadableDatabase().rawQuery("select desc from news where desc = " + desc, null);
                                if(cursor.getCount()!=0){
                                    continue;
                                }
                                Cursor cursorNews = MyApplication.getNewsDB().getReadableDatabase().rawQuery("select * from news ", null);

                                int max_id = cursorNews.getCount()+1;



                                String publishedAt = jsonArray.optJSONObject(i).get("publishedAt").toString();
                                String type = jsonArray.optJSONObject(i).get("type").toString();

                                String who = jsonArray.optJSONObject(i).get("who").toString();

                                MyApplication.getNewsDB().getReadableDatabase().execSQL(
                                        "insert  into news values(?,?,?,?,?,?)"
                                        , new String[]{max_id+"", desc, publishedAt,type, url, who});

                                news.setDesc(desc);
                                news.setPublishedAt(publishedAt);
                                news.setType(type);
                                news.setUrl(url);
                                news.setWho(who);




                                DataNews.add(news);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                    }
                });

        request.setTag("newsGet");
        MyApplication.getHttpQueues().add(request);
        //  Log.v("MY_TAA","FindNews:"+DataNews.size());
         return DataNews;
    }



}
