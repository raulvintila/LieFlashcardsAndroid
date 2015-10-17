package com.raulvintila.app.lieflashcards.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.raulvintila.app.lieflashcards.Adapters.PlayModeAdapter;
import com.raulvintila.app.lieflashcards.PlayMode;
import com.raulvintila.app.lieflashcards.R;

import java.util.ArrayList;
import java.util.List;

public class PlayModesFragment extends Fragment {

    private List<PlayMode> data;
    private View mFragmentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.play_modes_fragment, container, false);
        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstaceState) {
        super.onViewCreated(view, savedInstaceState);

        initializeData();

        //initializeRecyclerView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeRecyclerView();
    }

    public void initializeRecyclerView() {

        PlayModeAdapter adapter = new PlayModeAdapter(data);

        mRecyclerView = (RecyclerView) mFragmentView;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void initializeData() {

        data = new ArrayList<PlayMode>();

        data.add(new PlayMode("Study"));
        data.add(new PlayMode("Cram"));
        data.add(new PlayMode("Test"));
    }
}