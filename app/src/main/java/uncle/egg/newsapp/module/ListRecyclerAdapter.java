package uncle.egg.newsapp.module;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.activity.WebActivity;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.FindNewsByInternet;


public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<News> dataNews;

    public ListRecyclerAdapter(Context context, List<News> dataNews) {
        this.context = context;
        this.dataNews = dataNews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if ("福利".equals(dataNews.get(position).getType()) || "".equals(dataNews.get(position).getDesc())) {
            return;
        }

        holder.txtDesc.setText(dataNews.get(position).getDesc());
        holder.txtPublishedAt.setText(dataNews.get(position).getPublishedAt().substring(0, 10));
        holder.txtWho.setText(dataNews.get(position).getWho());


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
        TextView txtDesc;
        TextView txtPublishedAt;
        TextView txtWho;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
            txtPublishedAt = (TextView) itemView.findViewById(R.id.txt_published);
            txtWho = (TextView) itemView.findViewById(R.id.txt_who);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }



}
