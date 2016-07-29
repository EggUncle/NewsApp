package uncle.egg.newsapp.module;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.activity.WebActivity;

/**
 * Created by egguncle on 16.7.29.
 */
public class ListHistoryAdapter  extends RecyclerView.Adapter<ListHistoryAdapter.MyViewHolder> {

    private Context context;
    private List<News> dataNews;

    public ListHistoryAdapter(Context context, List<News> dataNews) {
        this.context = context;
        this.dataNews = dataNews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.head_layout,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if ("福利".equals(dataNews.get(position).getType()) || "".equals(dataNews.get(position).getDesc())) {
            return;
        }

        holder.todayTitle.setText(dataNews.get(position).getDesc());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dataNews.get(position).getUrl(), Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("desc", dataNews.get(position).getDesc());
                intent.putExtra("url", dataNews.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataNews == null ? 0 : dataNews.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
         CardView cardView;
         ImageView imgTodayGirl;
         TextView todayTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgTodayGirl = (ImageView) itemView.findViewById(R.id.img_today_girl);
            todayTitle = (TextView) itemView.findViewById(R.id.today_title);
            cardView = (CardView) itemView.findViewById(R.id.img_card_view);
        }
    }
}
