package com.raulvintila.app.lieflashcards.Fragments;

import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.raulvintila.app.lieflashcards.Activities.CardCollectionActivity;
import com.raulvintila.app.lieflashcards.Adapters.CardRecyclerListAdapter;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.R;

import java.util.ArrayList;
import java.util.List;


public class CardRecyclerFragment extends Fragment implements SearchView.OnQueryTextListener{

    private List<DBCard> data;
    private View mFragmentView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private IDatabaseManager databaseManager;
    private CardRecyclerListAdapter adapter;
    private Toolbar toolbar;
    private List<DBCard> filtered_list;
    private TextView deck_search_tv;
    private TextView toolbar_cards_tv;
    public Long deck_id;



    private void showDecksPopup(View v){
        PopupMenu popup = new PopupMenu(getActivity(), v);
        popup.getMenu().add(0,0,0,"All Cards");
        for(int i =0; i< databaseManager.getAllDecks().size();i++){
            DBDeck deck = databaseManager.getAllDecks().get(i);
            Integer deck_id = (int) (long) deck.getId();
            popup.getMenu().add(0,deck_id,0,deck.getName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deck_id = new Long(item.getItemId());
                CustomModel.getInstance().setDeck_id(deck_id);
                filtered_list = deck_filter(data, deck_id);
                adapter.animateTo(filtered_list);
                mRecyclerView.scrollToPosition(0);
                //Paint textPaint = deck_search_tv.getPaint();
                //Rect bounds = new Rect();
                Paint textPaint = deck_search_tv.getPaint();
/*                textPaint.getTextBounds(item.getTitle().toString(), 0, item.getTitle().toString().length(), bounds);
                int height = bounds.height();
                int width = bounds.width();*/
                String deck_name = item.getTitle().toString();
                float width = textPaint.measureText(deck_name);
                int widthint = getActivity().getResources().getDisplayMetrics().widthPixels;
                float maxwidth = widthint/2;
                int i = deck_name.length();
                while (width > maxwidth )
                {
                    deck_name = deck_name.substring(0, i)+"...";
                    width = textPaint.measureText(deck_name);
                    i -= 1;
                }
                deck_search_tv.setText(deck_name);

                toolbar_cards_tv.setText(filtered_list.size() + " cards");
                return true;
            }
        });
        popup.show();
        if (popup.getDragToOpenListener() instanceof ListPopupWindow.ForwardingListener) {
            ListPopupWindow.ForwardingListener listener = (ListPopupWindow.ForwardingListener) popup.getDragToOpenListener();
            listener.getPopup().setVerticalOffset(-v.getHeight());
            listener.getPopup().setHorizontalOffset(getActivity().findViewById(R.id.action_search).getWidth());
            listener.getPopup().show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        databaseManager = ((MyApplication)getActivity().getApplicationContext()).databaseManager;
        mFragmentView = inflater.inflate(R.layout.card_browser_recycler_view_fragment, container, false);

        mRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.recycler_view);



        toolbar = (Toolbar) mFragmentView.findViewById(R.id.toolbar);
        ((CardCollectionActivity)getActivity()).suportActionBar(toolbar);

/*        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_select_deck_text:
                       // showDecksPopup(mFragmentView,item);
                }
                return false;
            }
        });*/

//        spinner = (Spinner) mFragmentView.findViewById(R.id.toolbar_spinner);

        return mFragmentView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstaceState) {
        super.onViewCreated(view, savedInstaceState);
        setHasOptionsMenu(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        initializeData();
        filtered_list = data;
        //toolbar.setSubtitle(data.size() + " cards");
        adapter = new CardRecyclerListAdapter(data,getActivity());

        //initializeRecyclerView();
        mRecyclerView.setAdapter(adapter);

        databaseManager = ((MyApplication)getActivity().getApplicationContext()).databaseManager;

/*
        List<DBDeck> deck_list = databaseManager.getAllDecks();
        DBDeck all_cards_deck = new DBDeck();
        all_cards_deck.setName("All Cards");
        deck_list.add(0, all_cards_deck);


        final DeckSpinnerAdapter spinnerAdapter = new DeckSpinnerAdapter();
        spinnerAdapter.addItems(deck_list);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                Long deck_id = spinnerAdapter.getItem(pos).getId();
                filtered_list = deck_filter(data, deck_id);
                adapter.animateTo(filtered_list);
                mRecyclerView.scrollToPosition(0);
                ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });*/
        deck_search_tv = (TextView) mFragmentView.findViewById(R.id.toolbar_deck_textview);
        toolbar_cards_tv = (TextView) mFragmentView.findViewById(R.id.toolbar_cards_textview);
        RelativeLayout decks_relative_layout = (RelativeLayout) mFragmentView.findViewById(R.id.decks_relative_layout);
        if(CustomModel.getInstance().getDeck_id() != null){
            deck_id = CustomModel.getInstance().getDeck_id();
        } else {
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras != null) {
                deck_id = extras.getLong("deck_id");
            }
            else {
                deck_id = new Long(0);
            }
        }

        if (deck_id == 0)
        {
            filtered_list = data;
            adapter.animateTo(filtered_list);
            mRecyclerView.scrollToPosition(0);

            deck_search_tv.setText("All Cards");
            toolbar_cards_tv.setText(filtered_list.size() + " cards");
        }
        else
        {
            filtered_list = deck_filter(filtered_list, deck_id);
            adapter.animateTo(filtered_list);
            mRecyclerView.scrollToPosition(0);

            Paint textPaint = deck_search_tv.getPaint();
            String deck_name = databaseManager.getDeckById(deck_id).getName();
            float width = textPaint.measureText(deck_name);
            int widthint = this.getResources().getDisplayMetrics().widthPixels;
            float maxwidth = widthint/2;
            int i = deck_name.length();
            while (width > maxwidth )
            {
                deck_name = deck_name.substring(0, i)+"...";
                width = textPaint.measureText(deck_name);
                i -= 1;
            }
            deck_search_tv.setText(deck_name);
            toolbar_cards_tv.setText(filtered_list.size() + " cards");
        }

        decks_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDecksPopup(mFragmentView);
            }
        });




    }

    private List<DBCard> deck_filter(List<DBCard> card_items,Long deck_id) {

        final List<DBCard> filteredModelList = new ArrayList<>();
        if(deck_id == 0){

            return card_items;

        }
        else {
            for (DBCard card_item : card_items) {
                final Long card_deck_id = card_item.getDeckId();
                if ( card_deck_id.equals(deck_id)) {
                    filteredModelList.add(card_item);
                }
            }
        }

        return filteredModelList;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_card_collection, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);



    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<DBCard> filteredModelList = search_filter(filtered_list, query);
        adapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<DBCard> search_filter(List<DBCard> card_items, String query) {
        query = query.toLowerCase();

        final List<DBCard> filteredModelList = new ArrayList<>();
        for (DBCard card_item : card_items) {
            final String question = card_item.getQuestion().toLowerCase();
            final String answer = card_item.getAnswer().toLowerCase();
            if (question.contains(query) || answer.contains(query)) {
                filteredModelList.add(card_item);
            }
        }
        return filteredModelList;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeRecyclerView();
    }

    public void initializeRecyclerView() {

        adapter.setAdapterListener(new CardRecyclerListAdapter.AdapterListener() {
            @Override
            public void onModelObjectClicked(final DBCard item, final int position) {
                new MaterialDialog.Builder((CardCollectionActivity) getActivity())
                        .title("Delete")
                        .content("Are you sure you want to delete this card")
                        .positiveText(R.string.yes)
                        .negativeText("No")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {

                                databaseManager.deleteCardById(item.getId());
                                DBDeck deck = databaseManager.getDeckById(item.getDeckId());
                                deck.setNumber_of_cards(deck.getNumber_of_cards() - 1);

                                deck.setTotal_new_cards(deck.getTotal_new_cards() - 1);
                                databaseManager.insertOrUpdateDeck(deck);
                                adapter.notifyDataSetChanged();

                                if (filtered_list.size() == data.size()) {
                                    data.remove(position);
                                    filtered_list = deck_filter(data, deck_id);
                                    adapter.animateTo(filtered_list);
                                    mRecyclerView.scrollToPosition(0);
                                } else {
                                    filtered_list.remove(position);
                                    initializeData();
                                    filtered_list = deck_filter(filtered_list, deck_id);
                                    adapter.animateTo(filtered_list);
                                    mRecyclerView.scrollToPosition(0);
                                }
                                if (deck_id == 0) {
                                    deck_search_tv.setText("All Cards");

                                } else {
                                    deck_search_tv.setText(databaseManager.getDeckById(deck_id).getName());
                                }
                                toolbar_cards_tv.setText(filtered_list.size() + " cards");


                            }
                        })
                        .show();
                ;
            }
        });
    }

    public void initializeData() {

        data = new ArrayList<DBCard>();


        List<DBCard> card_list = databaseManager.getAllCards();
        //TableLayout card_table_layout = (TableLayout) findViewById(R.id.card_table_layout);

        data = card_list;

        //((CardCollectionActivity)getActivity()).setData(data);
    }


    /*@Override
    public void onDestroy() {
        if (databaseManager != null) {
            databaseManager.closeDbConnections();
            databaseManager = null;
        }
        super.onDestroy();
    }*/

}
