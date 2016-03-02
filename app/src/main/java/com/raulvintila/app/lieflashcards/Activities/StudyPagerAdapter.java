package com.raulvintila.app.lieflashcards.Activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.raulvintila.app.lieflashcards.R;

import java.util.List;

public class StudyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private List<Fragment> pagerFragments;


    public StudyPagerAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);
        this.viewPager = pager;
    }

    @Override
    public Fragment getItem(int position)
    {
        return (pagerFragments != null && pagerFragments.size() > position) ?
                pagerFragments.get(position) : null;
    }

    @Override
    public int getCount()
    {
        return DeckActivity.PAGES;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        int currentPosition = viewPager.getCurrentItem();

        if (state == ViewPager.SCROLL_STATE_IDLE)
        {
            if ( currentPosition % 2 == 1) viewPager.setCurrentItem(1,false);
            else viewPager.setCurrentItem(2,false);
        }
    }

    public List<Fragment> getPagerFragments() {
        return pagerFragments;
    }

    public void setPagerFragments(final List<Fragment> pagerFragments) {
        this.pagerFragments = pagerFragments;
    }

}

