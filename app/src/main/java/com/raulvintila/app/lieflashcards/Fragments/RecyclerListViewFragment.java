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
            DeckRecyclerViewItem deckRecyclerViewItem = new DeckRecyclerViewItem(i,decks.get(i).getName(),"25 / 70 / 250",R.drawable.tsunade,"1y",new Integer[]{0},20,decks.get(i).getId());
            data.add(deckRecyclerViewItem);
        }


        // Sends the data to MainActivity
        ((MainActivity)getActivity()).setData(data);
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

