package com.example.smartcityc_1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartcityc_1.AppClient;
import com.example.smartcityc_1.R;
import com.example.smartcityc_1.activity.AppHomeActivity;
import com.example.smartcityc_1.activity.MainActivity;
import com.example.smartcityc_1.activity.MotifInfoActivity;
import com.example.smartcityc_1.activity.MotifPwdActivity;
import com.example.smartcityc_1.activity.OrderActivity;
import com.example.smartcityc_1.activity.YjfkActivity;
import com.example.smartcityc_1.bean.UserInfos;
import com.example.smartcityc_1.bean.UserList;
import com.example.smartcityc_1.net.VolleyImage;
import com.example.smartcityc_1.net.VolleyLo;
import com.example.smartcityc_1.net.VolleyLoImage;
import com.example.smartcityc_1.net.VolleyTo;
import com.example.smartcityc_1.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 10:14
 */
public class MyCenterFragment extends Fragment {
    private ImageView ivImage;
    private TextView tvName;
    private TextView tvTel;
    private LinearLayout lauyoutInfo;
    private LinearLayout layoutOrder;
    private LinearLayout layoutPwd;
    private LinearLayout layoutSubmit;
    private TextView tvExit;

    public MyCenterFragment() {
    }

    public MyCenterFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    private AppHomeActivity appHomeActivity;

    public static MyCenterFragment newInstance(AppHomeActivity appHomeActivity) {
        return new MyCenterFragment(appHomeActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_center_framgent, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVolley();
        lauyoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotifInfoActivity.class);
                intent.putExtra("info", userInfos);
                startActivityForResult(intent, 1);
            }
        });
        layoutPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MotifPwdActivity.class);
                intent.putExtra("info", userInfos);
                startActivity(intent);
            }
        });

        layoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("date", userInfos);
                startActivity(intent);

            }
        });
        layoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), YjfkActivity.class);
                intent.putExtra("date", userInfos);
                startActivity(intent);
            }
        });
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(appHomeActivity, MainActivity.class));
                appHomeActivity.finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    setVolley();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    List<UserList> userLists;

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getUsers")
                .setDialog(getActivity())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userLists = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).toString()
                                , new TypeToken<List<UserList>>() {
                                }.getType());
                        setVolleyDetails();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    UserInfos userInfos;

    private void setVolleyDetails() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getUserInfo")
                .setJsonObject("userid", getUserName(AppClient.username))
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        userInfos = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).optJSONObject(0).toString(), UserInfos.class);
                        tvName.setText(userInfos.getName());
                        tvTel.setText(userInfos.getPhone());
                        userInfos.setUsreid(getUserName(AppClient.username));
                        VolleyImage volleyImage = new VolleyImage();
                        volleyImage.setUrl(userInfos.getAvatar())
                                .setVolleyLoImage(new VolleyLoImage() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        ivImage.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {

                                    }
                                }).start();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    private String getUserName(String username) {
        for (int i = 0; i < userLists.size(); i++) {
            UserList userList = userLists.get(i);
            if (userList.getUsername().equals(username)) {
                return userList.getUserid();
            }
        }
        return "1";
    }

    private void initView() {
        ivImage = getView().findViewById(R.id.iv_image);
        tvName = getView().findViewById(R.id.tv_name);
        tvTel = getView().findViewById(R.id.tv_tel);
        lauyoutInfo = getView().findViewById(R.id.lauyout_info);
        layoutOrder = getView().findViewById(R.id.layout_order);
        layoutPwd = getView().findViewById(R.id.layout_pwd);
        layoutSubmit = getView().findViewById(R.id.layout_submit);
        tvExit = getView().findViewById(R.id.tv_exit);
    }
}
