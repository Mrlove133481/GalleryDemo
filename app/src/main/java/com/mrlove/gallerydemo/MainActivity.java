package com.mrlove.gallerydemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Mrlove-MainActivity";
    ImageView imageView;
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;
    String url1 = "https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png";
    String url2 = "https://img.alicdn.com/tfs/TB1_uT8a5ERMeJjSspiXXbZLFXa-143-59.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        //1.创建一个volley队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.设置swipeRefreshLayout监听器，监听下拉刷新，执行相应的下拉操作
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImage();
            }
        });
    }

    //通过glide图片加载库加载图片
    void loadImage(){
        Random random = new Random();
        String url = random.nextBoolean()?url1:url2;
        Glide.with(this)
                //图片地址
                .load(url)
                //图片预设图片
                .placeholder(R.drawable.ic_launcher_background)
                //监听器，当数据成功加载或者失败时，都停止下来刷新转圈。
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        return false;
                    }
                })
                //加载图片控件
                .into(imageView);
    }
}
