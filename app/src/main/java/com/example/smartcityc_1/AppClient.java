package com.example.smartcityc_1;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 7:33
 */
public class AppClient extends Application {
    public static SharedPreferences sharedPreferences;
    public static final String IP = "IP";
    public static final String PORT = "port";
    private static RequestQueue requestQueue;
    public static final String IsFirst = "isFirst";
    public static String username = "abc";
    private static List<AppCompatActivity> appCompatActivities = new ArrayList<>();

    public static void add(AppCompatActivity appCompatActivity) {
        appCompatActivities.add(appCompatActivity);
    }

    public static void finalyAll() {
        for (int i = 0; i < appCompatActivities.size(); i++) {
            AppCompatActivity appCompatActivity = appCompatActivities.get(i);
            if (appCompatActivity != null && !appCompatActivity.isFinishing()) {
                appCompatActivity.finish();
            }
        }
    }

    public static String name, tel, address, time;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("aaa", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
    }

    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }

    public static void add(ImageRequest imageRequest) {
        requestQueue.add(imageRequest);
    }

}
