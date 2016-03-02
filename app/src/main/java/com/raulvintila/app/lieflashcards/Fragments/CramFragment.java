package com.raulvintila.app.lieflashcards.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.R;


public class CramFragment extends Fragment {

    private IDatabaseManager databaseManager;
    private View mFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseManager = ((MyApplication)getActivity().getApplicationContext()).databaseManager;
        mFragmentView = inflater.inflate(R.layout.cram_fragment, container, false);

        return mFragmentView;
    }

}

