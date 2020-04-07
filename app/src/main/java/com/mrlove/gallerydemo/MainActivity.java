package com.mrlove.gallerydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Mrlove-MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textView);
        String url = "https://www.baidu.com";
        //1.创建一个volley队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //2.创建一个请求,请求有三个参数:
            //Method:请求的方式
            //Response.Listener:正确响应处理操作
            //Response.ErrorListener:错误响应处理操作
        StringRequest stringRequest = new StringRequest(
                StringRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"onErrorResponse",error);
                    }
                }
        );
        //把请求添加到队列中处理,Volley执行的是异步操作.
        queue.add(stringRequest);
    }
}
