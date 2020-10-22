package com.example.smartcityc_1.util;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/9 at 9:38
 */
public class CustomPagerTranslation implements ViewPager.PageTransformer {
    private ViewPager viewPager;

    private int maxTranslation;

    public CustomPagerTranslation(Context context) {
        this.maxTranslation = dp2px(context, 180);
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) page.getParent();
        }
        int leftInScreen = page.getLeft() - viewPager.getScrollX();
        int currentXInViewPager = leftInScreen + page.getMeasuredWidth() / 2;
        int offsetX = currentXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            page.setScaleY(scaleFactor);
            page.setScaleX(scaleFactor);
            page.setTranslationX(-maxTranslation * offsetRate);
        }
    }

    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);

    }
}
