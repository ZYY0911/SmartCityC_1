package com.example.smartcityc_1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartcityc_1.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private TextView title1;
    private Button netSetting;
    private Button mainIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        netSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NetActivity.class));
            }
        });
        mainIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppHomeActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        netSetting = findViewById(R.id.net_setting);
        mainIn = findViewById(R.id.main_in);
    }
}