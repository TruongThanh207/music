package com.example.music.Acticity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.music.Adapter.MainViewAdapter;
import com.example.music.Fragment.Fragment_tim_kiem;
import com.example.music.Fragment.Fragment_trang_chu;
import com.example.music.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity{
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout)findViewById(R.id.myTabLayout);
        viewPager = (ViewPager)findViewById(R.id.myViewPager);
        init();
    }

    private void init() {
        MainViewAdapter mainViewAdapter= new MainViewAdapter(getSupportFragmentManager());
        mainViewAdapter.addFragment(new Fragment_trang_chu());
        mainViewAdapter.addFragment(new Fragment_tim_kiem());
        viewPager.setAdapter(mainViewAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ichomewhite);
        tabLayout.getTabAt(1).setIcon(R.drawable.icsearh);
    }



}
