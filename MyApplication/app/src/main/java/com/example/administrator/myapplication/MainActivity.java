package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.administrator.myapplication.fragment.ButlerFragment;
import com.example.administrator.myapplication.fragment.GirlFragment;
import com.example.administrator.myapplication.fragment.UserFragment;
import com.example.administrator.myapplication.fragment.WechatFragment;
import com.example.administrator.myapplication.ui.SettingActivity;
import com.example.administrator.myapplication.util.L;
import com.example.administrator.myapplication.util.ShareUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;

    //悬浮窗
    private FloatingActionButton fab_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();

        //ShareUtils.putString(this,"username","李子灿");
        //String username = ShareUtils.getString(this,"username","lizican");
    }


    //初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("综合统计");
        mTitle.add("近期表现");
        mTitle.add("最高记录");
        mTitle.add("队友对手");

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    //初始化View
    private void initView() {
        fab_setting = (FloatingActionButton)findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        mTabLayout = (TabLayout)findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager)findViewById(R.id.mViewPager);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
