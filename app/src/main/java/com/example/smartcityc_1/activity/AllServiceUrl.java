package com.example.smartcityc_1.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityc_1.R;
import com.example.smartcityc_1.bean.HomeService;
import com.example.smartcityc_1.net.VolleyImage;
import com.example.smartcityc_1.net.VolleyLo;
import com.example.smartcityc_1.net.VolleyLoImage;
import com.example.smartcityc_1.net.VolleyTo;
import com.example.smartcityc_1.util.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/9 at 7:31
 */
public class AllServiceUrl extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private WebView webView;
    private String index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wervic_web);

        initView();
        index = getIntent().getStringExtra("infos");
            itemChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        title.setText(getIntent().getStringExtra("date"));
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("service_info")
                .setJsonObject("serviceid", index)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        HomeService service = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).optJSONObject(0).toString(), HomeService.class);
                        webView.loadUrl(service.getUrl());

                    }


                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }


    private void initView() {
        itemChange = findViewById(R.id.item_change);
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        webView = findViewById(R.id.web_view);
    }
}
