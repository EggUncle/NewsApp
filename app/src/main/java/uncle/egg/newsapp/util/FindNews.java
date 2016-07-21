package uncle.egg.newsapp.util;

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

    public final static int FIND_NEWS_ANDROID = 1;
    public final static int FIND_NEWS_IOS = 2;
    public final static int FIND_NEWS_HTML = 3;
    public final static int FIND_NEWS_GIRL = 4;

    public final static String TAG = "MY_TAG";

    public static List<News> DataNews = new ArrayList<>();

    //type 请求的数据内容类型 android|IOS|前端|妹子图
    //page 请求了第几页
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

                            Log.v(TAG,jsonArray.length()+"  jsonArray.length()");

                            //解析json数据，存入list中
                            for (int i = 0; i < jsonArray.length(); i++) {
                                News news = null;
                                news = new News();

                                Log.v(TAG,"TEST"+jsonArray.optJSONObject(i).get("desc").toString());
                                Log.v(TAG,"type:"+jsonArray.optJSONObject(i).get("type").toString());

                                news.setDesc(jsonArray.optJSONObject(i).get("desc").toString());
                                news.setPublicedAt(jsonArray.optJSONObject(i).get("publishedAt").toString());
                                news.setType(jsonArray.optJSONObject(i).get("type").toString());
                                news.setUrl(jsonArray.optJSONObject(i).get("url").toString());
                                news.setWho(jsonArray.optJSONObject(i).get("who").toString());
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

        return DataNews;
    }
}
