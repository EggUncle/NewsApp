package uncle.egg.newsapp.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import uncle.egg.newsapp.DB.NewsDB;
import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.R;

import uncle.egg.newsapp.module.ListRecyclerAdapter;
import uncle.egg.newsapp.module.ListRecyclerTodayAdapter;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.FindNewsByInternet;

/**
 * Created by egguncle on 16.7.27.
 */
public class FragmentToday extends Fragment {

    private View view;
    private ImageView imgTodayGirl;
    private TextView todayTitle;
    private RecyclerView todayRecycle;

    private List<News> todayData = new ArrayList<>();

    private String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, null);
        imgTodayGirl = (ImageView) view.findViewById(R.id.img_today_girl);
        todayTitle = (TextView) view.findViewById(R.id.today_title);
        todayRecycle = (RecyclerView) view.findViewById(R.id.today_recycle);



        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后或往前一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final String strDate = sDateFormat.format(date);
        Log.v("TODAY", strDate);
        //FindNews.getTodayNews(date);
        // String date = "2016/07/28";

     //   getTodayTitle(strDate);

        todayData = FindNewsByInternet.getTodayNews(strDate);
        for (News news : todayData) {
            if ("福利".equals(news.getType())) {
                url = news.getUrl();
                Log.v("GIRL_URL",url);
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FindNewsByInternet.setTodayImage(url,imgTodayGirl);
                        FindNewsByInternet.getTodayTitle(strDate,todayTitle);
                    }
                });
            }
        }).start();



        ListRecyclerTodayAdapter adapter = new ListRecyclerTodayAdapter(getActivity(), todayData);
        todayRecycle.setAdapter(adapter);
        return view;
    }


    public void setImgTodayGirl(String url) {

        //  List<News> listGirl = FindNews.getGirlList();
        Log.v("MY_TAG2", url + " ");
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap bitmap) {
                imgTodayGirl.setImageBitmap(bitmap);
                //   bitmap = bitmap;
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imgTodayGirl.setImageResource(R.mipmap.ic_launcher);
                Log.v("MY_TAG2", volleyError + " ");
            }
        });
        imageRequest.setTag("imgGet");
        MyApplication.getHttpQueues().add(imageRequest);
    }

    public void getTodayTitle(String date) {
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

                            todayTitle.setText(title);
                            Log.v("TITLE", title);
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

}
