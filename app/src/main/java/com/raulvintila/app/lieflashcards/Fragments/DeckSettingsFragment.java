package com.raulvintila.app.lieflashcards.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raulvintila.app.lieflashcards.R;


public class DeckSettingsFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static DeckSettingsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DeckSettingsFragment fragment = new DeckSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment fragment = (Fragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.tab1);
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}
