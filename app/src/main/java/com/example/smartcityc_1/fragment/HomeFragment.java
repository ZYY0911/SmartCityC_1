package com.example.smartcityc_1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.example.smartcityc_1.R;
import com.example.smartcityc_1.activity.AllServiceUrl;
import com.example.smartcityc_1.activity.AppHomeActivity;
import com.example.smartcityc_1.adapter.HomeServiceAdapter;
import com.example.smartcityc_1.adapter.HoutThemeAdapter;
import com.example.smartcityc_1.bean.HomeImages;
import com.example.smartcityc_1.bean.HomeService;
import com.example.smartcityc_1.bean.NewsList;
import com.example.smartcityc_1.bean.SerViceOrder;
import com.example.smartcityc_1.net.VolleyImage;
import com.example.smartcityc_1.net.VolleyLo;
import com.example.smartcityc_1.net.VolleyLoImage;
import com.example.smartcityc_1.net.VolleyTo;
import com.example.smartcityc_1.util.OnClickItem;
import com.example.smartcityc_1.util.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 8:12
 */
public class HomeFragment extends Fragment {

    private ViewFlipper viewFlipper;
    private GridView girdService;
    private GridView girdTheme;
    private LinearLayout layoutNew;
    private LinearLayout newsLayout;

    public HomeFragment() {
    }

    public HomeFragment(AppHomeActivity appHomeActivity) {
        this.appHomeActivity = appHomeActivity;
    }

    private AppHomeActivity appHomeActivity;

    public static HomeFragment newInstance(AppHomeActivity appHomeActivity) {
        return new HomeFragment(appHomeActivity);
    }

    //
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setVolley_Images();
        setVolley_Service();
        setVolley_Subject();
        setVolley_News();
    }

    List<NewsList> newsLists;

    private void setVolley_News() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getNEWsList")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        newsLists = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).toString()
                                , new TypeToken<List<NewsList>>() {
                                }.getType());
                        setVolley_NewsType();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    List<String> newTypes;

    private void setVolley_NewsType() {
        newTypes = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getAllNewsType")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Utils.Row);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            newTypes.add(jsonObject1.optString("newstype"));
                        }
                        setVolley_Recommend();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<Integer> recommendNews;

    private void setVolley_Recommend() {
        recommendNews = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getRecommend")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray(Utils.Row);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            recommendNews.add(Integer.parseInt(jsonObject1.optString("newsid")));
                        }
                        setNewTypeLayout();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setNewTypeLayout() {
        layoutNew.removeAllViews();
        newTypes.add(0, "推荐");
        Log.i("aaa", "setNewTypeLayout: " + strings.size());
        for (int i = 0; i < newTypes.size(); i++) {
            final TextView textView = new TextView(getActivity());
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(20);
            textView.setText(newTypes.get(i));
            textView.setGravity(Gravity.CENTER);
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setBackgroundResource(R.drawable.text_line);
            }
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < layoutNew.getChildCount(); j++) {
                        TextView textView = (TextView) layoutNew.getChildAt(j);
                        if (j == finalI) {
                            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textView.setBackgroundResource(R.drawable.text_line);
                        } else {
                            textView.setTextColor(Color.parseColor("#333333"));
                            textView.setBackgroundResource(R.drawable.text_no_line);
                        }
                    }
                    String type = textView.getText().toString();
                    setLayoutType(type);
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 40, 0);
            textView.setLayoutParams(layoutParams);
            layoutNew.addView(textView);
        }
        setLayoutType("推荐");
    }

    private void setLayoutType(String type) {
        List<NewsList> newsLists1 = new ArrayList<>();
        for (int i = 0; i < newsLists.size(); i++) {
            NewsList newsList = newsLists.get(i);
            if (type.equals("推荐")) {
                if (isTyj(Integer.parseInt(newsList.getNewsid()))) {
                    newsLists1.add(newsList);
                }
            } else {
                if (newsList.getNewsType().equals(type)) {
                    newsLists1.add(newsList);
                }
            }
        }
        newsLayout.removeAllViews();
        for (int i = 0; i < newsLists1.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.news_item, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(10, 5, 10, 5);
            view.setLayoutParams(layoutParams);
            final ViewHolder holder = new ViewHolder();
            holder.itemImage = view.findViewById(R.id.item_image);
            holder.itemTitle = view.findViewById(R.id.item_title);
            holder.itemContext = view.findViewById(R.id.item_context);
            holder.itemMsg = view.findViewById(R.id.item_msg);
            final NewsList newsList = newsLists1.get(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                }
            });
            VolleyImage volleyImage = new VolleyImage();
            volleyImage.setUrl(newsList.getPicture())
                    .setVolleyLoImage(new VolleyLoImage() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            holder.itemImage.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
            holder.itemTitle.setText(newsList.getTitle());
            holder.itemContext.setText(newsList.getAbstractX());
            VolleyTo volleyTo = new VolleyTo();
            volleyTo.setURl("getCommitById")
                    .setJsonObject("id", newsList.getNewsid())
                    .setVolleyLo(new VolleyLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            JSONArray jsonArray = jsonObject.optJSONArray(Utils.Row);
                            final int all = jsonArray.length();
                            VolleyTo volleyTo1 = new VolleyTo();
                            volleyTo1.setURl("getNewsInfoById")
                                    .setJsonObject("newsid", newsList.getNewsid())
                                    .setVolleyLo(new VolleyLo() {
                                        @Override
                                        public void onResponse(JSONObject jsonObject) {
                                            holder.itemMsg.setText("总评：" + all + "发布日期：" + jsonObject.optJSONArray(Utils.Row).optJSONObject(0).optString("publicTime"));

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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(8)).commit();
                }
            });
            newsLayout.addView(view);
        }
    }

    static class ViewHolder {

        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemContext;
        private TextView itemMsg;
    }


    private boolean isTyj(int id) {
        for (int i = 0; i < recommendNews.size(); i++) {
            if (id == recommendNews.get(i)) {
                return true;
            }
        }
        return false;
    }


    List<String> strings;

    private void setVolley_Subject() {
        strings = new ArrayList<>();
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getAllSubject")
                .setDialog(getActivity())
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String jsonArray[] = jsonObject.optString("ROWS_DETAIL").replace("[", "").replace("]", "").split(",");
                        for (int i = 0; i < jsonArray.length; i++) {
                            strings.add(jsonArray[i].trim());
                        }
                        girdTheme.setAdapter(new HoutThemeAdapter(getActivity(), strings));
                        girdTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(7)).commit();
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }


    List<SerViceOrder> serViceOrders;

    private void setVolley_Service() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getServiceInOrder")
                .setJsonObject("order", 2)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        serViceOrders = new Gson().fromJson(jsonObject.optJSONArray(Utils.Row).toString()
                                , new TypeToken<List<SerViceOrder>>() {
                                }.getType());
                        HomeServiceAdapter adapter = new HomeServiceAdapter(getActivity(), serViceOrders);
                        girdService.setAdapter(adapter);
                        adapter.setOnClickItem(new OnClickItem() {
                            @Override
                            public void onClick(int position, String name) {
                                if (name.equals("更多服务")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(9)).commit();

                                } else if (name.equals("城市巴士")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(3)).commit();
                                } else if (name.equals("停车场")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(1)).commit();
                                } else if (name.equals("生活缴费")) {
                                    appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(5)).commit();
                                } else {
                                    Intent intent = new Intent(appHomeActivity, AllServiceUrl.class);
                                    intent.putExtra("infos", serViceOrders.get(position).getId());
                                    intent.putExtra("date", name);
                                    startActivity(intent);
                                }
                            }
                        });

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    List<HomeImages> homeImages;

    private void setVolley_Images() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setURl("getImages")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        homeImages = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<HomeImages>>() {
                                }.getType());
                        setImageLayout();

                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //true 隐藏

        if (hidden) {

        } else {

        }
    }

    List<ImageView> imageViews;

    private void setImageLayout() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < homeImages.size(); i++) {
            HomeImages homeImage = homeImages.get(i);
            final ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appHomeActivity.switchFragemnt(appHomeActivity.fragments.get(8)).commit();
                }
            });
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            VolleyImage volleyImage = new VolleyImage();
            volleyImage.setUrl(homeImage.getPath())
                    .setVolleyLoImage(new VolleyLoImage() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                            viewFlipper.addView(imageView);
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }).start();
        }
    }

    private void initView() {
        viewFlipper = getView().findViewById(R.id.view_flipper);
        girdService = getView().findViewById(R.id.gird_service);
        girdTheme = getView().findViewById(R.id.gird_theme);
        layoutNew = getView().findViewById(R.id.layout_new);
        newsLayout = getView().findViewById(R.id.news_layout);
    }
}
