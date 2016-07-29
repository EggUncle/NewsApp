package uncle.egg.newsapp.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import uncle.egg.newsapp.module.XCRecyclerView;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.FindNewsByInternet;

/**
 * Created by egguncle on 16.7.27.
 */
public class FragmentToday extends Fragment {

    private View view;
    private ImageView imgTodayGirl;
    private TextView todayTitle;
    private XCRecyclerView todayRecycle;
    private Context context;

    private List<News> todayData = new ArrayList<>();

    private String url;

    private String strDate;

    public FragmentToday(Context context){
        this.context = context;
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后或往前一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        strDate = sDateFormat.format(date);
        Log.v("TODAY", strDate);
        todayData = FindNewsByInternet.getTodayNews(strDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, null);
//        imgTodayGirl = (ImageView) view.findViewById(R.id.img_today_girl);
//        todayTitle = (TextView) view.findViewById(R.id.today_title);


        todayRecycle = (XCRecyclerView) view.findViewById(R.id.today_recycle);
        todayRecycle.setLayoutManager(new LinearLayoutManager(context));
        View head_layout = View.inflate(getActivity(),R.layout.head_layout,null);
        imgTodayGirl = (ImageView) head_layout.findViewById(R.id.img_today_girl);
        todayTitle = (TextView) head_layout.findViewById(R.id.today_title);
        todayRecycle.addHeaderView(head_layout);


        //FindNews.getTodayNews(date);
        // String date = "2016/07/28";

     //   getTodayTitle(strDate);


        Log.v("DataSize",todayData.size()+"");
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




}
