package uncle.egg.newsapp.module;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.R;

import uncle.egg.newsapp.activity.WebActivity;

/**
 * Created by egguncle on 16.7.24.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.MyViewHolder> {

    private Context context;
    public List<News> DataNews;
    public Bitmap mBitmap;


    public ImageRecyclerAdapter(Context context, List<News> dataNews) {
        this.context = context;
        this.DataNews = dataNews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String url = DataNews.get(position).getUrl();

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap bitmap) {
                holder.imgGirl.setImageBitmap(bitmap);
                mBitmap = bitmap;
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                holder.imgGirl.setImageResource(R.mipmap.ic_launcher);
            }
        });
        imageRequest.setTag("imgGet");
        MyApplication.getHttpQueues().add(imageRequest);


        holder.imgGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {

        return DataNews.size();

    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

    //    private ImageView imgStar;
        private ImageView imgGirl;


        public MyViewHolder(View itemView) {
            super(itemView);
    //        imgStar = (ImageView) itemView.findViewById(R.id.img_star);
            imgGirl = (ImageView) itemView.findViewById(R.id.img_girl);
        }
    }
}
