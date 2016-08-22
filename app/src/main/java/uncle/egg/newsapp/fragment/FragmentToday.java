package uncle.egg.newsapp.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.ListRecyclerTodayAdapter;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.module.XCRecyclerView;
import uncle.egg.newsapp.util.FindNewsByInternet;

/**
 * Created by egguncle on 16.7.27.
 */
public class FragmentToday extends Fragment {

    private View view;
    private ImageView imgTodayGirl;
    private TextView todayTitle;
    private TextView txtDate;
    private XCRecyclerView todayRecycle;
    private Context context;
    private ProgressBar todayProgressBar;
    private ListRecyclerTodayAdapter adapter;

    private List<News> todayData = new ArrayList<>();

    private String url;

    private String strDate;

    public FragmentToday(Context context) {
        this.context = context;
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -2);//把日期往后或往前一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        strDate = sDateFormat.format(date);
        todayData = FindNewsByInternet.getTodayNews(strDate);
        Log.v("TODAY", strDate);
    }

    public FragmentToday(Context context, String date) {
        this.context = context;
        strDate=date;
        todayData = FindNewsByInternet.getTodayNews(strDate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, null);
//        imgTodayGirl = (ImageView) view.findViewById(R.id.img_today_girl);
//        todayTitle = (TextView) view.findViewById(R.id.today_title);
        todayProgressBar = (ProgressBar) view.findViewById(R.id.today_progress);

        todayRecycle = (XCRecyclerView) view.findViewById(R.id.today_recycle);
        todayRecycle.setLayoutManager(new LinearLayoutManager(context));
        View head_layout = View.inflate(getActivity(), R.layout.head_layout, null);
        imgTodayGirl = (ImageView) head_layout.findViewById(R.id.img_today_girl);
        todayTitle = (TextView) head_layout.findViewById(R.id.today_title);
        txtDate = (TextView) head_layout.findViewById(R.id.txt_date);
        txtDate.setText(strDate);
        todayRecycle.addHeaderView(head_layout);

        adapter = new ListRecyclerTodayAdapter(getActivity(), todayData);
        todayRecycle.setAdapter(adapter);

        MyAyncTask myAyncTask = new MyAyncTask();
        myAyncTask.execute();
        //FindNews.getTodayNews(date);
        // String date = "2016/07/28";

        //   getTodayTitle(strDate);

//
//        Log.v("DataSize", todayData.size() + "");
//        for (News news : todayData) {
//            if ("福利".equals(news.getType())) {
//                url = news.getUrl();
//                Log.v("GIRL_URL", url);
//            }
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        FindNewsByInternet.setTodayImage(url, imgTodayGirl);
//                        FindNewsByInternet.getTodayTitle(strDate, todayTitle);
//                    }
//                });
//            }
//        }).start();


//        ListRecyclerTodayAdapter adapter = new ListRecyclerTodayAdapter(getActivity(), todayData);
//        todayRecycle.setAdapter(adapter);
        return view;
    }

    //使用异步加载来加载列表
    //第一个参数：启动任务时输入的参数类型.
    //第二个参数：后台任务执行中返回进度值的类型.
    //第三个参数：后台任务执行完成后返回结果的类型.
    class MyAyncTask extends AsyncTask<Void, Integer, ArrayList<String>> {

        ArrayList<String> list_name_data;

        public MyAyncTask() {
            super();
        }


        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            //执行后返回值
            super.onPostExecute(strings);
            adapter.notifyDataSetChanged();
            todayRecycle.setVisibility(View.VISIBLE);
            Log.v("TASK", "加载完成");
        }

        @Override
        protected void onPreExecute() {
            //执行前的初始化操作
            super.onPreExecute();
            todayProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //更新时调用的操作
            //  list_name_data = setListData();
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            //后台加载时的操作


            new Thread(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  Log.v("SET_IMG",url);

                            for (News news : todayData) {
                                if ("福利".equals(news.getType())) {
                                    url = news.getUrl();
                                    Log.v("GIRL_URL", url);
                                }
                            }
                            FindNewsByInternet.setTodayImage(url, imgTodayGirl);
                            FindNewsByInternet.getTodayTitle(strDate, todayTitle);
                            todayProgressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }).start();
//            FindNewsByInternet.setTodayImage(url, imgTodayGirl);
//            FindNewsByInternet.getTodayTitle(strDate, todayTitle);


            return null;
        }
    }

}
