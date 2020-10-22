package com.example.smartcityc_1.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcityc_1.AppClient;
import com.example.smartcityc_1.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 7:51
 */
public class NetActivity extends AppCompatActivity {
    private TextView title;
    private TextView title1;
    private EditText etIp;
    private EditText etPort;
    private Button btSubmit;
    private Button btExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_layout);
        initView();
        title.setText("网络设置");
        etPort.setText(AppClient.sharedPreferences.getString(AppClient.PORT, "8080"));
        etIp.setText(AppClient.sharedPreferences.getString(AppClient.IP, "192.168.155.106"));
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = AppClient.sharedPreferences;
                preferences.edit().putString(AppClient.IP, etIp.getText().toString()).apply();
                preferences.edit().putString(AppClient.PORT, etPort.getText().toString()).apply();
                Toast.makeText(NetActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        etIp = findViewById(R.id.et_ip);
        etPort = findViewById(R.id.et_port);
        btSubmit = findViewById(R.id.bt_submit);
        btExit = findViewById(R.id.bt_exit);
    }
}
