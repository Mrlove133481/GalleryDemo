package com.mrlove.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Mrlove-MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = findViewById(R.id.imageView);
        String url = "https://www.baidu.com/img/superlogo_c4d7df0a003d3db9b65e9ef0fe6da1ec.png";
        //1.创建一个volley队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.创建imageLoader对象,管理图片 ,imageLoader对象有两个参数:
            //RequestQueue:请求的队列
            //ImageLoader.ImageCache: 缓存设置
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            //3.Lru–>(Least recent used)最少最近使用算法 设置图片的缓冲对象为50个.
            private LruCache<String,Bitmap> cache = new LruCache<>(50);
            @Override
            public Bitmap getBitmap(String url) {
                //得到缓冲图片地址
                return cache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                //放进缓存,图片的URL地址和图片
                cache.put(url,bitmap);
            }
        });
        //4.通过imageLoader.get()方法设置图片,get方法有两个参数:
            //url:图片地址
            //ImageLoader.ImageListener 监听对象
                    //Response.Listener:正确响应处理操作
                    //Response.ErrorListener:错误响应处理操作
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
            }
        });

    }
}
