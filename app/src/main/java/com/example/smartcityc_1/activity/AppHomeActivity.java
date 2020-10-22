package com.example.smartcityc_1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcityc_1.R;
import com.example.smartcityc_1.fragment.AadGroype;
import com.example.smartcityc_1.fragment.AddGroupChilde;
import com.example.smartcityc_1.fragment.DFFragement;
import com.example.smartcityc_1.fragment.DFFragment1;
import com.example.smartcityc_1.fragment.EmptyFragment;
import com.example.smartcityc_1.fragment.EmptyFragment2;
import com.example.smartcityc_1.fragment.EmptyFragment3;
import com.example.smartcityc_1.fragment.EmptyFragment4;
import com.example.smartcityc_1.fragment.FjhsJFragment;
import com.example.smartcityc_1.fragment.HbFragment;
import com.example.smartcityc_1.fragment.HoManager;
import com.example.smartcityc_1.fragment.HomeFragment;
import com.example.smartcityc_1.fragment.LJWPASFragment;
import com.example.smartcityc_1.fragment.LiefMoney;
import com.example.smartcityc_1.fragment.MyCenterFragment;
import com.example.smartcityc_1.fragment.MyOderActivity;
import com.example.smartcityc_1.fragment.SFDragment1;
import com.example.smartcityc_1.fragment.SfFragment;
import com.example.smartcityc_1.fragment.SmartBus;
import com.example.smartcityc_1.fragment.TccFragment;
import com.example.smartcityc_1.fragment.XxylFragment;
import com.example.smartcityc_1.fragment.YylsFragment;
import com.example.smartcityc_1.fragment.YysmFragemnt;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 8:04
 */
public class AppHomeActivity extends AppCompatActivity {
    private TextView tvTitle;
    private BottomNavigationView bottomNav;
    public List<Fragment> fragments;
    private LinearLayout titleLayout;
    private FrameLayout frameLayout;
    private EditText etSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_home_layout);
        initView();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance(this));//0
        fragments.add(TccFragment.newInstance(this));//1
        fragments.add(MyCenterFragment.newInstance(this));
        fragments.add(SmartBus.newInstance(this));//3
        fragments.add(MyOderActivity.newInstance(this));
        fragments.add(LiefMoney.newInstance(this));//5
        fragments.add(HbFragment.newInstance(this));
        fragments.add(EmptyFragment.newInstance(this));//7
        fragments.add(EmptyFragment2.newInstance(this));
        fragments.add(EmptyFragment3.newInstance(this));//9
        fragments.add(LJWPASFragment.newInstance(this));//10
        fragments.add(YysmFragemnt.newInstance(this));//11
        fragments.add(YylsFragment.newInstance(this));//12
        fragments.add(XxylFragment.newInstance(this));
        fragments.add(FjhsJFragment.newInstance(this));//14


        fragments.add(SfFragment.newInstance(this));//15
        fragments.add(HoManager.newInstance(this));//16
        fragments.add(DFFragement.newInstance(this));//17
        fragments.add(EmptyFragment4.newInstance(this));//18
        fragments.add(AadGroype.newInstance(this));//19
        fragments.add(AddGroupChilde.newInstance(this));//20
        fragments.add(SFDragment1.newInstance(this));//21
        fragments.add(DFFragment1.newInstance(this));//22
        switchFragemnt(fragments.get(0)).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        switchFragemnt(fragments.get(0)).commit();
                        break;
                    case R.id.action_center:
                        switchFragemnt(fragments.get(2)).commit();
                        break;

                    case R.id.action_hb:
                        switchFragemnt(fragments.get(6)).commit();

                        break;
                    case R.id.action_new:
                        switchFragemnt(fragments.get(8)).commit();
                        break;

                    case R.id.action_service:
                        switchFragemnt(fragments.get(9)).commit();
                        break;
                }
                return true;
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (null != event && KeyEvent.KEYCODE_ENTER == event.getKeyCode()) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            switchFragemnt(fragments.get(8)).commit();
                            etSearch.setText("");
                            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputmanger.hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    Fragment currentFragment = new Fragment();

    public FragmentTransaction switchFragemnt(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.frame_layout, fragment, fragment.getClass().getName());

        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        if (!fragment.getClass().getName().equals(fragments.get(0).getClass().getName())) {
            titleLayout.setVisibility(View.GONE);
        } else {
            titleLayout.setVisibility(View.VISIBLE);
        }
        currentFragment = fragment;
        return transaction;
    }

    public void backOnClick() {
        switchFragemnt(fragments.get(0)).commit();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        bottomNav = findViewById(R.id.bottom_nav);
        titleLayout = findViewById(R.id.title_layout);
        frameLayout = findViewById(R.id.frame_layout);
        etSearch = findViewById(R.id.et_search);
    }
}
