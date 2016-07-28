package uncle.egg.newsapp.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.News;

/**
 * Created by egguncle on 16.7.28.
 */
public class FindNewsByInternet {
    //获取信息的工具类
    //数据来源文档 http://gank.io/api

    public final static int FIND_NEWS_ANDROID = 0;
    public final static int FIND_NEWS_IOS = 1;
    public final static int FIND_NEWS_HTML = 2;
    public final static int FIND_NEWS_GIRL = 3;

    private static List<News> androidData = new ArrayList<>();
    private static List<News> iOSData = new ArrayList<>();
    private static List<News> todayData= new ArrayList<>();

    public static List<News> getAndroidData(int page) {
        String volley_url = "http://gank.io/api/search/query/listview/category/Android/count/10/page/" + page;
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
                            //解析json数据，存入list中
                            for (int i = 0; i < jsonArray.length(); i++) {
                                News news = null;
                                news = new News();
                                String desc = jsonArray.optJSONObject(i).get("desc").toString();
                                String url = jsonArray.optJSONObject(i).get("url").toString();
                                String publishedAt = jsonArray.optJSONObject(i).get("publishedAt").toString();
                                String type = jsonArray.optJSONObject(i).get("type").toString();
                                String who = jsonArray.optJSONObject(i).get("who").toString();
                                news.setDesc(desc);
                                news.setPublishedAt(publishedAt);
                                news.setType(type);
                                news.setUrl(url);
                                news.setWho(who);
                                androidData.add(news);
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

        request.setTag("newsAndroidGet");
        MyApplication.getHttpQueues().add(request);
        return androidData;
    }

    public static List<News> getiOSData(int page) {
        String volley_url = "http://gank.io/api/search/query/listview/category/IOS/count/10/page/" + page;

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

                            //             Log.v(TAG, jsonArray.length() + "  jsonArray.length()");

                            //解析json数据，存入list中
                            for (int i = 0; i < jsonArray.length(); i++) {
                                News news = null;
                                news = new News();
                                String desc = jsonArray.optJSONObject(i).get("desc").toString();
                                String url = jsonArray.optJSONObject(i).get("url").toString();
                                String publishedAt = jsonArray.optJSONObject(i).get("publishedAt").toString();
                                String type = jsonArray.optJSONObject(i).get("type").toString();
                                String who = jsonArray.optJSONObject(i).get("who").toString();
                                news.setDesc(desc);
                                news.setPublishedAt(publishedAt);
                                news.setType(type);
                                news.setUrl(url);
                                news.setWho(who);


                                iOSData.add(news);
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

        request.setTag("newsIOSGet");
        MyApplication.getHttpQueues().add(request);
        //  Log.v("MY_TAA","FindNews:"+DataNews.size());
        return iOSData;
    }

    public List<News> getData(int type, int page) {
        final List<News> dataNews = new ArrayList<>();
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

                            //             Log.v(TAG, jsonArray.length() + "  jsonArray.length()");

                            //解析json数据，存入list中
                            for (int i = 0; i < jsonArray.length(); i++) {
                                News news = null;
                                news = new News();
                                String desc = jsonArray.optJSONObject(i).get("desc").toString();
                                String url = jsonArray.optJSONObject(i).get("url").toString();
                                String publishedAt = jsonArray.optJSONObject(i).get("publishedAt").toString();
                                String type = jsonArray.optJSONObject(i).get("type").toString();
                                String who = jsonArray.optJSONObject(i).get("who").toString();
                                news.setDesc(desc);
                                news.setPublishedAt(publishedAt);
                                news.setType(type);
                                news.setUrl(url);
                                news.setWho(who);


                                dataNews.add(news);
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
        return dataNews;
    }

    private static String getUrl(int type, int page) {
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

    public static void getTodayTitle(String date, final TextView textView) {
        final String idDate = date;
        String volley_url = "http://gank.io/api/history/content/day/" + date;
        //    String volley_url = "http://gank.io/api/day/2016/07/27";

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
                            String title = jsonArray.optJSONObject(0).getString("title");
                            String todayDate = idDate;
                            textView.setText(title);

                            Log.v("TITLE",title);
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
    }

    public static void setTodayImage(String url, final ImageView imageView) {
        Log.v("MY_TAG2", url + " ");
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                //   bitmap = bitmap;
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setImageResource(R.mipmap.ic_launcher);
                Log.v("MY_TAG2", volleyError + " ");
            }
        });
        imageRequest.setTag("imgGet");
        MyApplication.getHttpQueues().add(imageRequest);
    }

        public static List<News> getTodayNews(String date) {

        final String todayDate = date;

        // getTodayTitle(date);

//        final int idDate  = 20160727;
        String volley_url = "http://gank.io/api/day/" + date;
        //    String volley_url = "http://gank.io/api/day/2016/07/27";
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
                            jsonArray = jsonObject.getJSONObject("results").names();
                            JSONArray jsonArrayIndex;
                            //解析json数据，存入list中
                            for (int i = 0; i < jsonObject.getJSONObject("results").names().length(); i++) {
                                jsonArrayIndex = jsonObject.getJSONObject("results").getJSONArray(jsonArray.get(i).toString());
                                for (int j = 0; j < jsonArrayIndex.length(); j++) {
                                    News news = null;
                                    news = new News();
                                    String desc = jsonArrayIndex.optJSONObject(j).get("desc").toString();
                                    String url = jsonArrayIndex.optJSONObject(j).get("url").toString();
                                    String publishedAt = todayDate;
                                    //jsonArrayIndex.optJSONObject(j).get("publishedAt").toString();
                                    String type = jsonArrayIndex.optJSONObject(j).get("type").toString();
                                    String who = jsonArrayIndex.optJSONObject(j).get("who").toString();

                                    news.setDesc(desc);
                                    news.setPublishedAt(publishedAt);
                                    news.setType(type);
                                    news.setUrl(url);
                                    news.setWho(who);
                                    Log.v("TODAY", desc + " " + url + " " + publishedAt + " " + type + " " + who);
                                    todayData.add(news);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                             Log.v("TODAY","ERROR!!!!!!!!!!");
                    }
                });
        request.setTag("newsGet");
        MyApplication.getHttpQueues().add(request);
        return todayData;
    }
}
