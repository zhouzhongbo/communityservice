package com.bobo.communityservice.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bobo.communityservice.R;
import com.bobo.communityservice.fragment.HelpFragment;
import com.bobo.communityservice.fragment.MarketFragment;
import com.bobo.communityservice.fragment.MineFragment;
import com.bobo.communityservice.fragment.NoticeFragement;
import com.bobo.communityservice.tools.GeneralData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar bottomNavigationBar;
    private BadgeItem numberBadgeItem;
    private int lastSelectedPosition = 0;
//    //声明ViewPager
//    private ViewPager mViewPager;
//    //适配器
//    private FragmentPagerAdapter mAdapter;
//    //装载Fragment的集合
//    private List<Fragment> mFragments;
    NoticeFragement noticeFragment;
    MarketFragment  marketFragment;
    MineFragment  mineFragment;
    HelpFragment  helpFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        testInit();

    }


    private void testInit(){
        GeneralData gd = new GeneralData(this);
//        gd.generateNotice();
    }


    private void initView(){
//        mViewPager = (ViewPager)findViewById(R.id.id_viewpager);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this);

        numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(false);

        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED);
//            bottomNavigationBar
//                    .setMode(BottomNavigationBar.MODE_SHIFTING);

//            bottomNavigationBar
//                    .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.notice, R.string.public_notice).setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.help, R.string.help).setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.market, R.string.market).setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.mine, R.string.mine).setActiveColorResource(R.color.brown))
                .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                .initialise();
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        noticeFragment = NoticeFragement.newInstance("位置");
        transaction.replace(R.id.tabs, noticeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int i) {
        lastSelectedPosition = i;
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (i) {
            case 0:
                if (noticeFragment == null) {
                    noticeFragment = NoticeFragement.newInstance(getString(R.string.public_notice));
                }
                transaction.replace(R.id.tabs, noticeFragment);
                break;
            case 1:
                if (helpFragment == null) {
                    helpFragment = HelpFragment.newInstance(getString(R.string.help));
                }
                transaction.replace(R.id.tabs, helpFragment);
                break;
            case 2:
                if (marketFragment == null) {
                    marketFragment = MarketFragment.newInstance(getString(R.string.market));
                }
                transaction.replace(R.id.tabs, marketFragment);
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance(getString(R.string.mine));
                }
                transaction.replace(R.id.tabs, mineFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int i) {

    }

    @Override
    public void onTabReselected(int i) {

    }
}
