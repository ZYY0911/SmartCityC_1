package com.example.smartcityc_1.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc_1.AppClient;
import com.example.smartcityc_1.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 7:42
 */
public class GlideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private int image[] = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = AppClient.sharedPreferences;
        if (preferences.getBoolean(AppClient.IsFirst, true)) {
            preferences.edit().putBoolean(AppClient.IsFirst, true).apply();
        } else {
            startActivity(new Intent(GlideActivity.this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.glide_layout);
        initView();

    }

    List<ImageView> imageViews;

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        linearLayout = findViewById(R.id.linear_layout);
        imageViews = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(image[i]);
            TextView textView = new TextView(this);
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.tv_slect_yuan);
            } else {
                textView.setBackgroundResource(R.drawable.tv_noselect_yuan);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.setMargins(20, 0, 20, 0);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams1.setMargins(20, 0, 20, 0);
            imageView.setLayoutParams(layoutParams1);
            textView.setLayoutParams(layoutParams);
            linearLayout.addView(textView);
            imageViews.add(imageView);
        }
        imageViews.add(new ImageView(this));
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }


            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }


            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = imageViews.get(position);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageViews.size() - 1) {
                    startActivity(new Intent(GlideActivity.this, MainActivity.class));
                    finish();
                    return;
                }
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    TextView textView = (TextView) linearLayout.getChildAt(i);
                    if (i == position) {
                        textView.setBackgroundResource(R.drawable.tv_slect_yuan);
                    } else {
                        textView.setBackgroundResource(R.drawable.tv_noselect_yuan);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
