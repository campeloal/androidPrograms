package com.example.alcampelo.swipeviews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alcampelo.swipeviews.model.MyFourthFragment;
import com.example.alcampelo.swipeviews.model.MyFragment;
import com.example.alcampelo.swipeviews.model.MySecFragment;
import com.example.alcampelo.swipeviews.model.MyThirdFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 4;

    /** Constructor of the class */
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {

//        MyFragment myFragment = new MyFragment();
//        Bundle data = new Bundle();
//        data.putInt("current_page", arg0+1);
//        myFragment.setArguments(data);
//        return myFragment;

        switch(arg0) {
            case 0:
                return MyFragment.newInstance();
            case 1:
                return MySecFragment.newInstance();
            case 2:
                return MyThirdFragment.newInstance();
            case 3:
                return MyFourthFragment.newInstance();
            default:
                return MyFragment.newInstance();
        }
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
