package com.raulvintila.app.lieflashcards.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raulvintila.app.lieflashcards.Activities.CardCollectionActivity;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.R;

import java.util.ArrayList;
import java.util.List;


public class DeckSpinnerAdapter extends BaseAdapter {

    private List<DBDeck> mItems = new ArrayList<>();
    LayoutInflater mInflater;

    public void clear() {
        mItems.clear();
    }

    public void addItem(DBDeck deck) {
        mItems.add(deck);
    }

    public void addItems(List<DBDeck> decks) {
        mItems.addAll(decks);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public DBDeck getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getDropDownView(int position, View view, ViewGroup parent) {

        Context context= CustomModel.getInstance().getContext();
        mInflater = LayoutInflater.from(context);
        if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
            view = mInflater.inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);
            view.setTag("DROPDOWN");
        }

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));

        return view;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context context= CustomModel.getInstance().getContext();
        mInflater = LayoutInflater.from(context);
        if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
            view = mInflater.inflate(R.layout.
                    toolbar_spinner_item_actionbar, parent, false);
            view.setTag("NON_DROPDOWN");
        }
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));
        return view;
    }

    private String getTitle(int position) {
        return position >= 0 && position < mItems.size() ? mItems.get(position).getName() : "";
    }
}
