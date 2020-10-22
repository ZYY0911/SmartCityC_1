package com.example.smartcityc_1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityc_1.R;
import com.example.smartcityc_1.adapter.OrderAdapter;
import com.example.smartcityc_1.bean.OrderTitle;
import com.example.smartcityc_1.bean.UserList;
import com.example.smartcityc_1.net.VolleyLo;
import com.example.smartcityc_1.net.VolleyTo;
import com.example.smartcityc_1.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 10:38
 */
public class OrderActivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        initView();
        title.setText("订单列表");
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setVolleyType();
    }

    List<String> strings;

    private void setVolleyType() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getAllOrderType")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Utils.Row);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            strings.add(jsonArray.optString(i));
                        }
                        setVolley_OrderList();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<OrderTitle> orderTitles;

    private void setVolley_OrderList() {
        orderTitles = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            VolleyTo volleyTo = new VolleyTo();
            final int finalI = i;
            volleyTo.setURl("getOrderByType")
                    .setJsonObject("type", strings.get(i))
                    .setDialog(this)
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<OrderTitle> orderTitles1 = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).toString()
                                    , new TypeToken<List<OrderTitle>>() {
                                    }.getType());
                            orderTitles.addAll(orderTitles1);
                            if (finalI == strings.size() - 1) {
                                listView.setAdapter(new OrderAdapter(OrderActivity.this, orderTitles));
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                                        intent.putExtra("date", orderTitles.get(position));
                                        startActivity(intent);
                                    }
                                });
                            }

                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }

    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        listView = findViewById(R.id.list_view);
    }
}
