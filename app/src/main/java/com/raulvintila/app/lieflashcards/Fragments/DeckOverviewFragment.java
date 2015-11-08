package com.raulvintila.app.lieflashcards.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.DeckModeChange;
import com.raulvintila.app.lieflashcards.R;

import de.greenrobot.event.EventBus;


public class DeckOverviewFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private String default_main_mode = "Study";
    private String default_sub_mode = "Normal";

    private int mPage;
    IDatabaseManager databaseManager;


    public static DeckOverviewFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DeckOverviewFragment fragment = new DeckOverviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(DeckModeChange event){
        TextView deck_main_mode = (TextView) getActivity().findViewById(R.id.deck_main_mode);
        TextView deck_sub_mode = (TextView) getActivity().findViewById(R.id.deck_sub_mode);

        deck_main_mode.setText(event.getMain_mode());
        deck_sub_mode.setText(event.getSub_mode());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        // In order to achieve nested fragments you have to add them dynamically
        Fragment fragment = new PlayModesFragment();
        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.play_mode_fragment_container, fragment).commit();
        //deck_cards_per_day.setText(""+deck.getNumber_of_cards_per_day()+ " 2 6");
        return view;
    }


}
