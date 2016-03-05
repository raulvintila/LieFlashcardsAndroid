package com.raulvintila.app.lieflashcards.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.nineoldandroids.animation.ObjectAnimator;
import com.raulvintila.app.lieflashcards.Activities.MainActivity;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.UI.ItemTouchHelper.DecksItemTouchHelperCallback;
import com.raulvintila.app.lieflashcards.Adapters.DecksRecyclerListAdapter;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Communication.yolo;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;

import de.greenrobot.event.EventBus;

public class RecyclerListViewFragment extends Fragment {

    private List<DeckRecyclerViewItem> data;

    private View myFragmentView;
    private IDatabaseManager databaseManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private ItemTouchHelper mItemTouchHelper;

    public RecyclerListViewFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_recycler_list_view, container, false);
        return myFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeData();

        DecksRecyclerListAdapter adapter = new DecksRecyclerListAdapter(data,getActivity(), databaseManager);

        mRecyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true); // better performance for fixed size RecyclerViews
        mRecyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // ItemTouchHelper will handle drag&drop and swipe-to-dismiss
        ItemTouchHelper.Callback callback = new DecksItemTouchHelperCallback(adapter,getActivity());
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        // Handles the FAM behaviour on scrolling
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mInitialScroll = 0;
            boolean semafor = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Used to determine the scroll direction
                int scrolledOffset = mRecyclerView.computeVerticalScrollOffset();
                boolean scrollUp = scrolledOffset > mInitialScroll;
                mInitialScroll = scrolledOffset;


                // Scrolling down
                if (scrollUp == true && semafor == false) {
                    semafor = true;
                    final FloatingActionButton fam = (FloatingActionButton) getActivity().findViewById(R.id.fab_expand_menu_button);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(fam, "translationY", 200);
                    anim.start();

                }
                // Scrolling up
                if (scrollUp == false && semafor) {
                    semafor = false;
                    final FloatingActionButton fam = (FloatingActionButton) getActivity().findViewById(R.id.fab_expand_menu_button);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(fam, "translationY", 0);
                    anim.start();
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        CustomModel.getInstance().setAdapter(adapter);
    }

    private void initializeData() {

        databaseManager = ((MyApplication)getActivity().getApplicationContext()).databaseManager;


        List<DBDeck> decks = databaseManager.getAllDecks();

        data = new ArrayList<>();

        for(int i = 0; i < decks.size(); i++) {
            DeckRecyclerViewItem deckRecyclerViewItem = new DeckRecyclerViewItem(i,decks.get(i).getName(),generateCardsStatsStringFromDeck(decks.get(i)),R.drawable.tsunade,generateLastStudiedStringFromDeck(decks.get(i)),new Integer[]{0},20,decks.get(i).getId());
            data.add(deckRecyclerViewItem);
        }


        // Sends the data to MainActivity
        ((MainActivity)getActivity()).setData(data);
    }

    public String generateCardsStatsStringFromDeck(DBDeck deck) {

        long deck_cards_per_day = deck.getNumber_of_cards_per_day();
        Double new_cards;
        long total_number_of_cards = deck.getNumber_of_cards();

        if (deck_cards_per_day <= total_number_of_cards) {
            new_cards = deck_cards_per_day * 0.75;
        } else {
            new_cards = total_number_of_cards * 0.75;
        }
        return new_cards.intValue() + " / " + deck_cards_per_day + " / " + total_number_of_cards;

    }

    public String generateLastStudiedStringFromDeck(DBDeck deck){

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(deck.getDate_created());
        Calendar today = Calendar.getInstance();

        return getDurationBreakdown(today.getTimeInMillis() - thatDay.getTimeInMillis());
    }

    public static String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        int seconds = (int) (millis / 1000);
        if ( seconds < 60 ) return seconds + "s";
        int minutes = (int) ((millis / (1000*60)) );
        if ( minutes < 60 ) return  minutes + "m";
        int hours   = (int) ((millis / (1000*60*60)));
        if ( hours < 24 ) return  hours + "h";
        int days = (int) (((millis / (1000*60*60 *24))));
        if ( days < 7 ) return  days + "d";
        int weeks = (int) (((millis / (1000*60*60 *24 * 7))));
        if ( weeks < 4 ) return  weeks + "w";
        int months = (int) (((millis / (1000*60*60 *24 * 7 * 4))));
        if (months < 12 ) return  months + "mn";
        int years = (int) (((millis / (1000*60*60 *24 * 7 * 12))));
        return  years + "y";
    }

    /*@Override
    public void onStop() {
        if (databaseManager != null) {
            databaseManager.closeDbConnections();
           // CustomModel.getInstance().setDatabaseManager(null);
        }
        super.onStop();
    }*/

}

