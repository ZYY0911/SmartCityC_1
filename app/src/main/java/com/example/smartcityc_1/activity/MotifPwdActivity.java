package com.example.smartcityc_1.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityc_1.R;
import com.example.smartcityc_1.bean.UserInfos;
import com.example.smartcityc_1.net.VolleyLo;
import com.example.smartcityc_1.net.VolleyTo;
import com.example.smartcityc_1.util.Utils;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 10:31
 */
public class MotifPwdActivity extends AppCompatActivity {
    private ImageView itemChange;
    private TextView title;
    private TextView title1;
    private EditText etPwd;
    private EditText etPwdNew;
    private EditText etPwdNew2;
    private TextView tvSave;
    UserInfos userInfos;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motif_pwd_layout);
        initView();
        itemChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userInfos = (UserInfos) getIntent().getSerializableExtra("info");
        title.setText("修改密码");
        setVolley_PWd();
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPwd.getText()) || TextUtils.isEmpty(etPwdNew.getText())) {
                    Utils.showDialog("密码不能为空", MotifPwdActivity.this);
                    return;
                }
                String pwd = etPwd.getText().toString();
                String pwdNEW = etPwdNew.getText().toString();
                String pwdNEW2 = etPwdNew2.getText().toString();
                if (!pwd.equals(password)) {
                    Utils.showDialog("原密码不正确", MotifPwdActivity.this);
                    return;
                }
                if (!pwdNEW.equals(pwdNEW2)) {
                    Utils.showDialog("两次密码不一致", MotifPwdActivity.this);
                    return;
                }
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setURl("setPwd")
                        .setJsonObject("userid", userInfos.getUsreid())
                        .setJsonObject("password", pwdNEW)
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                if (jsonObject.optString("RESULT").equals("S")) {
                                    Utils.showDialog("保存成功", MotifPwdActivity.this);
                                } else {
                                    Utils.showDialog("保存失败", MotifPwdActivity.this);
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Utils.showDialog("保存失败", MotifPwdActivity.this);
                            }
                        }).start();
            }
        });
    }

    private void setVolley_PWd() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getPwd")
                .setJsonObject("userid", userInfos.getUsreid())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        password = jsonObject.optString("password");
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
        etPwd = findViewById(R.id.et_pwd);
        etPwdNew = findViewById(R.id.et_pwd_new);
        etPwdNew2 = findViewById(R.id.et_pwd_new2);
        tvSave = findViewById(R.id.tv_save);
    }
}
