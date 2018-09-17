package com.example.dadidi.musicplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dadidi.musicplayer.fragment.FragmentListSong;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> listFragment;

    public PagerAdapter(FragmentManager fm, ArrayList<Fragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
//        for (int i = 0; i < listFragment.size(); i++){
//            if (i == position){
//                return listFragment.get(i);
//            }
//        }
//        return null;
        switch (position){
            case 0: return listFragment.get(0);
            case 1: return listFragment.get(1);
            case 2: return listFragment.get(2);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
