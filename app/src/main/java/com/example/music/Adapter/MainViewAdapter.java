package com.example.music.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayFragment= new ArrayList<>();
    private ArrayList<String> arrayTitle = new ArrayList<>();
    public MainViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayFragment.get(position);
    }

    @Override
    public int getCount() {
        return arrayFragment.size();
    }

    public void addFragment(Fragment fragment)
    {
        arrayFragment.add(fragment);

    }


}
