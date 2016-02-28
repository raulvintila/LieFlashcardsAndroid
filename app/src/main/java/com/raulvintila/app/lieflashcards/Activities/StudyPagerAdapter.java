package com.raulvintila.app.lieflashcards.Activities;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raulvintila.app.lieflashcards.R;

public class StudyPagerAdapter extends PagerAdapter {

    private Context mContext;
    private Activity mActivity;

    public StudyPagerAdapter(Context context, Activity activity) {
        mContext = context;
        mActivity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.stats_rl;
                break;
            case 1:
                resId = R.id.cram_stats_rl;
                break;
        }
        return mActivity.findViewById(resId);

    }


    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
