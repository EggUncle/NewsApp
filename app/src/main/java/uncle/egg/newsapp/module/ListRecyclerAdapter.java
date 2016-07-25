package uncle.egg.newsapp.module;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.activity.WebActivity;
import uncle.egg.newsapp.util.FindNews;


public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.MyViewHolder> {

    private Context context;
    public List<News> DataNews;


    public ListRecyclerAdapter(Context context, List<News> dataNews) {
        this.context = context;
        this.DataNews = dataNews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list,
                parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtDesc.setText(DataNews.get(position).getDesc());
        holder.txtPublishedAt.setText(DataNews.get(position).getPublishedAt().substring(0, 10));
        holder.txtWho.setText(DataNews.get(position).getWho());



        holder.txtDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, DataNews.get(position).getUrl(), Toast.LENGTH_SHORT)
                        .show();
                Intent intent  =new Intent(context, WebActivity.class);
                intent.putExtra("desc",DataNews.get(position).getDesc());
                intent.putExtra("url",DataNews.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {

        return DataNews.size();

    }




    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDesc;
        TextView txtPublishedAt;
        TextView txtWho;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
            txtPublishedAt = (TextView) itemView.findViewById(R.id.txt_published);
            txtWho = (TextView) itemView.findViewById(R.id.txt_who);
        }
    }
}
